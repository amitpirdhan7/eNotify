package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity2 extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    EditText editTextemail1,editTextUserName1,editTextPassword1,editTextConfirmPassword1,editTextPasswordhint1;
    Button btnCreateAccount1;
    Spinner spinner;
    Intent intent1;
    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_MSG = "error_msg";
    private static final String TAG_USERS = "user";
    private static final String TAG_COLLEGE = "college";
    private static final String TAG_POST = "post";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_BRANCH = "branch";
    private static final String TAG_SEMESTER = "semester";
    ArrayList<Person> detail=new ArrayList<Person>();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity2);
        //spinner for branch
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("CSE");
        categories.add("IT");
        categories.add("ECE");
        categories.add("ELE");
        categories.add("MECH");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        // Get Refferences of Views
        editTextUserName1=(EditText)findViewById(R.id.username);
        editTextemail1=(EditText)findViewById(R.id.email);
        editTextPassword1=(EditText)findViewById(R.id.password);
        editTextConfirmPassword1=(EditText)findViewById(R.id.repassword);
        editTextPasswordhint1=(EditText)findViewById(R.id.passwordh);
        btnCreateAccount1=(Button)findViewById(R.id.signup);
        intent1=getIntent();
        btnCreateAccount1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
          /*
                String userName=editTextUserName1.getText().toString();
                String email=editTextemail1.getText().toString();
                String password=editTextPassword1.getText().toString();
                String confirmPassword=editTextConfirmPassword1.getText().toString();
                String branch = spinner.getSelectedItem().toString();
                String college=intent1.getStringExtra("College");
                String post=intent1.getStringExtra("Post");*/
                new Networking3().execute();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity2, menu);
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
    public class Networking3 extends AsyncTask<Void, Void, HttpResponse>
    {

        @Override
        protected HttpResponse doInBackground(Void... params)
        {
            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost(login.url);


            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
            nameValuePairs.add(new BasicNameValuePair("tag","register"));
            nameValuePairs.add(new BasicNameValuePair("college", intent1.getStringExtra(RegisterActivity.COLLEGE)));
            nameValuePairs.add(new BasicNameValuePair("post",  intent1.getStringExtra(RegisterActivity.VALUE)));
            nameValuePairs.add(new BasicNameValuePair("name", "" +editTextUserName1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("email", "" +editTextemail1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password1", "" +editTextPassword1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password2", "" +editTextConfirmPassword1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password_hint", "" +editTextPasswordhint1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("branch", ""+spinner.getSelectedItem().toString() ));
            nameValuePairs.add(new BasicNameValuePair("semester","" ));
            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            }
            catch (UnsupportedEncodingException e)
            {
                Log.d("Register", e.getMessage());
            }
            catch (ClientProtocolException e)
            {
                Log.d("Register", e.getMessage());
            }
            catch (IOException e)
            {
                Log.d("Register", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(HttpResponse o)
        {
            if (o == null) return;
            Log.d("Register", "" + o.toString());
            String respo = GetText(o);
            Log.d("Register", respo);
            if (respo != null)
            {
                try
                {
                    JSONObject jsonObj = new JSONObject(respo);
                    Boolean error = jsonObj.getBoolean(TAG_ERROR);
                    if (error)
                    {
                        String error_msg = jsonObj.getString(TAG_ERROR_MSG);
                        Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        JSONObject users = jsonObj.getJSONObject(TAG_USERS);
                        String college = users.getString(TAG_COLLEGE);
                        String post = users.getString(TAG_POST);
                        String name = users.getString(TAG_NAME);
                        String email = users.getString(TAG_EMAIL);
                        String branch = users.getString(TAG_BRANCH);
                        String semester = users.getString(TAG_SEMESTER);
                        SharedPreferences sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedprefrence.edit();
                        editor.putString("name",name);
                        editor.putString("email",email);
                        editor.putString("post",post);
                        editor.putString("college",college);
                        editor.putString("branch",branch);
                        editor.commit();
                       // Toast.makeText(getApplicationContext(), "Registered Successful", Toast.LENGTH_SHORT).show();
                        if(post.equals("HOD"))
                        {
                            JSONArray cast = users.getJSONArray("details");

                            for (int i = 0; i < cast.length(); i++)
                            {
                                JSONObject user = cast.getJSONObject(i);
                                String name1 = user.getString("name");
                                String email1 = user.getString("email");
                                Person person=new Person(name1,email1);
                                detail.add(person);

                            }
                            Intent k = new Intent(getApplicationContext(), HODHome.class);
                            k.putExtra("detail",detail);
                            startActivity(k);
                        }
                        if(post.equals("Professor"))
                        {
                            JSONArray cast = users.getJSONArray("details");

                            for (int i = 0; i < cast.length(); i++)
                            {
                                JSONObject user = cast.getJSONObject(i);
                                String name1 = user.getString("name");
                                String email1 = user.getString("email");
                                Person person=new Person(name1,email1);
                                detail.add(person);

                            }

                            Intent l = new Intent(getApplicationContext(), ProfessorHome.class);
                            l.putExtra("detail",detail);
                            startActivity(l);
                        }
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public  String GetText(InputStream in)
        {
            String text = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try
            {
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                text = sb.toString();
            }
            catch (Exception ex)
            {

            }
            finally
            {
                try
                {

                    in.close();
                }
                catch (Exception ex)
                {
                }
            }
            return text;
        }

        public  String GetText(HttpResponse response)
        {
            String text = "";
            try
            {
                text = GetText(response.getEntity().getContent());
            }
            catch (Exception ex)
            {
            }
            return text;
        }

    }
}
