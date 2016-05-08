package com.study.joomagg.dbconnection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by walk1 on 2016-05-06.
 */

public class DBConnection {
    private String url_s;   // url for connection.
    private String debug = "debug"; // debugging tag.
    private String data;    // data before converted.

    public DBConnection(String url) {
        this.url_s = url;
    }

    public void getFriendship()
    {
        getData();
    }

    // references from http://webnautes.tistory.com/828
    private void getData()
    {
        class GetData extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params)
            {
                try
                {
                    URL url = new URL(url_s);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    /* read server response */
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line+"\n");
                        break;
                    }
                    return sb.toString().trim();
                }
                catch (IOException e)
                {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result)
            {
                data = result;
                convert();
            }
        }

        GetData task = new GetData();
        task.execute();
    }

    private void convert()
    {
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray friend = jsonObj.getJSONArray("result");
            for (int i=0; i<friend.length(); i++) {
                JSONObject c = friend.getJSONObject(i);
                String friender = c.getString("friender");
                String friendee = c.getString("friendee");
                HashMap<String, String> persons = new HashMap<String, String>();
                persons.put("friender", friender);
                persons.put("friendee", friendee);

                Log.d(debug, friender);
                Log.d(debug, friendee);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}