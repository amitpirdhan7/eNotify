package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DirectorHome extends ActionBarActivity
{
    public final static String BRANCH = "com.example.amitpradhan.enotify.BRANCH";
    Button button1,button2,button3,button4,button5;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_home);
        button1=(Button)findViewById(R.id.cse);
        button2=(Button)findViewById(R.id.it);
        button3=(Button)findViewById(R.id.ec);
        button4=(Button)findViewById(R.id.ele);
        button5=(Button)findViewById(R.id.mec);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),DirectorPost.class);
                i.putExtra(BRANCH,"CSE");
                startActivity(i);

            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),DirectorPost.class);
                i.putExtra(BRANCH,"IT");
                startActivity(i);

            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),DirectorPost.class);
                i.putExtra(BRANCH,"ECE");
                startActivity(i);

            }
        });
        button4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),DirectorPost.class);
                i.putExtra(BRANCH,"ELE");
                startActivity(i);

            }
        });
        button5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent i=new Intent(getApplicationContext(),DirectorPost.class);
                i.putExtra(BRANCH,"MECH");
                startActivity(i);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_director_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
         //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/
        switch(id)
        {

            case R.id.action_profile:
                Intent i=new Intent(getApplicationContext(),DirectorProfile.class);
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
