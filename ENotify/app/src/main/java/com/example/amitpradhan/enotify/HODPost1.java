package com.example.amitpradhan.enotify;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HODPost1 extends ActionBarActivity implements View.OnClickListener
{
    private ArrayList<String> mSelectedPhotos = new ArrayList<String>();
    private GridView gridView = null;
    private ArrayAdapter mListViewAdapter;
    HorizontalScrollView hs;
    ProgressDialog progressDialog = null;
    EditText editText;
    Intent intent2;
    Button addButton;
    int serverResponseCode = 0;
    ListView listView;
    String text;
    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;
    SharedPreferences sharedprefrence;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodpost1);
        gridView = (GridView) findViewById(R.id.listview);
        hs = (HorizontalScrollView) findViewById(R.id.scroller);
        mListViewAdapter = new SelectedItemListAdapter(getApplicationContext(),
                mSelectedPhotos);
        gridView.setAdapter(mListViewAdapter);
        // findViewById(R.id.select).setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

        addButton = (Button) findViewById(R.id.addItem);

        listView = (ListView) findViewById(R.id.listView);

        listItems = new ArrayList<String>();

        listItems.add("Welcome to eNotify application");

        // ADAPTER
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);

        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // TODO Auto-generated method stub

                listItems.add(editText.getText().toString());
                text=editText.getText().toString();
                new Networking().execute();
				/*
				 *
				 * notifyDataSetChanged() notifies the attached observers that
				 * the underlying data has
				 *
				 * been changed and any View reflecting the data set should
				 * refresh itself
				 */

                adapter.notifyDataSetChanged();

                editText.setText("");

            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id)

            {

                String value = listView.getItemAtPosition(position).toString();
                Toast.makeText(HODPost1.this, value, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }
    public class Networking extends AsyncTask<Void,Void,HttpResponse>
    {


        @Override
        protected HttpResponse doInBackground(Void... params) {
            sharedprefrence = getSharedPreferences("Profile", Context.MODE_PRIVATE);
            String email1 = sharedprefrence.getString("email", "");
            String post1 = sharedprefrence.getString("post", "");
            String college1=sharedprefrence.getString("college","");
            String branch1=sharedprefrence.getString("branch","");
            intent2 = getIntent();
            String semester = intent2.getStringExtra(HODPost.SEMESTER);

            HttpClient httpClient = new DefaultHttpClient();
            // replace with your url
            HttpPost httpPost = new HttpPost(login.upLoadServerUri);


            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            nameValuePairs.add(new BasicNameValuePair("tag", "" + "textmsg"));
            nameValuePairs.add(new BasicNameValuePair("email", ""+email1));
            nameValuePairs.add(new BasicNameValuePair("post", "" + post1));
            nameValuePairs.add(new BasicNameValuePair("college", "" + college1));
            nameValuePairs.add(new BasicNameValuePair("branch", "" + branch1));
            nameValuePairs.add(new BasicNameValuePair("semester", "" +semester ));
            nameValuePairs.add(new BasicNameValuePair("text", "" + text));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                // log exception
                Log.d("hodpost1", e.getMessage());
            }
            try {
                HttpResponse response = httpClient.execute(httpPost);
                return response;
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("hodpost1", e.getMessage());
            } catch (IOException e) {
                // Log exception
                Log.d("hodpost1", e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(HttpResponse o)
        {
            if (o == null) return;
            Log.d("directorpost", "" + o.toString());
            String respo = GetText(o);
            Log.d("directorpost", respo);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if (resultCode == RESULT_OK)
            {

                ArrayList<String> result = data
                        .getStringArrayListExtra(MultiSelectGallery.KEY_ARRAYLIST_SELECTED_PHOTOS);
                mSelectedPhotos.clear();
                if (result == null)
                {
                    return;
                }

                Iterator<String> iterator = result.iterator();
                while (iterator.hasNext())
                {
                    final String path = (String) iterator.next();
                    if (!mSelectedPhotos.contains(path))
                    {
                        mSelectedPhotos.add(path);
                        new Thread(new Runnable() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"uploading started",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                int response= uploadFile(path);
                                System.out.println("RES : " + response);
                            }
                        }).start();
                    }

                }
                mListViewAdapter.notifyDataSetChanged();

            }

        }
        catch (Exception e)
        {

        }

    }

    int uploadFile(String sourceFileUri)
    {
        sharedprefrence=getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String email1=sharedprefrence.getString("email","");
        String post1=sharedprefrence.getString("post","");
        String college1=sharedprefrence.getString("college","");
        String branch1=sharedprefrence.getString("branch","");
        intent2=getIntent();
        String semester=intent2.getStringExtra(HODPost.SEMESTER);

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return 0;
        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(login.upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            //conn.setRequestProperty("email",email1);
            // conn.setRequestProperty("branch",branch);
            //conn.setRequestProperty("post",post1);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter name

            dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(email1);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter name

            dos.writeBytes("Content-Disposition: form-data; name=\"post\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(post1);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter name

            dos.writeBytes("Content-Disposition: form-data; name=\"college\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(college1);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter name

            dos.writeBytes("Content-Disposition: form-data; name=\"branch\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(branch1);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter name

            dos.writeBytes("Content-Disposition: form-data; name=\"semester\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(semester);
            dos.writeBytes(lineEnd);


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if(serverResponseCode == 200){
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            // dialog.dismiss();
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            // dialog.dismiss();
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
        }
        //dialog.dismiss();
        return serverResponseCode;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hodpost1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.gallery:
                Intent intent = new Intent(this, MultiSelectGallery.class);
                intent.putExtra(MultiSelectGallery.KEY_ARRAYLIST_SELECTED_PHOTOS,
                        mSelectedPhotos);
                startActivityForResult(intent, 100);
                hs.setVisibility(hs.VISIBLE);
                break;

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
    public class SelectedItemListAdapter extends ArrayAdapter<String> {

        private DisplayImageOptions mDisplayImageOptions = null;
        private ImageLoader mImageLoader;

        public SelectedItemListAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
            createImageLoader();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.listview_image_view, null);
                convertView.setTag(new ListViewHolder((ImageView) convertView
                        .findViewById(R.id.thumb), (TextView) convertView
                        .findViewById(R.id.path)));
            }

            convertView.setTag(new ListViewHolder((ImageView) convertView
                    .findViewById(R.id.thumb), (TextView) convertView
                    .findViewById(R.id.path)));

            ListViewHolder holder = (ListViewHolder) convertView.getTag();

            holder.path.setText(getItem(position));
            mImageLoader.displayImage("file://" + getItem(position),
                    holder.thumb, mDisplayImageOptions);
            return convertView;
        }

        public void createImageLoader() {

            DisplayImageOptions.Builder defaultOptionsBuilder = new DisplayImageOptions.Builder()//
                    .cacheOnDisc(true)//
                    .cacheInMemory(true)//
                    .delayBeforeLoading(0)//
                    .considerExifParams(true)//
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//
                    .bitmapConfig(Bitmap.Config.RGB_565)//
                    ;//

            mDisplayImageOptions = defaultOptionsBuilder.build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    getApplicationContext())//
                    .defaultDisplayImageOptions(mDisplayImageOptions)//
                    .memoryCacheSizePercentage(25) // default
                    .discCacheSize(50 * 1024 * 1024);//
            ImageLoaderConfiguration config = builder.build();

            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(config);

        }

    }

    public static class ListViewHolder
    {
        public ListViewHolder(ImageView thumb, TextView path) {
            this.thumb = thumb;
            this.path = path;
        }

        public ImageView thumb;
        public TextView path;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

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
