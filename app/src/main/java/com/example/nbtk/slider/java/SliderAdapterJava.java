package com.example.nbtk.slider.java;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nbtk.slider.R;

import java.util.ArrayList;

class SliderAdapterJava extends RecyclerView.Adapter<SliderAdapterJava.SliderItemViewHolder> {

    public Callback callback;
    private ArrayList<String> data = new ArrayList();

    @Override
    public SliderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slider_item, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClicked(v);
            }
        });

        SliderItemViewHolder horizontalViewHolder = new SliderItemViewHolder(itemView);
        return horizontalViewHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(SliderItemViewHolder holder, int position) {
        holder.tvItem.setText(data.get(position));
    }

    public void setData(ArrayList<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onItemClicked(View view);
    }

    class SliderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public SliderItemViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
