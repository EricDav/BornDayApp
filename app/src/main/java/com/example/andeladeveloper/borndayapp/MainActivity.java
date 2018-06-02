package com.example.andeladeveloper.borndayapp;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private Spinner yearSpinner;
    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        year = 1800;
        month = 1;
        day = 1;
        Spinner monthSpinner = (Spinner) findViewById(R.id.monthId);
        Spinner daySpinner = (Spinner) findViewById(R.id.dayId);
        yearSpinner = (Spinner)  findViewById(R.id.yearId);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_month, android.R.layout.simple_spinner_item);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(monthAdapter);
        daySpinner.setAdapter(dayAdapter);
        monthSpinner.setOnItemSelectedListener(this);
        daySpinner.setOnItemSelectedListener(this);

        setAdapters();

    }

    /**
     * It creates and sets the adapter for the year spinner(a.k.a dropdown).
     */
    public void setAdapters() {
        List<String> list = new ArrayList<>();

        for (int i =1800; i < 2019; i++ ) {
            list.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.dayId) {
           day = Integer.parseInt(parent.getItemAtPosition(position).toString());
        } else if (spinner.getId() == R.id.monthId) {
            month = position;
        } else {
            year = Integer.parseInt(parent.getItemAtPosition(position).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * It calculates the born day and display a dialog(a.k.a modal) that shows the born day.
     *
     * @param view
     */
    public void calculateBornDay(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday","Thursday", "Friday", "Saturday" };
        String bornDay = daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        String message = "You were born on " + bornDay;

        DialogFragment dialogFragment = new BornDayDialog();
        Bundle data = new Bundle();
        data.putString("message", message);
        dialogFragment.setArguments(data);
        dialogFragment.show(getFragmentManager(), "bornDay");
    }
}
