package com.example.shaun.visitorbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayVisitors extends AppCompatActivity {

    private TextView txtDisplayVisitors;
    private Button btnDisplayVisitors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_visitors);

        //Enabling TextView field to be scrollable
        txtDisplayVisitors = findViewById(R.id.txtDisplayVisitors);
        txtDisplayVisitors.setMovementMethod(new ScrollingMovementMethod());
        btnDisplayVisitors = findViewById(R.id.btnDisplayVisitors);

        final Spinner dateSpinner = findViewById(R.id.snpSelectDate);
        String date;
        //============================

        String fullDate;
        /*
        * The below code will generate today's date in the format dd-month-yyyy e.g., 22-November-2017
        * This code is for naming the txt file with the current date. e.g., 22-November-2017.txt
        * */
        String myDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        String day = myDate.substring(0, 2);
        String month = myDate.substring(3, (myDate.length() - 5));
        String year = myDate.substring((myDate.length() - 4), myDate.length());

        //If the date is a single digit it is going to add a zero to make it like this, '01'
        if (day.length() == 1) {
            day = "0" + day;
            fullDate = day + "-" + month + "-" + year;
        } else {
            fullDate = day + "-" + month + "-" + year;
        }
        final String dataFileName = fullDate + ".txt";

        //============================

        boolean fileExist = true;
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add(0, day);
        int index = 1;
        String newDataFile = dataFileName;
        //int dt = Integer.parseInt(day);
        Date tdy = new Date();
        int today = tdy.getDate();
        File file;
        do {
            file = new File(newDataFile);
            if (file.exists()) {
                today -= 1;
                newDataFile = today + fullDate.substring(2, fullDate.length()) + ".txt";
                fileNames.add(index, today + fullDate.substring(2, fullDate.length()));
                index += 1;
                fileExist = true;
            } else {
                fileExist = false;
            }
        } while (fileExist != false);

        //============================

        // Adding dates for the date spinner
        ArrayList<String> datesSpinnerArray = new ArrayList<>();
        for (int i = 0; i <= fileNames.size() - 1; i++) {
            datesSpinnerArray.add(i, fileNames.get(i));
        }

        ArrayAdapter<String> datesArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, datesSpinnerArray);

        datesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(datesArrayAdapter);


        btnDisplayVisitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = dateSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();

                //====================

                try {
                    FileInputStream fileIn = openFileInput(dataFileName);
                    InputStreamReader inputRead = new InputStreamReader(fileIn);

                    char[] inputBuffer = new char[1000];
                    String readDataFromFile = "";
                    int charRead;

                    while ((charRead = inputRead.read(inputBuffer)) > 0) {
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        readDataFromFile += readString;
                    }
                    txtDisplayVisitors.setText(readDataFromFile);
                    fileIn.close();
                    inputRead.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
