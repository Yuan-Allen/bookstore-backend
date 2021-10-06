package com.yhy.bookstore.controller;

import com.yhy.bookstore.constant.Constant;
import com.yhy.bookstore.entity.User;
import com.yhy.bookstore.service.UserService;
import com.yhy.bookstore.utils.msgutils.Msg;
import com.yhy.bookstore.utils.msgutils.MsgCode;
import com.yhy.bookstore.utils.msgutils.MsgUtil;
import com.yhy.bookstore.utils.sessionutils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

  @Autowired private UserService userService;

  @RequestMapping("/login")
  public Msg login(@RequestBody Map<String, String> params) {
    String username = params.get(Constant.USERNAME);
    String password = params.get(Constant.PASSWORD);
    User auth = userService.checkUser(username, password);
    if (auth != null && auth.getUserType() != -1) {

      JSONObject obj = new JSONObject();
      obj.put(Constant.USER_ID, auth.getUserId());
      obj.put(Constant.USERNAME, auth.getUsername());
      obj.put(Constant.USER_TYPE, auth.getUserType());
      SessionUtil.setSession(obj);

      JSONObject data = JSONObject.fromObject(auth);
      data.remove(Constant.PASSWORD);

      return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, data);
    } else if (auth != null) {
      return MsgUtil.makeMsg(MsgCode.ERROR, MsgUtil.LOGIN_USER_BAN_ERROR_MSG);
    } else {
      return MsgUtil.makeMsg(MsgCode.LOGIN_USER_ERROR);
    }
  }

  @RequestMapping("/register")
  public Msg register(@RequestBody Map<String, String> params) {
    String username = params.get(Constant.USERNAME);
    if (!userService.checkUsername(username))
      return MsgUtil.makeMsg(MsgCode.ERROR, MsgUtil.REGISTER_ERROR_MSG);
    String password = params.get(Constant.PASSWORD);
    String email = params.get(Constant.EMAIL);
    String address = params.get(Constant.ADDRESS);

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setUserType(1);
    user.setEmail(email);
    user.setAddress(address);
    userService.addUser(user);

    return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.REGISTER_SUCCESS_MSG);
  }

  @RequestMapping("/logout")
  public Msg logout() {
    Boolean status = SessionUtil.removeSession();

    if (status) {
      return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }
    return MsgUtil.makeMsg(MsgCode.ERROR, MsgUtil.LOGOUT_ERR_MSG);
  }

  @RequestMapping("/getUsers")
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @RequestMapping("/switchUserAuth")
  public Msg switchUserAuth(@RequestBody Map<String, String> params) {
    JSONObject jsonObject = JSONObject.fromObject(params);
    int userId = Integer.parseInt(jsonObject.get(Constant.USER_ID).toString());

    userService.switchUserAuth(userId);

    return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SWITCH_AUTH_SUCCESS_MSG);
  }

  @RequestMapping("/checkUsername")
  public Msg checkUserName(@RequestBody Map<String, String> params) {
    JSONObject jsonObject = JSONObject.fromObject(params);
    String username = jsonObject.getString(Constant.USERNAME);
    if (userService.checkUsername(username))
      return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    else return MsgUtil.makeMsg(MsgCode.ERROR, MsgUtil.ERROR_MSG);
  }

  @RequestMapping("/visits")
  public Integer visits() {
    return userService.visitsIncrement();
  }
}
