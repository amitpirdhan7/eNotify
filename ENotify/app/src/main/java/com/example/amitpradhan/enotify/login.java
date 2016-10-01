package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class login extends ActionBarActivity
{
    public static final String url="http://10.10.10.227/android_login_api/index.php";
    public static final String upLoadServerUri = "http://10.10.10.227/upload_test/upload_media_test.php";
    public static final String url1="http://10.10.10.227/forgot.php";
    public static final String url2 = "http://10.10.10.227/upload_test/receive_media_test.php";
    public static final String url3 = "http://10.10.10.227/upload_test/receive_media_test1.php";
    public String post1;

    EditText editTextemail,editTextPassword;
    Button btnSignIn;
   // public static String post1;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView forgot = (TextView) findViewById(R.id.forget);
        forgot.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Forgot.class);
                startActivity(i);
            }
        });

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });


        // get the Refferences of views
         editTextemail = (EditText)findViewById(R.id.email);
         editTextPassword = (EditText)findViewById(R.id.password);
         btnSignIn = (Button)findViewById(R.id.btnLogin);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                new Networking().execute();

            }


        });
    }


    public class Networking extends AsyncTask<Void,Void,HttpResponse>
    {


        @Override
        protected HttpResponse doInBackground(Void... params)
        {
            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost(url);


                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("tag",""+"login"));
                nameValuePairs.add(new BasicNameValuePair("email", "" +editTextemail.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", "" + editTextPassword.getText().toString()));
               try
               {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
               }
               catch (UnsupportedEncodingException e)
               {
                   // log exception
                   Log.d("Login", e.getMessage());
               }
            try
            {
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            }
            catch (ClientProtocolException e)
            {
                // Log exception
                Log.d("Login", e.getMessage());
            }
            catch (IOException e)
            {
                // Log exception
                Log.d("Login", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(HttpResponse o)
        {
            if (o == null) return;
            Log.d("Login", "" + o.toString());
            String respo = GetText(o);
            Log.d("Login", respo);
            //store json responses
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

                        //Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        //for create a session
                        SharedPreferences sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedprefrence.edit();
                        editor.putString("name",name);
                        editor.putString("email",email);
                        editor.putString("post",post);
                        editor.putString("college",college);
                        if(post.equals("Director"))
                        {
                            Intent j = new Intent(getApplicationContext(), DirectorHome.class);
                            startActivity(j);
                        }
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
                            editor.putString("branch",branch);
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
                            editor.putString("branch",branch);
                            Intent l = new Intent(getApplicationContext(), ProfessorHome.class);
                            l.putExtra("detail",detail);
                            startActivity(l);
                        }
                        if(post.equals("Student"))
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
                            editor.putString("branch",branch);
                            editor.putString("sem",semester);
                            Intent m = new Intent(getApplicationContext(), StudentHome.class);
                            m.putExtra("detail",detail);
                            startActivity(m);
                        }
                        editor.commit();

                    }

                 }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
      /*  void getPost()
        {
            Log.d("post",post1);
        }*/

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
