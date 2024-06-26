package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.Articles;
import com.tencent.wxcloudrun.model.Bill;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.vo.BillListVo;
import com.tencent.wxcloudrun.model.vo.UsersInfo;
import com.tencent.wxcloudrun.service.ArticlesService;
import com.tencent.wxcloudrun.service.BillService;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * counter控制器
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
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
                      .eq(User::getWechatOpenid, openId)
                      .eq(User::getIsLogin, 1);
      User one = userService.getOne(queryWrapper);
      if (one == null){
        // 不存在该用户
        return ApiResponse.ok(0);
      }else{
        return ApiResponse.ok(1);
      }
    }

  @GetMapping(value = "/logout")
  ApiResponse logout(@RequestHeader("x-wx-openid")String openId) {
    logger.info("/api/isLogin get request, openId: ", openId);
    boolean b = userService.logout(openId);
    return ApiResponse.ok();
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
  @GetMapping(value = "/articlesPage")
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
  /**
   * 新增文章
   * @param articles
   * @return
   */
  @PostMapping(value = "/articles")
  ApiResponse postArticles(@RequestBody Articles articles){
    articles.setArticlesId(null);
    boolean b = articlesService.saveOrUpdate(articles);
    return ApiResponse.ok(b);
  }
  /**
   * 删除文章
   * @param id
   * @return
   */
  @DeleteMapping(value = "/articles/{id}")
  ApiResponse deleteArticles(@PathVariable("id")int id){
    boolean b = articlesService.removeById(id);
    return ApiResponse.ok(b);
  }

  @PostMapping(value = "/login")
  ApiResponse loginToAdmin(@RequestBody User user){
    logger.info("/api/login post request, user: {}", user);
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getUsername, user.getUsername())
            .eq(User::getPassword, user.getPassword())
            .eq(User::getRole, 1)
            .eq(User::getIsDeleted, 0);
    User one = userService.getOne(queryWrapper);
    if (one != null){
      return ApiResponse.ok(one.getWechatOpenid());
    }else{
      return ApiResponse.error("用户名或密码错误");
    }

  }

  @GetMapping(value = "/userInfo")
  ApiResponse getUserInfo(@RequestHeader("x-wx-openid")String openId){
    logger.info("/api/userInfo get request, openId: {}", openId);
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId)
            .eq(User::getIsDeleted, 0)
            .eq(User::getRole, 1);
    User one = userService.getOne(queryWrapper);
    if (one == null){
      return ApiResponse.error("你没有权限！");
    }else{
      UsersInfo usersInfo = billService.getUsersInfo();
      return ApiResponse.ok(usersInfo);
    }
  }

  @GetMapping(value = "/users")
  ApiResponse getUserInfoByOpenid(@RequestHeader("x-wx-openid")String openId){
    logger.info("/api/userInfo get request, openId: {}", openId);
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId)
            .eq(User::getIsDeleted, 0);
    User one = userService.getOne(queryWrapper);
    if (one == null){
      return ApiResponse.error("用户不存在");
    }else{
      return ApiResponse.ok(one);
    }
  }

//  @GetMapping(value = "/getAuthKey")
//  ApiResponse getAuthKey(@RequestHeader("x-wx-openid")String openId) {
//    HttpClient client = HttpClient.newHttpClient();
//    HttpRequest request = HttpRequest.newBuilder()
//            .uri(URI.create("http://api.weixin.qq.com/wxa/msg_sec_check"))
//            .header("Content-Type", "application/json")
//            .POST(java.net.http.HttpRequest.BodyPublishers.ofString(
//                    "{\"openid\":\"用户的openid\",\"version\":2,\"scene\":2,\"content\":\"安全检测文本\"}"))
//            .build();
//
//    client.sendAsync(request, BodyHandlers.ofString())
//            .thenApply(HttpResponse::body)
//            .thenAccept(responseBody -> {
//              System.out.println("接口返回内容: " + responseBody);
//              try {
//                // 这里可以解析responseBody为JSON对象，然后进行后续处理
//                // 例如，使用org.json或者Jackson库解析JSON
//              } catch (IOException e) {
//                e.printStackTrace();
//              }
//            })
//            .exceptionally(error -> {
//              System.err.println("请求失败: " + error);
//              return null;
//            });
//  }


  @GetMapping("/usersPage")
  ApiResponse getUsersPage(@RequestParam("info")String info, @RequestParam("page")int page) {
    Page<User> users = userService.getUsersPage(info, page);
    return ApiResponse.ok(users);
  }

  @GetMapping("/userXheader/{openId}")
  ApiResponse userXheader(@PathVariable("openId")String openId){
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId);
    User one = userService.getOne(queryWrapper);
    one.setAvatarUrl("");
    userService.updateById(one);
    return ApiResponse.ok();
  }

  @GetMapping("/userXname/{openId}")
  ApiResponse userXname(@PathVariable("openId")String openId){
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId);
    User one = userService.getOne(queryWrapper);
    one.setUsername("违规名称");
    userService.updateById(one);
    return ApiResponse.ok();
  }

  @PostMapping("/users")
  ApiResponse postUser(@RequestBody User user, @RequestHeader("x-wx-openid")String openId) {
    // 查找数据库是否有该用户
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId)
            .eq(User::getIsDeleted, 0);
    User one = userService.getOne(queryWrapper);
    if (one == null){
      user.setWechatOpenid(openId);
      user.setIsLogin(1);
      userService.save(user);
    }else{
      one.setIsLogin(1);
      one.setUsername(user.getUsername());
      one.setAvatarUrl(user.getAvatarUrl());
      userService.updateById(one);
    }
    return ApiResponse.ok();
  }

  @PutMapping("/users")
  ApiResponse putUser(@RequestBody User user, @RequestHeader("x-wx-openid")String openId) {
    // 查找数据库是否有该用户
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
            .eq(User::getWechatOpenid, openId)
            .eq(User::getIsDeleted, 0);
    User one = userService.getOne(queryWrapper);
    if (user.getUsername() != null && user.getUsername().length() > 0){
      one.setUsername(user.getUsername());
    }
    if (user.getAvatarUrl() != null && user.getAvatarUrl().length() > 0){
      one.setAvatarUrl(user.getAvatarUrl());
    }
    userService.updateById(one);
    return ApiResponse.ok();
  }



}