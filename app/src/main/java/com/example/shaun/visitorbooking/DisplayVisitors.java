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

import java.io.File;
import java.io.FileInputStream;
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

        //
        final Spinner dateSpinner = findViewById(R.id.snpSelectDate);
        String date;
        //============================

        String fullDate;
    /*The below code will generate today's date in the format dd-month-yyyy e.g., 22-November-2017
     * This code is for naming the txt file with the current date. e.g., 22-November-2017.txt
     * */
        String myDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        String dd = myDate.substring(0, 2);
        String month = myDate.substring(3, (myDate.length() - 5));
        String year = myDate.substring((myDate.length() - 4), myDate.length());

        //If the date is a single digit it is going to add a zero to make it like this, '01'
        if (dd.length() == 1) {
            dd = "0" + dd;
            fullDate = dd + "-" + month + "-" + year;
        } else {
            fullDate = dd + "-" + month + "-" + year;
        }
        final String dataFileName = fullDate + ".txt";

        //============================

        boolean exst;
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add(0, dd);
        int index = 1;
        String newDataFile = dataFileName;
        int dt = Integer.parseInt(fullDate.substring(0, 2));
        File file;
        do {
            file = new File(newDataFile);
            if (file.exists()) {
                dt = dt - 1;
                newDataFile = dt + fullDate.substring(2, fullDate.length()) + ".txt";
                fileNames.add(index, dt + fullDate.substring(2, fullDate.length()));
                index += 1;
                exst = true;
            } else {
                exst = false;
            }
        } while (exst);


        //============================

        // Adding dates for the date spinner
        ArrayList<String> datesSpinnerArray = new ArrayList<>();
        for (int i = 0; i <= fileNames.size(); i++) {
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

                    final int READ_BLOCK_SIZE = 1000;

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String textFileOutput = "";
                    int charRead;

                    while ((charRead = inputRead.read(inputBuffer)) > 0) {
                        // char to string conversion
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        textFileOutput += readstring + "\n";
                    }
                    txtDisplayVisitors.setText(textFileOutput);
                    inputRead.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //====================
                /*
                File file = new File(Environment.getExternalStorageDirectory(), "22-November.txt");//fileName);
                try{
                    int len = (int) file.length();
                    byte[] bytes = new byte[len];

                    FileInputStream readFile = new FileInputStream(file);
                    readFile.read(bytes);

                    String readDate = new String(bytes);
                    txtDisplayVisitors.setText(readDate.toString());
                    readFile.close();
                    Toast.makeText(getApplicationContext(), "File read succussfully!", Toast.LENGTH_SHORT).show();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error file not found!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
