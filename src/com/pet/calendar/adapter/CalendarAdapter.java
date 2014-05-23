package com.pet.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pet.calendar.R;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter{
    private Calendar calendar;
    public CalendarAdapter(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public int getCount() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.calendar_item, viewGroup, false);
        }
        TextView date = (TextView) view.findViewById(R.id.calendar_item_date);
        date.setText(String.valueOf(position+1));
        return view;
    }
}
