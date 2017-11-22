package com.example.shaun.visitorbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayVisitors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_visitors);

        //
        Spinner dateSpinner = findViewById(R.id.snpSelectDate);
        String date;

        // Adding dates for the date spinner
        ArrayList<Integer> datesSpinnerArray = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            datesSpinnerArray.add(i);
        }

        ArrayAdapter<Integer> datesArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, datesSpinnerArray);

        datesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(datesArrayAdapter);
        date = dateSpinner.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), date + " was selected", Toast.LENGTH_SHORT).show();
    }
}
