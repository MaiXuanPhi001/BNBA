package com.example.binance.adapter;

import static com.example.binance.constants.App.COIN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binance.R;
import com.example.binance.models.coins.Coin;
import com.example.binance.screens.TradeActivity;

import java.io.Serializable;
import java.util.List;

public class CoinHomeAdapter extends RecyclerView.Adapter<CoinHomeAdapter.CoinHomeViewHolder>{
    private Context context;
    private List<Coin> coins;
    private int width;

    public CoinHomeAdapter(Context context, int width) {
        this.context = context;
        this.width = width;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Coin> coins) {
        this.coins = coins;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoinHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_home, parent, false);
        return new CoinHomeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CoinHomeViewHolder holder, int position) {
        holder.lnPercentCoin.setMinimumWidth(width * 10 / 100);

        Coin coin = coins.get(position);
        holder.tvSymbol.setText(coin.getSymbol());
        holder.tvClose.setText(String.valueOf(coin.getClose()));
        holder.tvClose2.setText(coin.getClose() + "");
        if (coin.getPercentChange() >= 0) {
            holder.tvPercent.setText("+" + coin.getPercentChange() + "%");
            holder.lnPercentCoin.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.green_can), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.tvPercent.setText(coin.getPercentChange() + "%");
            holder.lnPercentCoin.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red_can), PorterDuff.Mode.SRC_ATOP);
        }
        holder.linerLayoutItemCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TradeActivity.class);
                intent.putExtra(COIN, (Serializable) coin);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (coins != null) {
            return coins.size();
        }
        return 0;
    }

    public class CoinHomeViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSymbol, tvClose, tvClose2, tvPercent;
        private LinearLayout linerLayoutItemCoin, lnPercentCoin;

        public CoinHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSymbol = itemView.findViewById(R.id.tv_symbol_home);
            tvClose = itemView.findViewById(R.id.tv_close_price_home);
            tvClose2 = itemView.findViewById(R.id.tv_close_price_home2);
            tvPercent = itemView.findViewById(R.id.tv_percent_coin_home);
            lnPercentCoin = itemView.findViewById(R.id.ln_percent_coin_home);
            linerLayoutItemCoin = itemView.findViewById(R.id.linear_layout_item_coin_home);
        }
    }
}
