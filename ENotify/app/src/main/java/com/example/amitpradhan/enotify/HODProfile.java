package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class HODProfile extends ActionBarActivity {

    TextView username,email,post,college,branch;
    SharedPreferences sharedprefrence;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodprofile);
        username=(TextView)findViewById(R.id.textview);
        email=(TextView)findViewById(R.id.textview1);
        post=(TextView)findViewById(R.id.textview2);
        college=(TextView)findViewById(R.id.textview3);
        branch=(TextView)findViewById(R.id.textview4);
        sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String name=sharedprefrence.getString("name","");
        String email1=sharedprefrence.getString("email","");
        String post1=sharedprefrence.getString("post","");
        String college1=sharedprefrence.getString("college","");
        String branch1=sharedprefrence.getString("branch","");
        username.setText(name);
        email.setText(email1);
        post.setText(post1);
        college.setText(college1);
        branch.setText(branch1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hodprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_logout)
        {
            editor=sharedprefrence.edit();
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
