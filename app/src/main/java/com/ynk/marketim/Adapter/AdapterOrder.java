package com.ynk.marketim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ynk.marketim.Model.Order;
import com.ynk.marketim.R;
import com.ynk.marketim.Util.Utils;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {

    private List<Order> items;
    private Context context;
    private List<String> months;
    private DecimalFormat decimalFormat;

    public AdapterOrder(Context context, List<Order> items) {
        this.items = items;
        months = Arrays.asList(context.getResources().getStringArray(R.array.months));
        decimalFormat = new DecimalFormat(Utils.DEC_FORMAT);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfMonth;
        TextView tvMonthName;
        TextView tvMarketName;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductState;
        TextView tvDetailProductName;
        TextView tvDetailProductPrice;
        View viewProductStateColor;
        View viewProductDetail;
        View parentView;

        ViewHolder(View v) {
            super(v);
            tvDayOfMonth = v.findViewById(R.id.tvDayOfMonth);
            tvMonthName = v.findViewById(R.id.tvMonthName);
            tvMarketName = v.findViewById(R.id.tvMarketName);
            tvProductName = v.findViewById(R.id.tvProductName);
            tvProductPrice = v.findViewById(R.id.tvProductPrice);
            viewProductStateColor = v.findViewById(R.id.viewProductStateColor);
            viewProductDetail = v.findViewById(R.id.viewProductDetail);
            tvDetailProductName = v.findViewById(R.id.tvDetailProductName);
            tvDetailProductPrice = v.findViewById(R.id.tvDetailProductPrice);
            tvProductState = v.findViewById(R.id.tvProductState);
            parentView = v.findViewById(R.id.parentView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderpage, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Order order = getItem(position);
        holder.tvDayOfMonth.setText(order.getDate());
        holder.tvMonthName.setText(months.get(Integer.parseInt(order.getMonth()) - 1));
        holder.tvMarketName.setText(order.getMarketName());
        holder.tvProductName.setText(order.getOrderName());
        holder.tvProductState.setText(order.getProductState());
        holder.tvProductPrice.setText(decimalFormat.format(order.getProductPrice()).concat(" TL"));
        holder.tvDetailProductName.setText(order.getProductDetail().getOrderDetail());
        holder.tvDetailProductPrice.setText(decimalFormat.format(order.getProductDetail().getSummaryPrice()).concat(" TL"));

        holder.parentView.setTag(position);
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = holder.viewProductDetail.getVisibility() == View.VISIBLE;
                if (show)
                    holder.viewProductDetail.setVisibility(View.GONE);
                else holder.viewProductDetail.setVisibility(View.VISIBLE);
                order.setExpand(!show);
            }
        });

        /*
        * Sipariş statüsüne göre state bilgilerini içeren componentlerin renklendirilmesi
        * */
        switch (order.getProductState()) {
            case "Yolda":
                holder.viewProductStateColor.setBackgroundColor(context.getResources().getColor(R.color.green_500));
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.green_500));
                break;
            case "Hazırlanıyor":
                holder.viewProductStateColor.setBackgroundColor(context.getResources().getColor(R.color.orange_500));
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.orange_500));
                break;
            case "Onay Bekliyor":
                holder.viewProductStateColor.setBackgroundColor(context.getResources().getColor(R.color.red_500));
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.red_500));
                break;
        }

        if (order.isExpand()) {
            holder.viewProductDetail.setVisibility(View.VISIBLE);
        } else {
            holder.viewProductDetail.setVisibility(View.GONE);
        }

    }


    private Order getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}