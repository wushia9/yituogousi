package com.tencent.wxcloudrun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.wxcloudrun.mapper.StocksMapper;
import com.tencent.wxcloudrun.model.Stocks;
import com.tencent.wxcloudrun.model.dto.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liusheng
 * @date 2024/4/222:00
 * @desc TODO
 */
@Component
public class StockDataFetcher {

    @Autowired
    private StocksMapper stocksMapper;

    private static final String STOKE_URL = "https://tsanghi.com/api/fin/stock/XSHG/list?token=771744d866fa4b65a67e0eac3a31d45d";
    private static final String STOKE_2_URL = "http://71.push2.eastmoney.com/api/qt/clist/get";

    public Map<String, Object> getStokeData() {
        Map<String, Object> stockMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(STOKE_URL, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // 假设响应体是一个包含股票信息的 JSON 数组
                TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
                List<HashMap<String, Object>> stocks = objectMapper.readValue(responseBody, typeRef);

                for (HashMap<String, Object> stock : stocks) {
                    String ticker = (String) stock.get("ticker");
                    // 假设股票的活跃状态、交易所代码、国家代码和货币信息都在 stock 对象中
                    Map<String, Object> stockInfo = new HashMap<>();
                    stockInfo.put("is_active", stock.get("is_active"));
                    stockInfo.put("exchange_code", stock.get("exchange_code"));
                    stockInfo.put("country_code", stock.get("country_code"));
                    stockInfo.put("currency_code", stock.get("currency_code"));
                    stockMap.put(ticker, stockInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockMap;
    }

    /**
     * 获取股票数据并持久化到数据库
     * @param stockMap 获取的股票信息
     * @throws JsonProcessingException
     */
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void getStoke2Data(Map<String, Map<String, Object>> stockMap) throws JsonProcessingException {
//        List<StockData> stockDataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        for (int pageNum = 1; pageNum <= 282; pageNum++) {
            // 构造请求参数
            Map<String, String> params = new HashMap<>();
            params.put("pn", String.valueOf(pageNum)); // 页码
            params.put("pz", "20"); // 每页数量
            params.put("po", "1"); // 排序选项
            params.put("np", "1"); // 其他参数，根据实际情况可能不需要
            params.put("ut", "bd1d9ddb04089700cf9c27f6f7426281"); // 未知参数，根据实际情况可能需要替换
            params.put("fltt", "2"); // 股票类型
            params.put("invt", "2"); // 交易所类型
            params.put("wbp2u", "|0|0|0|web"); // 未知参数，根据实际情况可能需要替换
            params.put("fid", "f3"); // 字段 ID
            params.put("fs", "m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23,m:0+t:81+s:2048"); // 字段选择
            params.put("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152"); // 需要返回的字段列表
            params.put("_", "1711802870563"); // 时间戳

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            params.put("cb", "jQuery112401346198306766324_1711802870562");
            // 其他必要的参数...

            HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(STOKE_2_URL, request, String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // 假设响应体是一个包含股票信息的 JSON 对象
                Map<String, Object> data = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
                });
                Map<String, Object> diffData = (Map<String, Object>) data.get("data");
                List<Map<String, Object>> stockList = (List<Map<String, Object>>) diffData.get("diff");

                for (Map<String, Object> item : stockList) {
                    String stockCode = (String) item.get("f12"); // 股票代码
                    String stockName = (String) item.get("f14"); // 股票名称
                    BigDecimal latestPrice; // 最新价
                    Object priceObj = item.get("f2");
                    if (priceObj instanceof String) {
                        String priceStr = (String) priceObj;
                        if ("–".equals(priceStr) || "null".equals(priceStr) || priceStr.isEmpty()) {
                            // 如果字符串是 "–"、"null" 或空字符串，则设置为 0.00
                            latestPrice = BigDecimal.ZERO;
                        } else {
                            // 尝试将字符串转换为 BigDecimal
                            try {
                                latestPrice = new BigDecimal(priceStr);
                            } catch (NumberFormatException e) {
                                // 如果转换失败（例如字符串不是有效的数字），设置为 0.00
                                latestPrice = BigDecimal.ZERO;
                            }
                        }
                    } else if (priceObj instanceof BigDecimal) {
                        // 如果已经是一个 BigDecimal 对象，直接赋值
                        latestPrice = (BigDecimal) priceObj;
                    } else {
                        // 如果对象既不是字符串也不是 BigDecimal，设置为 0.00
                        latestPrice = BigDecimal.ZERO;
                    }
                    BigDecimal changePercent = BigDecimal.valueOf(((Number) item.get("f3")).doubleValue()); // 涨跌幅
                    BigDecimal changeAmount = BigDecimal.valueOf(((Number) item.get("f4")).doubleValue()); // 涨跌额
                    BigDecimal volume = BigDecimal.valueOf(((Number) item.get("f5")).longValue()); // 成交量(手)
                    BigDecimal turnover = BigDecimal.valueOf(((Number) item.get("f6")).doubleValue()); // 成交额
                    BigDecimal amplitude = BigDecimal.valueOf(((Number) item.get("f7")).doubleValue()); // 振幅
                    BigDecimal highestPrice = BigDecimal.valueOf(((Number) item.get("f15")).doubleValue()); // 最高价
                    BigDecimal lowestPrice = BigDecimal.valueOf(((Number) item.get("f16")).doubleValue()); // 最低价
                    BigDecimal openPrice = BigDecimal.valueOf(((Number) item.get("f17")).doubleValue()); // 今开价
                    BigDecimal lastClosePrice = BigDecimal.valueOf(((Number) item.get("f18")).doubleValue()); // 昨收价

                    // 创建股票数据对象并添加到列表中

                    Stocks stocks = new Stocks();
                    stocks.setStockSymbol(stockCode);
                    stocks.setStockName(stockName);
                    stocks.setStockTime(new Date(System.currentTimeMillis()));
//                    stocks.setChangePercent(changePercent);
//                    stocks.setChangeAmount(changeAmount);
                    stocks.setStockVolume(turnover);
//                    stocks.setTurnover(turnover);
//                    stocks.setAmplitude(amplitude);
                    stocks.setStockHigh(highestPrice);
                    stocks.setStockLow(lowestPrice);
                    stocks.setStockOpen(openPrice);
                    stocks.setStockClose(lastClosePrice);
                    // 假设 stockMap 已经包含了从 stoke.py 获取的数据
                    // 这里我们将其合并到 stockData 对象中
//                    stocks.setIsActive((Boolean)(stockMap.get(stockCode).get("is_active"))) ;
                    stocks.setExchangeId((String) stockMap.get(stockCode).get("exchange_code"));
                    stocks.setCountry((String) stockMap.get(stockCode).get("country_code"));
                    stocks.setCurrency((String) stockMap.get(stockCode).get("currency_code"));

                    stocksMapper.insert(stocks);

//                    stockDataList.add(stockData);
                }
            }
        }
//        return stockDataList;
    }
}
