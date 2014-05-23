package com.pet.calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.pet.calendar.adapter.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFrameActivity extends FragmentActivity{
    private GridView calendarGridView;
    private TextView monthText;
    private TextView yearText;
    private Calendar calendar;
    private int year, month, day;
    private CalendarAdapter calendarAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        this.calendar = Calendar.getInstance();

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        this.calendarGridView = (GridView) findViewById(R.id.calendar_grid);
        this.monthText = (TextView) findViewById(R.id.calendar_month_text);
        this.yearText = (TextView) findViewById(R.id.calendar_year_text);

        this.calendarAdapter = new CalendarAdapter(calendar);
        this.calendarGridView.setAdapter(calendarAdapter);

        setMonthYearText();

    }
    public void onPrevious(View view) {
        month = month - 1;
        calendar.set(Calendar.MONTH, month);
        setMonthYearText();
        calendarAdapter.notifyDataSetChanged();
    }

    public void onNext(View view) {
        month = month + 1;
        calendar.set(Calendar.MONTH, month);
        setMonthYearText();
        calendarAdapter.notifyDataSetChanged();
    }

    private void setMonthYearText() {
        this.monthText.setText(this.calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US));
        this.yearText.setText(String.valueOf(this.year));
    }
}
