package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ProfessorPost extends ActionBarActivity
{
    public final static String SEMESTER = "com.example.amitpradhan.enotify.SEMESTER";
    Button button1,button2,button3,button4,button5,button6,button7,button8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_post);
        button1=(Button)findViewById(R.id.sem1);
        button2=(Button)findViewById(R.id.sem2);
        button3=(Button)findViewById(R.id.sem3);
        button4=(Button)findViewById(R.id.sem4);
        button5=(Button)findViewById(R.id.sem5);
        button6=(Button)findViewById(R.id.sem6);
        button7=(Button)findViewById(R.id.sem7);
        button8=(Button)findViewById(R.id.sem8);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"1");
                startActivity(i);

            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"2");
                startActivity(i);

            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"3");
                startActivity(i);

            }
        });
        button4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"4");
                startActivity(i);

            }
        });
        button5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"5");
                startActivity(i);

            }
        });
        button6.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"6");
                startActivity(i);

            }
        });
        button7.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"7");
                startActivity(i);

            }
        });
        button8.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),HODPost1.class);
                i.putExtra(SEMESTER,"8");
                startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_professor_post, menu);
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
                Intent i=new Intent(getApplicationContext(),HODProfile.class);
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
