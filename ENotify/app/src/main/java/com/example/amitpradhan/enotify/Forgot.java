package com.example.amitpradhan.enotify;

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


public class Forgot extends ActionBarActivity {

    EditText editTextemail,editTextPasswordhint,editTextPassword;
    Button btnSubmit;
    // public static String post1;
    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_MSG = "error_msg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        editTextemail = (EditText)findViewById(R.id.email);
        editTextPasswordhint = (EditText)findViewById(R.id.passwordh);
        editTextPassword = (EditText)findViewById(R.id.password);
        btnSubmit = (Button)findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

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
            HttpPost httpPost = new HttpPost(login.url1);


            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("tag",""+"forgot"));
            nameValuePairs.add(new BasicNameValuePair("email", "" +editTextemail.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("passwordhint", "" +editTextPasswordhint.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", "" + editTextPassword.getText().toString()));
            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            catch (UnsupportedEncodingException e)
            {
                // log exception
                Log.d("forgot", e.getMessage());
            }
            try
            {
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            }
            catch (ClientProtocolException e)
            {
                // Log exception
                Log.d("forgot", e.getMessage());
            }
            catch (IOException e)
            {
                // Log exception
                Log.d("forgot", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(HttpResponse o)
        {
            if (o == null) return;
            Log.d("forgot", "" + o.toString());
            String respo = GetText(o);
            Log.d("forgot", respo);
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

                        Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                        //for create a session
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot, menu);
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
}
