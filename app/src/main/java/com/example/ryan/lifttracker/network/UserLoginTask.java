package com.example.ryan.lifttracker.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ryan.lifttracker.MainActivity;
import com.example.ryan.lifttracker.constants.Constants;
import com.example.ryan.lifttracker.interfaces.LogInScreenInteraction;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * Created by Andrey on 3/1/2017.
 */

public class UserLoginTask extends AsyncTask<String, Void, String> {

    private final String username;
    private final String password;
    private Context context;
    private LogInScreenInteraction activity;

    public UserLoginTask(Context context, String username, String password) {
        this.username = username;
        this.password = password;
        this.context=context;
        this.activity = (LogInScreenInteraction)context;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = Constants.STATUS_OFFLINE;
        try {
            // Send the entered username and password to the server and check for success

         publishProgress();
            Thread.sleep(500);
            result = attemptLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

        activity.NetworkingFlagUpdate(true);
      return;
    }
    @Override
    protected void onPostExecute(String result) {

        activity.NetworkingFlagUpdate(false);
        activity.LoginStatus(result);


    }

    @Override
    protected void onCancelled() {

    }

    private void saveInSharedPreferences(String result) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();




        editor.putString("sessionid", result);
        editor.putString("username", username);
        editor.putString("password", password);

        editor.apply();
    }

    private String attemptLogin() throws IOException {
        InputStream is = null;
        String cookie = "empty cookie";
        String result="relogin";

        try {
            System.setProperty("http.keepAlive", "false");
            Log.d("FitEx", "Creating connection to server for logging in.. url"+ context.getString(R.string.login_url)+ " username:"+ username +" password:"+ password);
            HttpURLConnection conn = (HttpURLConnection) ((new URL(
                    context.getString(R.string.login_url)).openConnection()));
            conn.setDoOutput(true);
            conn.setReadTimeout(MainActivity.READ_TIMEOUT_MS /* milliseconds */);
            conn.setConnectTimeout(MainActivity.CONNECT_TIMEOUT_MS /* milliseconds */);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.connect();

            JSONObject credentials = new JSONObject();
            credentials.put("username", username);
            credentials.put("password", password);

            Log.d("FitEx", "Sending login credentials to server... url"+ context.getString(R.string.login_url)+ " username:"+ username +" password:"+ password);
            Writer osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(credentials.toString());
            osw.flush();
            osw.close();

            final int HttpResultCode = conn.getResponseCode();
            is = HttpResultCode >= 400 ? conn.getErrorStream() : conn.getInputStream();

            Log.d("FitEx", "Reposne is: " + HttpResultCode);
            if (HttpResultCode == HttpURLConnection.HTTP_OK) {
                Map<String, List<String>> headerFields = conn.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(context.getString(R.string.cookies_header));
                cookie = cookiesHeader.get(0).substring(0, cookiesHeader.get(0).indexOf(";"));
                saveInSharedPreferences(cookie);
                result =Constants.STATUS_LOGGED_IN;
            } else if(HttpResultCode==401){
                Log.d("FitEx", "Did not receive HTTP_OK from server!:"+401);
                result=Constants.STATUS_RELOGIN;
            }else{
                result=Constants.STATUS_OFFLINE;
                Log.d("FitEx", "Did not receive HTTP_OK from server!--other");

            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (is != null) {
                is.close();
            }

        }

        return result;
    }
}