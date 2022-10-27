package com.kai.blebeacon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kai.blebeacon.R;
import com.kai.blebeacon.model.Item;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemHolder> {
    List<Item> itemList;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public HomeItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        HomeItemHolder holder = new HomeItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewPrice.setText("Before Price $: "+ df.format(item.getPrice()));
        holder.textViewDiscount.setText("Discount: " + item.getDiscount() +"%");
        holder.textViewQuantity.setText("0");
        double finalPrice = item.getPrice() - ((item.getDiscount() * item.getPrice())/100);
        holder.textViewFinalPrice.setText("Discounted Price $: " + df.format(finalPrice));

        Picasso.get().load(item.getUri()).placeholder(R.mipmap.ic_launcher_round).into(holder.imageViewItem);

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
class HomeItemHolder extends RecyclerView.ViewHolder{

    TextView textViewName;
    TextView textViewPrice;
    TextView textViewFinalPrice;
    TextView textViewQuantity;
    TextView textViewDiscount;
    ImageView imageViewItem;

    public HomeItemHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewPrice = itemView.findViewById(R.id.textViewPrice);
        textViewFinalPrice = itemView.findViewById(R.id.textViewFinalPrice);
        textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
        textViewDiscount = itemView.findViewById(R.id.textViewDiscount);
        imageViewItem = itemView.findViewById(R.id.imageViewItem);
    }
}
