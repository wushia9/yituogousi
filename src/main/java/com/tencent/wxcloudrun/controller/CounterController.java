package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Bill;
import com.tencent.wxcloudrun.model.vo.BillListVo;
import com.tencent.wxcloudrun.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * counter控制器
 */
@RestController
@RequestMapping("/api")
public class CounterController {

  final Logger logger;
  private final BillService billService;

  public CounterController(@Autowired BillService billService) {
    this.billService = billService;
    this.logger = LoggerFactory.getLogger(CounterController.class);
  }

  /**
   * 获取账单
   * @return API response json
   */
  @GetMapping(value = "/bills")
    ApiResponse getBills(@RequestHeader("x-wx-openid")String openId) {
      //获取请求中的请求头
      logger.info("/api/bills get request openid: {}", openId);
      List<BillListVo> bills = billService.getBills(openId);
      return ApiResponse.ok(bills);
    }

  /**
   * 修改账单
   * @param bill 账单
   * @param billId 账单id
   * @return API response json
   */
  @PutMapping(value = "/bills/{id}")
    ApiResponse putBill(@RequestBody Bill bill, @PathVariable("id") int billId, @RequestHeader("x-wx-openid")String openId) {
      logger.info("/api/putBill post request, bill: {}", bill);
      bill.setBillId(billId);
      billService.putBill(bill, openId);
      return ApiResponse.ok(bill);
    }

  /**
   * 修改账单
   * @param bill 账单
   * @param request 请求
   * @return API response json
   */
  @PostMapping(value = "/bill")
  ApiResponse putBill(@RequestBody Bill bill, HttpServletRequest request) {
    logger.info("/api/putBill post request, bill: {}", bill);
    String openId = request.getHeader("x-wx-openid");
    billService.putBill(bill, openId);
    return ApiResponse.ok(bill);
  }

}