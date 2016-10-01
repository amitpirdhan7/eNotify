package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class StudentHome extends ActionBarActivity {
    ListView listView;
    String text;
    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;


    String ab;

    Intent intent;
    ArrayList<Person> test ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        test=(ArrayList<Person>)getIntent().getSerializableExtra("detail");
        listView = (ListView) findViewById(R.id.Listview01);

        listItems = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        for(int i=0;i<test.size();i++)
        {
            Person person= test.get(i);
            Log.d("ProfessorHome", person.getName());
            listItems.add(person.getName());

            ab=person.getEmail();
            adapter.notifyDataSetChanged();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id)

            {

                Person person=test.get(position);
                String value = listView.getItemAtPosition(position).toString();
                Intent i=new Intent(getApplicationContext(),HODReceive.class);
                i.putExtra("email",person.getEmail());
                startActivity(i);

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {

            case R.id.action_profile:
                Intent i=new Intent(getApplicationContext(),StudentProfile.class);
                startActivity(i);
                return true;
            case R.id.action_logout:
                SharedPreferences sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedprefrence.edit();
                editor.clear();
                editor.commit();
                Intent j=new Intent(getApplicationContext(),login.class);
                j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(j);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
