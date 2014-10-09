package com.pet.calendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pet.calendar.R;
import com.pet.calendar.view.SquareTextView;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
    private DatePickerCalendar context;
    private final Calendar startDate;
    private final Calendar endDate;
    private final OnDateSelectListener onClickListener;
    private Calendar calendar;
    private int firstDayOfMonth;
    private int previousMonthMaxDays;
    private Calendar today = Calendar.getInstance();

    public CalendarAdapter(DatePickerCalendar context, Calendar calendar, Calendar selectedDate, Calendar toDate, OnDateSelectListener onClickListener) {
        this.calendar = calendar;
        this.context = context;
        this.startDate = selectedDate;
        this.endDate = toDate;
        this.endDate.set(Calendar.HOUR_OF_DAY, 24);
        this.endDate.set(Calendar.MINUTE, 00);
        this.endDate.set(Calendar.SECOND, 00);
        setFirstDayOfMonth();
        setPreviousMonthMax();
        this.onClickListener = onClickListener;
    }

    private void setFirstDayOfMonth() {
        Calendar tempCalendar = (Calendar) this.calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private void setPreviousMonthMax() {
        Calendar tempCalendar = (Calendar) this.calendar.clone();
        tempCalendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) + 1);
        this.previousMonthMaxDays = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return 42;
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
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.calendar_item, viewGroup, false);
        }
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        tempCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        int maxNumberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        SquareTextView date = (SquareTextView) view.findViewById(R.id.calendar_item_date);
        date.setTextColor(Color.BLACK);
        date.setBackgroundResource(R.drawable.date_back);
        view.setOnClickListener(null);

        if (position < firstDayOfMonth) {
            int value = this.previousMonthMaxDays - firstDayOfMonth + position + 1;
            tempCalendar.set(Calendar.DAY_OF_MONTH, value);
            tempCalendar.set(Calendar.MONTH, tempCalendar.get(Calendar.MONTH) - 1);
            if(isUnderSelectedDate(tempCalendar)){
                date.setBackgroundResource(R.drawable.selected_date_back);
            }
            date.setText(String.valueOf(value));
            date.setTextColor(Color.rgb(166, 166, 166));
            return view;
        }

        if (position >= firstDayOfMonth && position < maxNumberOfDays + firstDayOfMonth) {
            int value = position - firstDayOfMonth + 1;
            tempCalendar.set(Calendar.DAY_OF_MONTH, value);
            if (tempCalendar.before(this.today)) {
                date.setText(String.valueOf(value));
                date.setTextColor(Color.rgb(166, 166, 166));
                return view;
            }
            date.setText(String.valueOf(value));
            if (isUnderSelectedDate(tempCalendar)) {
                date.setBackgroundResource(R.drawable.selected_date_back);
                date.setTextColor(Color.WHITE);
            }
            setOnClickListener(view, date);
            return view;
        }

        if (position >= maxNumberOfDays + firstDayOfMonth) {
            int value = position - (maxNumberOfDays + firstDayOfMonth - 1);
            tempCalendar.set(Calendar.DAY_OF_MONTH, value);
            tempCalendar.set(Calendar.MONTH, tempCalendar.get(Calendar.MONTH) + 1);
            if(isUnderSelectedDate(tempCalendar)){
                date.setBackgroundResource(R.drawable.selected_date_back);
            }
            date.setText(String.valueOf(value));
            date.setTextColor(Color.rgb(166, 166, 166));
            return view;
        }

        date.setText("");
        return view;
    }

    private boolean isUnderSelectedDate(Calendar tempCalendar) {
        boolean isBetween = tempCalendar.after(this.startDate) && tempCalendar.before(this.endDate);
        return isBetween;
    }

    private void setOnClickListener(View view, final SquareTextView date) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dismiss();
                String selected = date.getText().toString();
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(selected));
                onClickListener.onSelect(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.setFirstDayOfMonth();
        this.setPreviousMonthMax();
    }
}
