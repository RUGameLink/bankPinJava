package com.example.bankpinjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private List<Card> cardList;

    public recyclerAdapter(List<Card> cardList){ //Конструктор с передачей  листа
        this.cardList = cardList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//Инициализация объектов лайаута айтемов ресайклера
        private TextView nameText;
        private TextView pinText;

        public MyViewHolder(final View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            pinText = itemView.findViewById(R.id.pinText);
        }
    }
    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Подвязка лайаута к адаптеру ресайклера
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false); //Определение лайаута
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {//Запись в айтем данных в заготовленные textview
        String name = cardList.get(position).getName();
        holder.nameText.setText(name);

        String pin = cardList.get(position).getPin();
        holder.pinText.setText(pin);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
