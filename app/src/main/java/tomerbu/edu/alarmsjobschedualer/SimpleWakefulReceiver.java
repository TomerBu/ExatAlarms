package tomerbu.edu.alarmsjobschedualer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Date;

public class SimpleWakefulReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This is the Intent to deliver to our service.
        Intent service = new Intent(context, SimpleWakefulService.class);

        // Start the service, keeping the device awake while it is launching.
        Log.i(Constants.TAG, "Starting service @ " + new Date());
        startWakefulService(context, service);
    }
}
