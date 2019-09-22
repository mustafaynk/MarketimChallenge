package com.ynk.marketim.Presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynk.marketim.Model.Order;
import com.ynk.marketim.Util.Utils;
import com.ynk.marketim.View.IOrdersView;

import java.lang.reflect.Type;
import java.util.List;

public class OrdersPresenter implements IOrdersPresenter {

    private IOrdersView repositoryView;
    private RequestQueue queue;

    public OrdersPresenter(Context context, IOrdersView repositoryView) {
        queue = Volley.newRequestQueue(context);
        this.repositoryView = repositoryView;
    }

    @Override
    public void getOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.ORDERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Order>>() {
                        }.getType();
                        List<Order> orders = gson.fromJson(response, listType);
                        repositoryView.getOrdersResult(orders);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                repositoryView.getOrdersResult(null);
            }
        });
        queue.add(stringRequest);
    }
}
