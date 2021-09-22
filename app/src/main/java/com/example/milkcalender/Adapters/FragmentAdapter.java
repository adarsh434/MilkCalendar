package com.example.milkcalender.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.milkcalender.Fragments.BillFragment;
import com.example.milkcalender.Fragments.CalenderFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) { super(fm); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CalenderFragment();
            case 1:
                return new BillFragment();
            default:
                return new CalenderFragment();
        }
    }

    @Override
    public int getCount() { return 2;}

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title =null;
        if(position==0){
            title = "Your Calender";
        }
        if(position==1){
            title = "Calculate Bill";
        }
        return title;
    }

}
