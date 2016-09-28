package tomerbu.edu.alarmsjobschedualer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_ALARM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheTimeAndSetAlarm();
            }
        });
    }

    private void getTheTimeAndSetAlarm() {
        final DateTime now = DateTime.now();
        final int hour = now.getHourOfDay();
        int min = now.getMinuteOfHour();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DateTime userDateTime =
                                now.withHourOfDay(hourOfDay).withMinuteOfHour(minute);
                        setAlarm(userDateTime);
                    }
                }, hour, min, true);

        timePickerDialog.show();
    }

    void setAlarm(DateTime userDateTime) {
        AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, SimpleWakefulReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,
                REQUEST_ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    userDateTime.getMillis(), pIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mgr.setExact(AlarmManager.RTC_WAKEUP, userDateTime.getMillis(), pIntent);
        } else {
            mgr.set(AlarmManager.RTC_WAKEUP, userDateTime.getMillis(), pIntent);
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
