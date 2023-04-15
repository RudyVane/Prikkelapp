package com.hfad.prikkelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Yellow extends Activity implements AsyncTaskCompleteListener{

    int number = 0;
    int eind = 0;
    String username;

    public static final String LOG_TAG = Yellow.class.getSimpleName();

    private ArrayList<String> yellowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow);
        Intent Intent = getIntent();
        username = Intent.getStringExtra("key");

        TextView textView_userText = (TextView) findViewById(R.id.textView_user);
        textView_userText.setText("");
        textView_userText.setText(username);
        yellowText = new ArrayList<>();

        updateUI();
    }

    private void updateUI() {

        AsyncTaskUrlToJson asyncTaskUrlToJson = new AsyncTaskUrlToJson(this, this);
        asyncTaskUrlToJson.execute("http://prikkelapp.mediade.eu/gettekstR95.php?name="+ username);
    }
    public void next(View view) {
        number++;
        if (number > (eind-1)) {
            number = 0;

        }
        TextView textView_yellowText = (TextView) findViewById(R.id.textView_yellowText);

        textView_yellowText.setText("");
        textView_yellowText.setText(yellowText.get(number));
    }
    private void reloadUI(){
        TextView textView_yellowText = (TextView) findViewById(R.id.textView_yellowText);

        textView_yellowText.setText("");
        textView_yellowText.setText(yellowText.get(number));
    }

    private void jsonToArray(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            yellowText.clear();

            JSONArray jsonArrayYellow = jsonObject.getJSONArray("yellowtext");
            eind= jsonArrayYellow.length();
            for (int i = 0; i < jsonArrayYellow.length(); i++) {
                JSONObject jsonObj = jsonArrayYellow.getJSONObject(i);
                yellowText.add(new String(jsonObj.getString("text")));
            }
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "jsonToArray: ", ex);
            // TODO: 14-8-2017
        }
    }

    @Override
    public void onTaskComplete(Object result) {
        String json=(String)result;
        if (json.isEmpty()) {
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            json = sharedPreferences.getString("json", null);
        }else {
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("json",json);
            editor.commit();
        }
        jsonToArray(json);
        reloadUI();
    }
}