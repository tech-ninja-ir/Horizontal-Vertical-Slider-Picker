package com.example.nbtk.slider.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nbtk.slider.R;

import java.util.ArrayList;

public class MainActivityJava extends AppCompatActivity implements SliderAdapterJava.Callback,
        SliderLayoutManagerJava.OnItemSelectedListener {

    private RecyclerView rvHorizontalPicker;
    private TextView tvSelectedItem;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setDataToArrayList();
        setTvSelectedItem();
        setHorizontalPicker();

    }

    private void setDataToArrayList() {
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
            Log.e("---", "i = " + i);
        }
    }

    private void setTvSelectedItem() {
        tvSelectedItem = findViewById(R.id.tv_selected_item);
    }

    private void setHorizontalPicker() {
        rvHorizontalPicker = findViewById(R.id.rv_horizontal_picker);

        // Setting the padding such that the items will appear in the middle of the screen
        int padding = ScreenUtilsJava.getScreenWidth(this) / 2
                - ScreenUtilsJava.dpToPx(this, 40);
        rvHorizontalPicker.setPadding(padding, 0, padding, 0);

        Log.e("---", "11111111111");

        // Setting layout manager
        SliderLayoutManagerJava sliderLayoutManagerJava = new SliderLayoutManagerJava(this, LinearLayoutManager.HORIZONTAL, true);
        sliderLayoutManagerJava.callback = this;

        rvHorizontalPicker.setLayoutManager(sliderLayoutManagerJava);
        Log.e("---", "22222222222");

        // Setting Adapter
        SliderAdapterJava sliderAdapterJava = new SliderAdapterJava();
        sliderAdapterJava.callback = this;
        sliderAdapterJava.setData(data);
        rvHorizontalPicker.setAdapter(sliderAdapterJava);

        Log.e("---", "33333333333");
    }

    @Override
    public void onItemClicked(View view) {
        rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view));
    }

    @Override
    public void onItemSelected(int layoutPosition) {
        tvSelectedItem.setText(data.get(layoutPosition));
    }
}
