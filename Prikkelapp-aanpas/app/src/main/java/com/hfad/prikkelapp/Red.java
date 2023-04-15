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


public class Red extends Activity implements AsyncTaskCompleteListener{

    int number = 0;
    int eind = 0;
    String username;
    public static final String LOG_TAG = Red.class.getSimpleName();


    private ArrayList<String> redText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
        Intent Intent = getIntent();
        username = Intent.getStringExtra("key");

        TextView textView_userText = (TextView) findViewById(R.id.textView_user);
        textView_userText.setText("");
        textView_userText.setText(username);
        redText = new ArrayList<>();

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
        TextView textView_redText = (TextView) findViewById(R.id.textView_redText);

        textView_redText.setText("");
        textView_redText.setText(redText.get(number));
    }
    private void reloadUI(){
        TextView textView_redText = (TextView) findViewById(R.id.textView_redText);

        textView_redText.setText("");
        textView_redText.setText(redText.get(number));
    }

    private void jsonToArray(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            redText.clear();

            JSONArray jsonArrayRed = jsonObject.getJSONArray("redtext");
            eind = jsonArrayRed.length();
            for (int i = 0; i < eind; i++) {
                JSONObject jsonObj = jsonArrayRed.getJSONObject(i);
                redText.add(new String(jsonObj.getString("text")));
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