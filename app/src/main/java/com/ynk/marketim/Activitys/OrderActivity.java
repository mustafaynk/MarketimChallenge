package com.ynk.marketim.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ynk.marketim.Adapter.AdapterOrder;
import com.ynk.marketim.Model.Order;
import com.ynk.marketim.Presenter.IOrdersPresenter;
import com.ynk.marketim.Presenter.OrdersPresenter;
import com.ynk.marketim.R;
import com.ynk.marketim.Util.Utils;
import com.ynk.marketim.View.IOrdersView;

import java.util.ArrayList;
import java.util.List;

import muyan.snacktoa.SnackToa;

public class OrderActivity extends AppCompatActivity implements IOrdersView {

    //Variables
    private List<Order> orders;
    private AdapterOrder adapterOrder;

    //Components
    private View rlContent, llProgressLayout;

    //Presenter
    private IOrdersPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //Init Presenter
        presenter = new OrdersPresenter(this, this);

        //Init Variable
        orders = new ArrayList<>();
        adapterOrder = new AdapterOrder(this, orders);

        //Init Components
        rlContent = findViewById(R.id.rlContent);
        llProgressLayout = findViewById(R.id.llProgressLayout);
        RecyclerView recyclerOrders = findViewById(R.id.recyclerOrders);
        Button btnOrders = findViewById(R.id.btnOrders);
        Button btnExitApp = findViewById(R.id.btnExitApp);
        btnExitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setMessage(getString(R.string.exitAppDialogTitle))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.exitAppDialogApprove), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                clearAutoLogin();
                            }
                        })
                        .setNegativeButton(getString(R.string.exitAppDialogDiscard), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders.clear();
                llProgressLayout.setVisibility(View.VISIBLE);
                rlContent.setVisibility(View.GONE);
                presenter.getOrders();
            }
        });

        //Init RecyclerView
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrders.setHasFixedSize(true);
        recyclerOrders.setAdapter(adapterOrder);

        //Order List Loading
        presenter.getOrders();
    }

    /*
    * Auto Login durumunun pasif hale getirilmesi yani kullanıcının oturumunun sonlandırılması.
    * */
    private void clearAutoLogin() {
        SharedPreferences preferences = getSharedPreferences(Utils.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        openLoginPage();
    }

    /*
    * Login Page Intent
    * Login Page'i açan metod
    * */
    private void openLoginPage() {
        Intent orderIntent = new Intent(OrderActivity.this, LoginActivity.class);
        startActivity(orderIntent);
        OrderActivity.this.finish();
    }

    @Override
    public void getOrdersResult(List<Order> orderList) {
        if (orderList != null) {
            orders.addAll(orderList);
            adapterOrder.notifyDataSetChanged();
            llProgressLayout.setVisibility(View.GONE);
            rlContent.setVisibility(View.VISIBLE);
        } else
            SnackToa.snackBarError(this, getString(R.string.connectionError));
    }
}
