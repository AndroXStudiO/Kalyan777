package in.kal_777.kal_777_777.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import in.kal_777.kal_777_777.BuildConfig;
import in.kal_777.kal_777_777.R;

public class ExclusiveActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclusive_screen);
    }
    public void rateApp(View view) {
        String url = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void logOut(View view) {
        Intent logOut= new Intent(ExclusiveActivity.this, LoginActivity.class);
        startActivity(logOut);
    }
}