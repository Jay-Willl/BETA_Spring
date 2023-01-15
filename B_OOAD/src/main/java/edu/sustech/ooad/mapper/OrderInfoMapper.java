package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    void insertOrderInfo(OrderInfo orderInfo);

    List<OrderInfo> selectNoPayOrderInfo();

    List<OrderInfo> selectOrderInfoByOrderNo(String order_no);

    void updateStatusByOrderNo(String order_no);
}
