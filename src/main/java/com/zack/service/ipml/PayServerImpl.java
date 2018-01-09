package com.zack.service.ipml;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.zack.common.ServerResponse;
import com.zack.mapper.ProductMapper;
import com.zack.mapper.ProductOrderItemMapper;
import com.zack.mapper.ProductOrderMapper;
import com.zack.model.Product;
import com.zack.model.ProductOrder;
import com.zack.model.ProductOrderItem;
import com.zack.service.IPayService;
import com.zack.utils.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PayServerImpl implements IPayService {

    @Autowired
    private ProductOrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductOrderItemMapper orderItemMapper;

    public ServerResponse aliPay(Long orderNo) {
        if (orderNo == null) {
            return ServerResponse.createByErrorMessage("订单号为空");
        }
        ProductOrder productOrder = orderMapper.selectByOrderNo(orderNo);
        if (productOrder == null) {
            return ServerResponse.createByErrorMessage("订单号错误");
        }
        return aliPay(productOrder);

    }

    private ServerResponse aliPay(ProductOrder productOrder) {
        List<ProductOrderItem> orderItemList = orderItemMapper.getByOrderNo(productOrder.getOrderNo());
        StringBuffer orderMsg = new StringBuffer();
        for (ProductOrderItem orderItem : orderItemList) {
            orderMsg.append(orderItem.getProductName() + ";");
        }
        String APP_ID = AlipayUtil.getProperty("appid");
        String APP_PRIVATE_KEY = AlipayUtil.getProperty("private_key");
        String CHARSET = "UTF-8";
        String ALIPAY_PUBLIC_KEY = AlipayUtil.getProperty("alipay_public_key");
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("订单号：" + productOrder.getOrderNo());
        model.setSubject(orderMsg.toString());
        model.setOutTradeNo(productOrder.getOrderNo() + "");
        model.setTimeoutExpress("30m");
        model.setTotalAmount(productOrder.getPayment().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://domain.merchant.com/payment_notify");
        AlipayTradeAppPayResponse response = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = alipayClient.sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccessMessage(response.getBody());
    }
}
