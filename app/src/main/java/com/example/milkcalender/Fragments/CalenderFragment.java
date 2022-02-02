package com.example.milkcalender.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.milkcalender.Utils.DatabaseHandler;

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

        DatabaseHandler handler = new DatabaseHandler(getContext());

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
                            if(handler.CheckIsDataAlreadyPresent(dayMonthYear)) {
                                handler.updateVolume(dayMonthYear, Integer.parseInt(editText.getText().toString()));
                                Toast.makeText(getActivity(), "VolumeUpdated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                handler.addVolume(dayMonthYear, Integer.parseInt(editText.getText().toString()));
                                Toast.makeText(getActivity(), "VolumeSaved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                if(handler.CheckIsDataAlreadyPresent(dayMonthYear)) {
                    SQLiteDatabase db = handler.getReadableDatabase();
                    String Query = "SELECT volume FROM MilkCalender WHERE date = " + dayMonthYear;

                    Cursor cursor = db.rawQuery(Query, null);
                    cursor.moveToFirst();
                    String data = cursor.getString(cursor.getColumnIndex("volume"));
                    editText.setText(data);
                }
            }
        });
        return root;
    }
}