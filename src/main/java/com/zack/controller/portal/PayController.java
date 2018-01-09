package com.zack.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.zack.common.Const;
import com.zack.common.ResponseCode;
import com.zack.common.ServerResponse;
import com.zack.model.User;
import com.zack.service.IPayService;
import com.zack.utils.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/pay/")
@Controller
public class PayController {
    private static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private IPayService iPayService;

    @RequestMapping("alipay/order.do")
    @ResponseBody
    public ServerResponse aliPay(HttpSession httpSession, @RequestParam("order_no") Long orderNo) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iPayService.aliPay(orderNo);
    }
}
