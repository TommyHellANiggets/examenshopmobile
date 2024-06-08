package com.example.examenshopmobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Получите экземпляр базы данных
        AppDatabase db = AppDatabase.getInstance(getContext());
        orderDao = db.orderDao();
        orderItemDao = db.orderItemDao();

        // Асинхронное получение списка заказов
        new GetOrdersTask().execute();

        return view;
    }

    private class GetOrdersTask extends AsyncTask<Void, Void, List<Order>> {
        @Override
        protected List<Order> doInBackground(Void... voids) {
            return orderDao.getAllOrders();
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            orderAdapter = new OrderAdapter(orders, orderItemDao);
            recyclerView.setAdapter(orderAdapter);
        }
    }
}
