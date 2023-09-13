package in.kal_777.kal_777_777.Others;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import static in.kal_777.kal_777_777.Others.NetBroadCastClass.BCStrForAction;

public class InternetService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("","");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not yet implement");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(priodicUpdate);
        return START_STICKY;
    }

    public static boolean isOnline(Context c){
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    Handler handler = new Handler();
    private final Runnable priodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(priodicUpdate, 1*1000- SystemClock.elapsedRealtime()%1000);
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(BCStrForAction);
            broadCastIntent.putExtra("online_status", ""+isOnline(InternetService.this));
            sendBroadcast(broadCastIntent);
        }
    };

}