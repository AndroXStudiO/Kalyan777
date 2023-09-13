package in.kal_777.kal_777_777;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.android.volley.*;
import com.romellfudi.ussdlibrary.*;
import com.suke.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import me.everything.*;
import org.json.*;
import android.provider.Settings.Secure;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.SimSupport.androx.NetworkService.ConfiGSSL;
import com.android.volley.toolbox.HurlStack;


public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double num = 0;
	private double n = 0;
	private String [] AddBalanceSMSList;
	
	private ArrayList<String> simList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> SellerList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> OTPListMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
        private boolean bol = false;
	
	private TimerTask Timers;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
	}
	
	private void initializeLogic() {

		//Make Screen On
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_NavStatusBarColor("#ffffff", "#ffffff");
		_DARK_ICONS();
		bol = validateAppSignature(getApplicationContext(), Const.SGIN_KEY());;
		if (bol) {
			Timers = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (Const.instance().FetchVALUE("AUTH").equals("true")) {
								startActivity(new Intent(MainActivity.this, HomeActivity.class));
								finish();
							}
							else {
								if (Const.instance().FetchVALUE("PERMIT").equals("true")) {
									startActivity(new Intent(MainActivity.this, SetupHostActivity.class));
									finish();
								}
								else {
									startActivity(new Intent(MainActivity.this, PermissionActivity.class));
									finish();
								}
							}
						}
					});
				}
			};
			_timer.schedule(Timers, (int)(2000));
		}
		else {
			finishAffinity();
		}
	} android.content.pm.PackageInfo packageInfo;
	 public boolean validateAppSignature(android.content.Context context, String _signature){
		   try{
			      packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), android.content.pm.PackageManager.GET_SIGNATURES);
			   }catch(Exception e){}
		    
		  if(packageInfo != null){
			      for (android.content.pm.Signature signature : packageInfo.signatures) {
				        String sha1 = getSHA1(signature.toByteArray());
				        return (_signature.equals(sha1.toLowerCase()));
				      }
			   }
		   if(packageInfo != null){
			      return false; 
			   }else{
			     return true;
			   }
	}
	 public static String getSHA1(byte[] sig) {
		    try{
			       java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA1");
			       digest.update(sig);
			       byte[] hashtext = digest.digest();
			       return bytesToHex(hashtext);
			    }
		    catch(java.security.NoSuchAlgorithmException e){
			        return (Const.SGIN_KEY());
			    }
	}
	 public static String bytesToHex(byte[] bytes) {
		    final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			            '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		    char[] hexChars = new char[bytes.length * 2];
		    int v;
		    for (int j = 0; j < bytes.length; j++) {
			       v = bytes[j] & 0xFF;
			       hexChars[j * 2] = hexArray[v >>> 4];
			       hexChars[j * 2 + 1] = hexArray[v & 0x0F];
			    }
		    return new String(hexChars);
	}{
        }

	public void _NavStatusBarColor(final String _color1, final String _color2) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
		}
	}
	
	
	public void _DARK_ICONS() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}
	
	
	public String _device_id() {
		((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", Secure.getString(getContentResolver(), Secure.ANDROID_ID).toUpperCase()));
		return (Secure.getString(getContentResolver(), Secure.ANDROID_ID).toUpperCase());
	}
	
	
	public void _setBackground(final View _view, final double _radius, final double _shadow, final String _color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
	}
	
	
	public boolean _isJSONValid(final String _response) {
		
		    try {
			        new JSONObject(_response);
			   } catch (JSONException ex) {
			        
			       try {
				         new JSONArray(_response);
				       } catch (JSONException ex1) {
				            return false;
				       }
			   }
		    return true;
		
	}
}
