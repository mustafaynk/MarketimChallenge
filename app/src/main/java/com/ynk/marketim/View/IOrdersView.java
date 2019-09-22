package com.ynk.marketim.View;


import com.ynk.marketim.Model.Order;

import java.util.List;

public interface IOrdersView {
    /*
    * Sipariş listesini veren method
    * @param
    *   orders: Sipariş listesini içerir.
    * */
    void getOrdersResult(List<Order> orders);
}
