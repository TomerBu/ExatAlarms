package tomerbu.edu.alarmsjobschedualer;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;

import org.joda.time.DateTime;

public class SimpleWakefulService extends IntentService {
    public SimpleWakefulService() {
        super("SimpleWakefulService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // At this point SimpleWakefulReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.
        // Note that when using this approach you should be aware that if your
        // service gets killed and restarted while in the middle of such work
        // (so the Intent gets re-delivered to perform the work again), it will
        // at that point no longer be holding a wake lock since we are depending
        // on SimpleWakefulReceiver to that for us.  If this is a concern, you can
        // acquire a separate wake lock here:
        /**
         PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
         WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
         "MyWakelockTag");
         wakeLock.acquire();

         //Do work...

         wakeLock.release();
         */

        MediaPlayer player = MediaPlayer.create(this, R.raw.gong);
        player.start();


        for (int i = 0; i < 5; i++) {
            Log.i(Constants.TAG, "Running service " + (i + 1) + "@ " + DateTime.now().toString());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
        Log.i("SimpleWakefulReceiver", "Completed service @ " + SystemClock.elapsedRealtime());
        SimpleWakefulReceiver.completeWakefulIntent(intent);
    }
}
