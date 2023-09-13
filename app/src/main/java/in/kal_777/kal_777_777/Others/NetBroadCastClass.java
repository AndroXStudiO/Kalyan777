package in.kal_777.kal_777_777.Others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.material.textview.MaterialTextView;

public class NetBroadCastClass {
    public static final String BCStrForAction ="checkingInternet";
    public static MaterialTextView aStatus;

    public NetBroadCastClass(MaterialTextView aStatus) {
        NetBroadCastClass.aStatus = aStatus;
    }

    public static BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BCStrForAction)){
                if (intent.getStringExtra("online_status").equals("true")){
                    aStatus.setVisibility(View.GONE);
                }else aStatus.setVisibility(View.VISIBLE);
            }
        }
    };
}
