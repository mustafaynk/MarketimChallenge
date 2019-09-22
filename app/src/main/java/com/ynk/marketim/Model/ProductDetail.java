package com.ynk.marketim.Model;

/*
* Sipariş içerisindeki ürünün detayını içeren model
* */
public class ProductDetail {

    private String orderDetail;
    private double summaryPrice;

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public double getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(double summaryPrice) {
        this.summaryPrice = summaryPrice;
    }
}
