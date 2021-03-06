package com.example.shaun.visitorbooking;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.util.Date;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout menuLayout;
    private ActionBarDrawerToggle toggleBar;    //This is the button that will be pressed to show the hidden menu
    private MenuItem myMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText visitorName = (EditText) findViewById(R.id.txtVisitorName);
        final EditText contactNumber = (EditText) findViewById(R.id.txtContactNumber);
        final EditText personVisitingNumber = (EditText) findViewById(R.id.txtPersonVisitingNumber);
        final EditText reason = (EditText) findViewById(R.id.txtReason);

        /* Allowing the EditText field to be scrollable */
        reason.setMovementMethod(new ScrollingMovementMethod());

        final byte[] nameValue = visitorName.getText().toString().getBytes();

        final Button btnReg = findViewById(R.id.btnRegister);

        menuLayout = findViewById(R.id.drawerLayout);
        toggleBar = new ActionBarDrawerToggle(this, menuLayout, R.string.open, R.string.close);
        toggleBar.setDrawerIndicatorEnabled(true);

        menuLayout.addDrawerListener(toggleBar);
        toggleBar.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.registerVisitor) {
                    Intent registerVisitorIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(registerVisitorIntent);
                    //Changing the title of the bar according to the selected option
                    getSupportActionBar().setTitle("Register visitor");
                } else {
                    if (id == R.id.displayVisitors) {
                        Intent displayVisitorsIntent = new Intent(getApplicationContext(), DisplayVisitors.class);
                        startActivity(displayVisitorsIntent);
                        //Changing the title of the bar according to the selected option
                        getSupportActionBar().setTitle("Display visitors");
                    }
                }
                return true;
            }
        });

        btnReg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name = visitorName.getText().toString();
                        final String persnVisitingNum = personVisitingNumber.getText().toString();
                        final String contctNum = contactNumber.getText().toString();
                        final String reasn = reason.getText().toString();

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
                        String text = "Visitor:\t\t\t" + name + "\nPerson visiting:\t" + persnVisitingNum +
                                "\nVisitor number:\t\t" + contctNum + "\nReason:\t\t\t\t" + reasn;
                        String fileName = fullDate + ".txt";

                        //================

                        try {
                            FileOutputStream fileout = openFileOutput(fileName, Context.MODE_APPEND);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.append(text.toString());
                            outputWriter.append("\n");
                            outputWriter.append("\t\t\t\t* * * * * * * * * * * * * * * * * \n");
                            outputWriter.close();

                            //display file saved message
                            Toast.makeText(getBaseContext(), "File saved successfully!",
                                    Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggleBar.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}