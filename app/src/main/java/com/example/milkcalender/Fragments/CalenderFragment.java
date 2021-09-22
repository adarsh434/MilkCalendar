package com.example.milkcalender.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milkcalender.R;

public class CalenderFragment extends Fragment {

    CalendarView calendarView;
    EditText editText;
    TextView textView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarView = root.findViewById(R.id.calenderView);
        editText = root.findViewById(R.id.volume);
        textView = root.findViewById(R.id.enterVolume);
        button = root.findViewById(R.id.save);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                editText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                editText.setText("");

                String dayMonthYear = ""+dayOfMonth+month+year;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "First Enter Volume", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putInt("volume", Integer.parseInt(editText.getText().toString()));
                            myEdit.apply();

                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                SharedPreferences sh = getActivity().getSharedPreferences(dayMonthYear, Context.MODE_PRIVATE);
                int volume = sh.getInt("volume", 00);
                editText.setText(String.valueOf(volume));
            }
        });
        return root;
    }
}