package com.hfad.prikkelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    String name;
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Intent = getIntent();
        username = Intent.getStringExtra("key");
        TextView textView_userText = (TextView) findViewById(R.id.textView_user);
        textView_userText.setText("");
        textView_userText.setText(username);
        SharedPreferences prefs = this.getSharedPreferences("User", Context.MODE_PRIVATE);

        name = prefs.getString("LOGIN_ID", "");



    }

    public void Green(View view) {

        Intent Intent = new Intent(MainActivity.this, Green.class);
        Intent.putExtra("key", name); //Optional parameters
        MainActivity.this.startActivity(Intent);
    }

    public void Yellow(View view) {
        Intent Intent = new Intent(MainActivity.this, Yellow.class);
        Intent.putExtra("key", name); //Optional parameters
        MainActivity.this.startActivity(Intent);

    }

    public void Red(View view) {
        Intent Intent = new Intent(MainActivity.this, Red.class);
        Intent.putExtra("key", name); //Optional parameters
        MainActivity.this.startActivity(Intent);
    }

    public void user(View view) {
        String User = "nieuw";
        Intent Intent = new Intent(MainActivity.this, LoginActivity.class);
        Intent.putExtra("gebruiker", User);
        MainActivity.this.startActivity(Intent);
    }
}

