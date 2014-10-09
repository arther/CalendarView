package com.pet.calendar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pet.calendar.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatePickerCalendar extends Dialog {

    private final OnDateSelectListener onDateSelectListener;
    private java.util.Calendar calendar;
    private int day;
    private int month;
    private int year;
    private GridView calendarGridView;
    private GridView weekDaysGridView;
    private TextView monthText;
    private TextView yearText;
    private java.util.Calendar fromDate;
    private java.util.Calendar toDate;
    private CalendarAdapter calendarAdapter;

    public DatePickerCalendar(Context context, OnDateSelectListener onDateSelectListener, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        super(context);
        this.onDateSelectListener = onDateSelectListener;
        fromDate = Calendar.getInstance();
        fromDate.set(Calendar.DAY_OF_MONTH, startDay);
        fromDate.set(Calendar.MONTH, startMonth);
        fromDate.set(Calendar.YEAR, startYear);

        toDate = Calendar.getInstance();
        toDate.set(Calendar.DAY_OF_MONTH, endDay);
        toDate.set(Calendar.MONTH, endMonth);
        toDate.set(Calendar.YEAR, endYear);

    }

    @Override
    protected void onCreate(Bundle savedStateInstance){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedStateInstance);
        setContentView(R.layout.calendar);
        this.calendar = java.util.Calendar.getInstance();

        this.calendarGridView = (GridView) findViewById(R.id.calendar_grid);
        this.weekDaysGridView = (GridView) findViewById(R.id.weekday_gridview);
        this.monthText = (TextView) findViewById(R.id.calendar_month_text);
        this.yearText = (TextView) findViewById(R.id.calendar_year_text);

        List weedDays;
        weedDays = Arrays.asList("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT");
        ArrayAdapter<String> weekDaysAdapter = new ArrayAdapter<String>(getContext(), R.layout.week_days, R.id.weekday_text, weedDays);
        this.weekDaysGridView.setAdapter(weekDaysAdapter);

        this.calendar.set(java.util.Calendar.MONTH, this.fromDate.get(java.util.Calendar.MONTH));
        this.calendar.set(java.util.Calendar.YEAR, this.fromDate.get(java.util.Calendar.YEAR));
        this.calendar.set(java.util.Calendar.DAY_OF_MONTH, this.fromDate.get(java.util.Calendar.DAY_OF_MONTH));
        this.calendarAdapter = new CalendarAdapter(this, calendar, fromDate, toDate, onDateSelectListener);
        this.calendarGridView.setAdapter(calendarAdapter);

        this.day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        this.month = calendar.get(java.util.Calendar.MONTH);
        this.year = calendar.get(java.util.Calendar.YEAR);

        setMonthYearText();
        setPreviousListener();
        setNextListener();
    }

    private void setNextListener() {
        ImageView next = (ImageView) findViewById(R.id.calendar_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNext(view);
            }
        });
    }

    private void setPreviousListener() {
        ImageView prev = (ImageView) findViewById(R.id.calendar_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPrevious(view);
            }
        });
    }

    public void onPrevious(View view) {
        if(month == 0){
            month = 12;
            calendar.set(java.util.Calendar.YEAR, calendar.get(java.util.Calendar.YEAR) - 1);
        }
        month = month - 1;
        calendar.set(java.util.Calendar.MONTH, month);
        setMonthYearText();
        calendarAdapter.notifyDataSetChanged();
    }

    public void onNext(View view) {
        if(month == 11){
            month = -1;
            calendar.set(java.util.Calendar.YEAR, calendar.get(java.util.Calendar.YEAR) + 1);
        }
        month = month + 1;
        calendar.set(java.util.Calendar.MONTH, month);
        setMonthYearText();
        calendarAdapter.notifyDataSetChanged();
    }

    private void setMonthYearText() {
        this.monthText.setText(this.calendar.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, Locale.US));
        this.yearText.setText(String.valueOf(this.calendar.get(java.util.Calendar.YEAR)));
    }
}
