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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity3 extends ActionBarActivity
{
    EditText editTextemail,editTextUserName,editTextPassword,editTextConfirmPassword,editTextPasswordhint;
    Button btnCreateAccount;

    Intent intent2;
    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_MSG = "error_msg";
    private static final String TAG_USERS = "user";
    private static final String TAG_COLLEGE = "college";
    private static final String TAG_POST = "post";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_BRANCH = "branch";
    private static final String TAG_SEMESTER = "semester";






    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity3);

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.username);
        editTextemail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextConfirmPassword=(EditText)findViewById(R.id.repassword);
        editTextPasswordhint=(EditText)findViewById(R.id.passwordh);
        btnCreateAccount=(Button)findViewById(R.id.signup);
         intent2=getIntent();
        btnCreateAccount.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                // TODO Auto-generated method stub

               /* String userName=editTextUserName.getText().toString();
                String email=editTextemail.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();*/

                new  Networking1().execute();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
    public class Networking1 extends AsyncTask<Void, Void, HttpResponse>
    {

        @Override
        protected HttpResponse doInBackground(Void... params)
        {
            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost(login.url);

               // Log.d("intent",intent2.getStringExtra(RegisterActivity.COLLEGE));
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
            nameValuePairs.add(new BasicNameValuePair("tag","register"));
            nameValuePairs.add(new BasicNameValuePair("college", intent2.getStringExtra(RegisterActivity.COLLEGE)));
            nameValuePairs.add(new BasicNameValuePair("post",  intent2.getStringExtra(RegisterActivity.VALUE)));
            nameValuePairs.add(new BasicNameValuePair("name", "" +editTextUserName.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("email", "" +editTextemail.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password1", "" +editTextPassword.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password2", "" +editTextConfirmPassword.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password_hint", "" +editTextPasswordhint.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("branch", "" ));
            nameValuePairs.add(new BasicNameValuePair("semester","" ));
            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            }
            catch (UnsupportedEncodingException e)
            {
                Log.d("Register",e.getMessage());
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
            if(o == null) return;
            Log.d("Register", "" + o.toString());
            String respo=GetText(o);
            Log.d("Register",respo);
           if(respo!=null)
            {
                try
                {
                    JSONObject jsonObj=new JSONObject(respo);
                     Boolean error=jsonObj.getBoolean(TAG_ERROR);
                    if(error)
                    {
                      String  error_msg =jsonObj.getString(TAG_ERROR_MSG);
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
                        //Toast.makeText(getApplicationContext(),"Registered Successful",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedprefrence.edit();
                        editor.putString("name",name);
                        editor.putString("email",email);
                        editor.putString("post",post);
                        editor.putString("college",college);
                        editor.commit();
                        Intent j = new Intent(getApplicationContext(), DirectorHome.class);
                        startActivity(j);

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


