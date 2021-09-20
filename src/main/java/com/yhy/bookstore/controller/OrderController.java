package com.yhy.bookstore.controller;

import com.yhy.bookstore.constant.Constant;
import com.yhy.bookstore.entity.CartItem;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.User;
import com.yhy.bookstore.service.BookService;
import com.yhy.bookstore.service.CartService;
import com.yhy.bookstore.service.OrderService;
import com.yhy.bookstore.service.UserService;
import com.yhy.bookstore.utils.jmsutils.beans.OrderBean;
import com.yhy.bookstore.utils.jmsutils.beans.OrderItemBean;
import com.yhy.bookstore.utils.msgutils.Msg;
import com.yhy.bookstore.utils.msgutils.MsgCode;
import com.yhy.bookstore.utils.msgutils.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
  @Autowired private BookService bookService;
  @Autowired private UserService userService;
  @Autowired private CartService cartService;
  @Autowired private WebApplicationContext applicationContext;

  @RequestMapping("/getCart")
  public List<CartItem> getCart(@RequestBody Map<String, String> params) {
    Integer userId = Integer.valueOf(params.get(Constant.USER_ID));
    User user = userService.getUserById(userId);
    return user.getCart();
  }

  @RequestMapping("/addCart")
  public Msg addCart(@RequestBody Map<String, String> params) {
    JSONObject jsonObject = JSONObject.fromObject(params);
    CartItem cartItem = new CartItem();
    int userId = Integer.parseInt(jsonObject.get(Constant.USER_ID).toString());
    cartItem.setUserId(userId);
    int bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
    cartItem.setBook(bookService.findBookById(bookId));
    int bookNumber = Integer.parseInt(jsonObject.get(Constant.BOOK_NUMBER).toString());
    cartItem.setBookNumber(bookNumber);
    cartService.addCartItem(cartItem);
    return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ADD_SUCCESS_MSG);
  }

  @RequestMapping("/getOrders")
  public JSONArray getOrders(@RequestBody Map<String, String> params) {
    Integer userId = Integer.valueOf(params.get(Constant.USER_ID));
    List<Order> orders = userService.getOrders(userId);
    JSONArray result = new JSONArray();
    for (Order order : orders) {
      JSONObject object = new JSONObject();
      object.put("time", order.getTime().toString());
      object.put("items", order.getItems());
      object.put("userId", order.getUserId());
      object.put("orderId", order.getOrderId());
      result.add(object);
    }
    // return userService.getOrders(userId);
    return result;
  }

  @RequestMapping("/addOrder")
  public Msg addOrder(@RequestBody Map<String, Object> params) {
    JSONObject jsonObject = JSONObject.fromObject(params);
    int userId = Integer.parseInt(jsonObject.get(Constant.USER_ID).toString());
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    JSONArray jsonArray = jsonObject.getJSONArray(Constant.ITEMS);
    List<OrderItemBean> orderItemBeans = new ArrayList<OrderItemBean>();
    for (int i = 0; i < jsonArray.size(); ++i) {
      JSONObject tmp = jsonArray.getJSONObject(i);
      OrderItemBean orderItemBean = new OrderItemBean();
      int bookId = Integer.parseInt(tmp.get(Constant.BOOK_ID).toString());
      orderItemBean.setBookId(bookId);
      orderItemBean.setBookNumber(Integer.parseInt(tmp.get(Constant.BOOK_NUMBER).toString()));
      orderItemBean.setBookPrice(Integer.parseInt(tmp.get(Constant.BOOK_PRICE).toString()));

      orderItemBeans.add(orderItemBean);
    }

    OrderService orderService = applicationContext.getBean(OrderService.class);
    JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
    jmsTemplate.convertAndSend("order", new OrderBean(userId, timestamp, orderItemBeans));

    return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ORDER_RECEIVE_MSG);
  }

  @RequestMapping("/delCart")
  public Msg delCart(@RequestBody List<Map<String, String>> params) {
    JSONArray jsonArray = JSONArray.fromObject(params);
    if (bookService.decBookInventory(jsonArray)) {
      for (int i = 0; i < jsonArray.size(); ++i) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Integer itemId = Integer.parseInt(jsonObject.get(Constant.ITEMID).toString());
        cartService.delCartItem(itemId);
      }
      return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_SUCCESS_MSG);
    } else return MsgUtil.makeMsg(MsgCode.ERROR, MsgUtil.PURCHASE_FAIL_MSG);
  }

  @RequestMapping("/getAllOrders")
  public JSONArray getAllOrders() {
    List<User> users = userService.getUsers();
    List<Order> orders = new ArrayList<>();
    for (User user : users) {
      orders.addAll(user.getOrders());
    }
    JSONArray result = new JSONArray();
    for (Order order : orders) {
      JSONObject object = new JSONObject();
      object.put("time", order.getTime().toString());
      object.put("items", order.getItems());
      object.put("userId", order.getUserId());
      object.put("orderId", order.getOrderId());
      result.add(object);
    }
    return result;
  }

  @RequestMapping("delOrder")
  public Msg delOrder(@RequestBody Map<String, String> params) {
    JSONObject jsonObject = JSONObject.fromObject(params);
    int orderId = Integer.parseInt(jsonObject.get(Constant.ORDERID).toString());
    OrderService orderService = applicationContext.getBean(OrderService.class);
    orderService.delOrder(orderId);
    return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_SUCCESS_MSG);
  }
}
