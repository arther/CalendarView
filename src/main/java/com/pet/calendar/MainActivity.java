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

public class MainActivity extends Activity {
    TextView showCalendar;
    Calendar selectedDate = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showCalendar = (TextView) findViewById(R.id.main_calendar);
        final Calendar calendar = Calendar.getInstance();
        showCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerCalendar datePickerCalendar = new DatePickerCalendar(MainActivity.this, new OnDateSelectListener() {
                    @Override
                    public void onSelect(int day, int month, int year) {
                        String dateText = day + "/" + (month) + "/" + year;
                    }
                }, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)+1, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
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
