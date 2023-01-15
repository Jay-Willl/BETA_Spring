package edu.sustech.ooad.service.itf;

import edu.sustech.ooad.entity.OrderInfo;

import java.util.List;

public interface OrderInfoService{

    OrderInfo createOrderByProductId(int payAmount, int userId);

    public List<OrderInfo> getNoPayOrderList();
}

