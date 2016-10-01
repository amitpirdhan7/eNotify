package com.example.amitpradhan.enotify;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HODReceive extends ActionBarActivity
{
    ListView listView;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Intent intent2;

    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_MSG = "error_msg";
    private static final String TAG_Detail = "detail";

    private GridView gridView = null;
    //private ArrayAdapter gridadapter;
    HorizontalScrollView hs;
    ArrayList<String> Imagesitems=new ArrayList<String>();
    //public static final ArrayList<Drawable> draw=new ArrayList<Drawable>();
   ImageView imageView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodreceive);


        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        //imageview=(ImageView)findViewById(R.id.image);

        listView.setAdapter(adapter);
        new Networking().execute();
        new Networking1().execute();
        gridView = (GridView) findViewById(R.id.gridview);
        hs = (HorizontalScrollView) findViewById(R.id.scroller);

        gridView.setAdapter(new ImageAdapter(this));

    }

    public class Networking extends AsyncTask<Void, Void, HttpResponse> {


    @Override
    protected HttpResponse doInBackground(Void... params) {

        intent2 = getIntent();
        String email1 = intent2.getStringExtra("email");

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(login.url2);


        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("tag", "" + "receivemsg"));
        nameValuePairs.add(new BasicNameValuePair("email", "" + email1));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            // log exception
            Log.d("hodreceive", e.getMessage());
        }
        try {
            HttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("hodreceive", e.getMessage());
        } catch (IOException e) {
            // Log exception
            Log.d("hodreceive", e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(HttpResponse o) {
        if (o == null) return;
        Log.d("hodreceive", "" + o.toString());
        String respo = GetText(o);
        Log.d("hodreceive", respo);
        if (respo != null) {
            try {
                JSONObject jsonObj = new JSONObject(respo);


                Boolean error = jsonObj.getBoolean(TAG_ERROR);
                if (error) {
                    String error_msg = jsonObj.getString(TAG_ERROR_MSG);
                    Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray cast = jsonObj.getJSONArray("detail");

                    for (int i = 0; i < cast.length(); i++) {
                        JSONObject user = cast.getJSONObject(i);
                        String text1 = user.getString("text");
                        listItems.add(text1);
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
   public class Networking1 extends AsyncTask<Void, Void, HttpResponse>
   {


        @Override
        protected HttpResponse doInBackground(Void... params) {

            intent2 = getIntent();
            String email1 = intent2.getStringExtra("email");

            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost(login.url3);


            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("tag", "" + "receivemsg"));
            nameValuePairs.add(new BasicNameValuePair("email", "" + email1));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                // log exception
                Log.d("hodreceive", e.getMessage());
            }
            try {
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("hodreceive", e.getMessage());
            } catch (IOException e) {
                // Log exception
                Log.d("hodreceive", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(HttpResponse o) {
            if (o == null) return;
            Log.d("hodreceive", "" + o.toString());
            String respo = GetText(o);
            Log.d("hodreceive", respo);
            if (respo != null) {
                try {
                    JSONObject jsonObj = new JSONObject(respo);


                    Boolean error = jsonObj.getBoolean(TAG_ERROR);
                    if (error) {
                        String error_msg = jsonObj.getString(TAG_ERROR_MSG);
                        Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT).show();
                    } else
                    {
                        JSONArray cast = jsonObj.getJSONArray("paths");

                        for (int i = 0; i < cast.length(); i++)
                        {
                            JSONObject user = cast.getJSONObject(i);
                            String path1 = user.getString("path");
                            String url="http://10.10.10.227/upload_test/"+path1;
                            Log.d("url",url);
                            Imagesitems.add(url);
                           /* try
                            {
                                URL thumb_u = new URL(url);
                              Drawable  thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                                  //draw.add(thumb_d);
                                imageview.setImageDrawable(thumb_d);

                            }
                            catch(Exception e)
                            {
                                //Log.d("hodreceive", e.getMessage());
                                String msg = (e.getMessage()==null)?"Error in Retrieving!":e.getMessage();
                                Log.i("hodreceive",msg);
                            }*/

                         }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public class ImageAdapter extends BaseAdapter implements ListAdapter {
        private Context context;

        public ImageAdapter(Context context) {

            this.context = context;


        }

        public int getCount() {
            return Imagesitems.size();

        }

        public Object getItem(int position) {

            return Imagesitems.get(position);
        }

        public long getItemId(int position) {

            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
           // ImageView imageView;
            if (convertView == null)
            {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (ImageView) convertView;

            }
            new LoadImageFromURL().execute(Imagesitems.get(position));
            return imageView;
        }
    }
        private  class LoadImageFromURL extends AsyncTask<String,Void,Drawable>
        {

            @Override
            protected Drawable doInBackground(String... params)
            {
                try
                {
                    InputStream is = (InputStream) new URL(params[0]).getContent();
                    Drawable d = Drawable.createFromStream(is, "src");
                    return d;
                }catch (Exception e) {
                    System.out.println(e);
                    return null;
                }
            }
            protected void onPostExecute(Drawable o)
            {
                imageView.setImageDrawable(o);
            }

        }
       /* private Drawable LoadImageFromURL(String url)

        {

            try
            {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src");
                return d;
            }catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }*/



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_hodreceive, menu);
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

        public String GetText(InputStream in) {
            String text = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                text = sb.toString();
            } catch (Exception ex) {

            } finally {
                try {

                    in.close();
                } catch (Exception ex) {
                }
            }
            return text;
        }

        public String GetText(HttpResponse response) {
            String text = "";
            try {
                text = GetText(response.getEntity().getContent());
            } catch (Exception ex) {
            }
            return text;
        }

}
