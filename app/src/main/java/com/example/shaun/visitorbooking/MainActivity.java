package com.example.shaun.visitorbooking;

import android.content.ClipData;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout menuLayout;
    private ActionBarDrawerToggle toggleBar;    //This is the button that will be pressed to show the hidden menu
    private MenuItem myMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText visitorName = findViewById(R.id.txtVisitorName);
        EditText contactNumber = findViewById(R.id.txtContactNumber);
        EditText personVisitingNumber = findViewById(R.id.txtPersonVisitingNumber);
        final EditText reason = findViewById(R.id.txtReason);

        final String name = visitorName.getText().toString();
        final String persnVisitingNum = personVisitingNumber.getText().toString();
        final String contctNum = contactNumber.getText().toString();
        final String reasn = reason.getText().toString();
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
                    Toast.makeText(getApplicationContext(), "Register visitor", Toast.LENGTH_SHORT).show();
                    Intent registerVisitorIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(registerVisitorIntent);
                    //Changing the title of the bar according to the selected option
                    getSupportActionBar().setTitle("Register visitor");
                } else {
                    if (id == R.id.displayVisitors) {
                        Toast.makeText(getApplicationContext(), "Display visitors", Toast.LENGTH_SHORT).show();
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
                        writeToFile(name, persnVisitingNum, contctNum, reasn);
                        Toast.makeText(getApplicationContext(), "Visitor registered", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggleBar.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void writeToFile(String nm, String perVisitingNum, String contNum, String rsn) {
        Toast.makeText(getApplicationContext(), "Button clicked, " + nm + ", xx", Toast.LENGTH_SHORT).show();
    }
}
