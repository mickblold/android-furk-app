package com.simple.furk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String scheme = getIntent().getScheme();

        if(scheme != null && scheme.equals("furk-cmd"))
        {
            if(getIntent().getDataString().equals("furk-cmd://logout"))
            {
                preferences.edit().remove("api_key").commit();
            }
        }

        String apiKey = preferences.getString("api_key","");

        if(apiKey.isEmpty())
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,1);
        }
        else {
            APIRequest.setAPIKEY(apiKey);
            Intent intent = new Intent(this,Furk.class);
            intent.setData(getIntent().getData());
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1 && resultCode == 200)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String apiKey = preferences.getString("api_key","");
            APIRequest.setAPIKEY(apiKey);
            Intent intent = new Intent(this,Furk.class);
            intent.setData(getIntent().getData());
            startActivity(intent);
            finish();
        }
        else
        {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
