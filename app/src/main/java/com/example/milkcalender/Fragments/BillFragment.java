package com.example.milkcalender.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.milkcalender.R;
import com.example.milkcalender.Utils.DatabaseHandler;

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

        DatabaseHandler handler = new DatabaseHandler(getContext());

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


                for (int i = dayOfMonth; i <= 31; i++) {
                    String dayMonthYear = "" + i + month + year;

                    if(handler.CheckIsDataAlreadyPresent(dayMonthYear)) {
                        SQLiteDatabase db = handler.getReadableDatabase();
                        String Query = "SELECT volume FROM MilkCalender WHERE date = " + dayMonthYear;

                        Cursor cursor = db.rawQuery(Query, null);
                        cursor.moveToFirst();
                        String data = cursor.getString(cursor.getColumnIndex("volume"));
                        totalVolume = totalVolume + Integer.parseInt(data);
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