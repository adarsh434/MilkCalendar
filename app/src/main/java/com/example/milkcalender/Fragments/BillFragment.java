package com.example.milkcalender.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milkcalender.MainActivity;
import com.example.milkcalender.R;

public class BillFragment extends Fragment {

    DatePicker datePicker;
    Button button, calculateBill;
    EditText price;
    TextView volume, rate, enterPrice, enterVolume, totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bill, container, false);

        datePicker = root.findViewById(R.id.simpleDatePicker);
        button = root.findViewById(R.id.showBill);
        price = root.findViewById(R.id.price);
        volume = root.findViewById(R.id.volume);
        rate = root.findViewById(R.id.rate);
        calculateBill = root.findViewById(R.id.calculateBill);
        enterPrice = root.findViewById(R.id.enterPrice);
        enterVolume = root.findViewById(R.id.enterVolume);
        totalPrice = root.findViewById(R.id.totalPrice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalVolume = 0;
                int dayOfMonth = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                price.setVisibility(View.VISIBLE);
                volume.setVisibility(View.VISIBLE);
                rate.setVisibility(View.VISIBLE);
                calculateBill.setVisibility(View.VISIBLE);
                enterPrice.setVisibility(View.VISIBLE);
                enterVolume.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);

                if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
                    for (int i = dayOfMonth; i <= 31; i++) {
                        String dayMonthYear = "" + i + month + year;

                        SharedPreferences sh = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                        totalVolume = totalVolume + sh.getInt("volume", 00);
                    }
                }
                else if(month == 1 && ( ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0) )) {
                    for (int i = dayOfMonth; i <= 29; i++) {
                        String dayMonthYear = "" + i + month + year;

                        SharedPreferences sh = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                        totalVolume = totalVolume + sh.getInt("volume", 00);
                    }
                }
                else if(month == 1 || month == 3 || month == 5 || month == 8 || month == 10){
                    for (int i = dayOfMonth; i <= 30; i++) {
                        String dayMonthYear = "" + i + month + year;

                        SharedPreferences sh = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                        System.out.println(sh.getInt("volume", 00));
                        totalVolume = totalVolume + sh.getInt("volume", 00);
                    }
                }
                else {
                    for (int i = dayOfMonth; i <= 28; i++) {
                        String dayMonthYear = "" + i + month + year;

                        SharedPreferences sh = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                        totalVolume = totalVolume + sh.getInt("volume", 00);
                    }
                }
                String set = ""+totalVolume;
                volume.setText(set);
            }
        });

        calculateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "First Enter Price", Toast.LENGTH_SHORT).show();
                }
                else{
                    int Rs = Integer.parseInt(price.getText().toString());
                    int TotalVolume = Integer.parseInt(volume.getText().toString());
                    int totalRs = Rs * TotalVolume;
                    String set = "Rs."+totalRs;
                    rate.setText(set);
                }
            }
        });

        return root;
    }
}