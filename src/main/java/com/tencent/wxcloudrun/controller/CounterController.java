package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.Articles;
import com.tencent.wxcloudrun.model.Bill;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.vo.BillListVo;
import com.tencent.wxcloudrun.service.ArticlesService;
import com.tencent.wxcloudrun.service.BillService;
import com.tencent.wxcloudrun.service.UserService;
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
  private final ArticlesService articlesService;
  private final UserService userService;

  public CounterController(@Autowired BillService billService,
                           @Autowired ArticlesService articlesService,
                           @Autowired UserService userService
                           ) {
    this.billService = billService;
    this.userService = userService;
    this.logger = LoggerFactory.getLogger(CounterController.class);
    this.articlesService = articlesService;
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
   * 获取一条账单
   * @return API response json
   */
  @GetMapping(value = "/billsOne/{id}")
  ApiResponse getBill(@RequestHeader("x-wx-openid")String openId, @PathVariable("id")int id) {
    //获取请求中的请求头
    logger.info("/api/bills get request openid: {}", openId);
    Bill bill = billService.getBillById(id);
    return ApiResponse.ok(bill);
  }

  /**
   * 添加账单
   * @param bill 账单
   * @return API response json
   */
  @PostMapping(value = "/bills")
    ApiResponse postBill(@RequestBody Bill bill, @RequestHeader("x-wx-openid")String openId) {
      logger.info("/api/bills post request, bill: {}", bill);
      billService.putBill(bill, openId);
      return ApiResponse.ok(bill);
    }

    @GetMapping(value = "/isLogin")
    ApiResponse isLogin(@RequestHeader("x-wx-openid")String openId) {
      logger.info("/api/isLogin get request, openId: ", openId);
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda()
                      .eq(User::getIsDeleted, 0)
                      .eq(User::getWechatOpenid, openId);
      User one = userService.getOne(queryWrapper);
      if (one == null){
        // 不存在该用户
        return ApiResponse.ok(0);
      }else{
        return ApiResponse.ok(1);
      }

    }

  /**
   * 修改账单
   * @param bill 账单
   * @param request 请求
   * @return API response json
   */
  @PutMapping(value = "/bills/{id}")
  ApiResponse putBill(@RequestBody Bill bill, @PathVariable("id") int billId, HttpServletRequest request) {
    logger.info("/api/putBill post request, bill: {}", bill);
    String openId = request.getHeader("x-wx-openid");
    billService.updateById(bill);
    return ApiResponse.ok(bill);
  }
  /**
   * 删除账单
   * @param request 请求
   * @return API response json
   */
  @DeleteMapping(value = "/bills/{id}")
  ApiResponse deleteBill(@PathVariable("id") int billId, HttpServletRequest request) {
    String openId = request.getHeader("x-wx-openid");
    Bill bill = billService.getBillById(billId);
    bill.setIsDeleted(1);
    billService.updateById(bill);
    return ApiResponse.ok(bill);
  }


  /**
   * 获得文章分页
   * @param info
   * @param page
   * @return
   */
  @GetMapping(value = "/articles")
  ApiResponse getArticlesPage(@RequestParam("info")String info, @RequestParam("page")int page){
    Page<Articles> articles = articlesService.getArticlesPage(info, page);
    logger.info("/api/articles get request, info: {}, page: {}", info, page);
    for (Articles art :
            articles.getRecords()) {
      art.setContent("");
    }
    return ApiResponse.ok(articles);
  }
  /**
   * 获得文章列表
   * @return
   */
  @GetMapping(value = "/articlesList")
  ApiResponse getArticlesList(){
    List<Articles> articles = articlesService.getArticlesList();
    return ApiResponse.ok(articles);
  }

  /**
   * 获得具体文章
   * @param id
   * @return
   */
  @GetMapping(value = "/articles/{id}")
  ApiResponse getArticlesContent(@PathVariable("id")int id){
    Articles articles = articlesService.getById(id);
    return ApiResponse.ok(articles);
  }

  /**
   * 修改文章
   * @param articles
   * @param id
   * @return
   */
  @PutMapping(value = "/articles/{id}")
  ApiResponse putArticles(@RequestBody Articles articles, @PathVariable("id")int id){
    articlesService.updateById(articles);
    return ApiResponse.ok();
  }

}