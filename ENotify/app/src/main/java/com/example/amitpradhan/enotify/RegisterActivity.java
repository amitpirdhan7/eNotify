package com.example.amitpradhan.enotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMITPRADHAN on 12-Apr-15.
 */
public class RegisterActivity extends Activity implements OnItemSelectedListener
{
    public final static String VALUE = "com.example.amitpradhan.enotify.VALUE";
    public final static String COLLEGE = "com.example.amitpradhan.enotify.COLLEGE";
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.college);

       final  Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList <String>();
        categories.add("National Institute of Technology,Srinagar");
        categories.add("National Institute of Technology,Durgapur");
        categories.add("National Institute of Technology,Jaipur");
        categories.add("National Institute of Technology,Bhopal");
        categories.add("National Institute of Technology,Allahbad");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);


        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        List<String> category = new ArrayList <String>();
        category.add("Director");
        category.add("HOD");
        category.add("Professor");
        category.add("Student");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,category);

        dataAdapter1.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter1);

        Button button=(Button)findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // Perform action on click
                String value = spinner1.getSelectedItem().toString();
                String college=spinner.getSelectedItem().toString();
                if(value.equalsIgnoreCase("Student")) {

                    Intent intent = new Intent(getApplicationContext(), RegisterActivity1.class);
                    intent.putExtra(VALUE, value);
                    intent.putExtra(COLLEGE,college);
                    startActivity(intent);

                }
                if(value.equalsIgnoreCase("HOD")) {

                    Intent intent = new Intent(getApplicationContext(), RegisterActivity2.class);
                    intent.putExtra(VALUE, value);
                    intent.putExtra(COLLEGE,college);
                    startActivity(intent);

                }
                if(value.equalsIgnoreCase("Professor")) {

                    Intent intent = new Intent(getApplicationContext(), RegisterActivity2.class);
                    intent.putExtra(VALUE, value);
                     intent.putExtra(COLLEGE,college);
                    startActivity(intent);

                }
                if(value.equalsIgnoreCase("Director")) {

                    Intent intent = new Intent(getApplicationContext(), RegisterActivity3.class);
                    intent.putExtra(VALUE , value);
                    intent.putExtra(COLLEGE,college);
                    startActivity(intent);

                }
            }
        });
   }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_college, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {

        Toast.makeText(parent.getContext(),
                 parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
        {
        // TODO Auto-generated method stub

    }
}
