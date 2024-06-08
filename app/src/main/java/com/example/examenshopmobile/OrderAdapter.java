package com.example.examenshopmobile;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OrderItemDao orderItemDao;

    public OrderAdapter(List<Order> orderList, OrderItemDao orderItemDao) {
        this.orderList = orderList;
        this.orderItemDao = orderItemDao;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderNumberTextView.setText("Заказ №: " + order.orderNumber);
        holder.orderDateTextView.setText("Дата и время: " + order.orderDate);
        holder.totalAmountTextView.setText("К оплате:" + order.totalAmount + "₽");
        holder.paymentMethodTextView.setText("Метод оплаты: " + order.paymentMethod);

        new GetItemCountTask(holder.itemCountTextView).execute(order.orderId);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumberTextView, orderDateTextView, totalAmountTextView, itemCountTextView, paymentMethodTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumberTextView = itemView.findViewById(R.id.orderNumberTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
            itemCountTextView = itemView.findViewById(R.id.itemCountTextView);
            paymentMethodTextView = itemView.findViewById(R.id.paymentMethodTextView);
        }
    }

    private class GetItemCountTask extends AsyncTask<Integer, Void, Integer> {
        private TextView itemCountTextView;

        public GetItemCountTask(TextView itemCountTextView) {
            this.itemCountTextView = itemCountTextView;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int orderId = params[0];
            return orderItemDao.getItemCountByOrderId(orderId);
        }

        @Override
        protected void onPostExecute(Integer itemCount) {
            itemCountTextView.setText("Товаров: " + itemCount);
        }
    }
}
