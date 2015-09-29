package sk.mpage.androidsample.lifecycle;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public static final String key = "lifecycle";
    public static final String[] methods = {
            "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy", "onRestart", "onSaveInstanceState"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = (TextView) findViewById(R.id.myTextView);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String textViewText = sharedPref.getString(key, "");

        textView.setText(textViewText);

        setLifeCycleLog(methods[0]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        setLifeCycleLog(methods[1]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        setLifeCycleLog(methods[2]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        setLifeCycleLog(methods[3]);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        setLifeCycleLog(methods[4]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        setLifeCycleLog(methods[5]);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, textView.getText().toString());
        editor.commit();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        // The activity is about to be restarted.
        setLifeCycleLog(methods[6]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setLifeCycleLog(methods[7]);
    }

    public void setLifeCycleLog(String text){
        String formattedDate = getDateTime();
        this.textView.append("\n" + text + ": " + formattedDate);
        Log.d("lifeCycleLog", text + ": " + formattedDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_clear:
                clearLog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearLog(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.commit();
        textView.setText("");
    }

    public String getDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return df.format(c.getTime());
    }

}
