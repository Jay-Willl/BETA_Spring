package edu.sustech.ooad.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import edu.sustech.ooad.entity.OrderInfo;
import edu.sustech.ooad.mapper.OrderInfoMapper;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.itf.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Resource
    OrderInfoServiceImpl orderInfoService;

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private Environment config;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public String tradeCreate(Integer payAmount, Integer userId) {
        try{
            OrderInfo orderInfo = orderInfoService.createOrderByProductId(payAmount, userId);
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

            request.setNotifyUrl(config.getProperty("alipay.notify-url"));
            request.setReturnUrl(config.getProperty("alipay.return-url"));

            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderInfo.getOrder_no());
            BigDecimal total = new BigDecimal(orderInfo.getTotal_fee().toString()).divide(new BigDecimal("100"));
            bizContent.put("total_amount", total);
            bizContent.put("subject", orderInfo.getTitle());
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

            request.setBizContent(bizContent.toString());

            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

            if(response.isSuccess()){
                return response.getBody();
            } else {
                throw new RuntimeException("创建支付交易失败");
            }
        }catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    @Override
    public String queryOrder(String orderNo) {
        try{
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());

            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                return response.getBody();
            } else {
                return null;//订单不存在
            }
        }catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口的调用失败");
        }
    }

    @Override
    public void checkOrderStatus(String orderNo) {

        String result = this.queryOrder(orderNo);
        //订单未创建
        if(result == null){
            return;
        }
        Gson gson = new Gson();

        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");
        String tradeStatus = (String)alipayTradeQueryResponse.get("trade_status");

        if("TRADE_SUCCESS".equals(tradeStatus)){
            List<OrderInfo> orderInfoList = orderInfoMapper.selectOrderInfoByOrderNo(orderNo);
            int userId = orderInfoList.get(0).getUser_id();
            int gold = orderInfoList.get(0).getTotal_fee();
            userMapper.rechargeUserGold(gold,userId);
            orderInfoMapper.updateStatusByOrderNo(orderNo);
        }
    }
}
