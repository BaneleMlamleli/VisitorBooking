package com.example.shaun.visitorbooking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        final Spinner fileNameSpinner = findViewById(R.id.snpSelectDate);
        //============================

        /*
        * The below code will generate today's date in the format dd-month-yyyy e.g., 22-November-2017
        * This code is for naming the txt file with the current date. e.g., 22-November-2017.txt
        * */
        String myDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        String day = myDate.substring(0, 2);
        String month = myDate.substring(3, (myDate.length() - 5));
        String year = myDate.substring((myDate.length() - 4), myDate.length());
        String fullDate;
        //If the date is a single digit it is going to be padded zero to make it double digits, like this: '01'
        if (day.length() == 1) {
            day = "0" + day;
            fullDate = day + "-" + month + "-" + year;
        } else {
            fullDate = day + "-" + month + "-" + year;
        }
        final String dataFileName = fullDate + ".txt";

        //============================


        /*
        ArrayList<String> fileNames = new ArrayList<>();
        int index = 0;
        String newDataFile = dataFileName;
        Date tdy = new Date();
        int today = tdy.getDate();
        File file;
        while(today != 0){
            file = new File(newDataFile);
            if (file.exists()){
                fileNames.add(index, today + fullDate.substring(2, fullDate.length())+".txt");
                newDataFile = today + fullDate.substring(2, fullDate.length()) + ".txt";
                today -= 1;
                index += 1;
            }
        }*/

        //============================

        // Adding dates for the date spinner
        ArrayList<String> datesSpinnerArray = new ArrayList<>();
        Date dt = new Date();
        int fiveFilesOnly = 0;   //This will allow the program to only display five files
        int today = dt.getDate();
        while ((today != 0) && (fiveFilesOnly != 5)) {
            String strToday = Integer.toString(today);
            if (strToday.length() == 1) {
                strToday = "0" + strToday;
                datesSpinnerArray.add(strToday + dataFileName.substring(2, dataFileName.length()));
            } else {
                datesSpinnerArray.add(today + dataFileName.substring(2, dataFileName.length()));
            }
            today -= 1;
            fiveFilesOnly += 1;
        }

        ArrayAdapter<String> datesArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, datesSpinnerArray);

        datesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileNameSpinner.setAdapter(datesArrayAdapter);

        //============================

        //button for reading the text file and displaying
        btnDisplayVisitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the file to be read from the option in the spinner
                final String fileName = fileNameSpinner.getSelectedItem().toString();

                //====================

                try {
                    FileInputStream fileIn = openFileInput(fileName);
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
