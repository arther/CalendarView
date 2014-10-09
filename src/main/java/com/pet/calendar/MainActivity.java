package com.pet.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.pet.calendar.adapter.DatePickerCalendar;
import com.pet.calendar.adapter.OnDateSelectListener;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity {
    TextView fromDate;
    Calendar selectedDate = Calendar.getInstance();
    private TextView toDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fromDate = (TextView) findViewById(R.id.from_date);
        toDate = (TextView) findViewById(R.id.to_date);
        final Calendar fromCalendar = Calendar.getInstance();
        final Calendar toCalendar = Calendar.getInstance();
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerCalendar datePickerCalendar = new DatePickerCalendar(MainActivity.this, new OnDateSelectListener() {
                    @Override
                    public void onSelect(int day, int month, int year) {
                        fromCalendar.set(Calendar.DAY_OF_MONTH, day);
                        fromCalendar.set(Calendar.MONTH, month);
                        fromCalendar.set(Calendar.YEAR, year);
                        String dateText = day + " " + fromCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + year;
                        fromDate.setText(dateText);
                    }
                }, fromCalendar.get(Calendar.DAY_OF_MONTH), fromCalendar.get(Calendar.MONTH), fromCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.DAY_OF_MONTH), toCalendar.get(Calendar.MONTH), toCalendar.get(Calendar.YEAR));
                datePickerCalendar.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerCalendar datePickerCalendar = new DatePickerCalendar(MainActivity.this, new OnDateSelectListener() {
                    @Override
                    public void onSelect(int day, int month, int year) {
                        toCalendar.set(Calendar.DAY_OF_MONTH, day);
                        toCalendar.set(Calendar.MONTH, month);
                        toCalendar.set(Calendar.YEAR, year);
                        String dateText = day + " " + toCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + year;
                        toDate.setText(dateText);
                    }
                }, fromCalendar.get(Calendar.DAY_OF_MONTH), fromCalendar.get(Calendar.MONTH), fromCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.DAY_OF_MONTH), toCalendar.get(Calendar.MONTH), toCalendar.get(Calendar.YEAR));
                datePickerCalendar.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                this.selectedDate = (Calendar) data.getExtras().get("SELECTED_DATE");
                String selectedDate = this.selectedDate.get(Calendar.DAY_OF_MONTH) + "/" + (this.selectedDate.get(Calendar.MONTH) + 1) + "/" + this.selectedDate.get(Calendar.YEAR);
                Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
