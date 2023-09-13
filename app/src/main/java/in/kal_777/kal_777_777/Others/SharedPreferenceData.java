package in.kal_777.kal_777_777.Others;

import android.content.Context;

public class SharedPreferenceData {
    public static String SHA_PREF_NAME = "appNaam";
    public static String USER_NAME_KEY = "userNhhme";
    public static String PHONE_NUMBER_KEY = "phumber";
    public static String CLIENT_EMAIL_KEY = "ClientMail";
    public static String BANK_USER_NAME_KEY = "bUserrrrName";
    public static String B_AC_N_KEY = "bAccovvNumber";
    public static String B_IFSC_C_KEY = "bIfscccCode";
    public static String B_N_KEY = "bName";
    public static String BRANCH_ADDRESS_KEY = "branchAddddress";

    public static String P_UPI_ID_KEY = "pUPIiiId";
    public static String PP_UPI_ID_KEY = "ppUPppIId";
    public static String G_PAY_UPI_ID_KEY = "gPayUPjIId";
    public static String MAR_TXT_KEY = "mrTxt";

    public static String PHONE_NUMBER1_KEY = "phoneber1";
    public static String PHONE_NUMBER2_KEY = "phomber2";
    public static String WHATSAP_NUMBER_KEY = "whtamber";
    public static String TELEGRAM_KEY = "telegram";
    public static String REACH_US_EMAIL_KEY = "reacEmail";
    public static String POSTER_IMAGES1_KEY = "postemages1";
    public static String POSTER_IMAGES2_KEY = "posmages2";
    public static String POSTER_IMAGES3_KEY = "postmages3";

    static String SIGNIN_SUCCESS_KEY = "signinScess";
    static String LIVE_USER_KEY = "liveUser";
    static String SIGNIN_TOKEN_KEY = "Signoken";
    static String CUSTOMER_COINS_KEY = "customoins";
    static String TRANSMIT_COINS_KEY = "transmcoins";


    public static String ADD_COINS_BHIM_ID_KEY = "addcopiID";
    public static String ADD_COINS_BHIM_NAME_KEY = "addcopiName";

    public static String MIN_EXTRACT_COINS_KEY = "minextrtcoins";
    public static String MAX_EXTRACT_COINS_KEY = "maxexttcoins";
    public static String MIN_TRANSMIT_COINS_KEY = "minTranitPoints";
    public static String MAX_TRANSMIT_COINS_KEY = "maxTransfrPoints";
    public static String MIN_OFFER_SUM_KEY = "minoffemAmount";
    public static String MAX_OFFER_SUM_KEY = "maxoffersAmount";
    public static String MIN_ADD_AMOUNT_COINS_KEY = "minAddamPoints";
    public static String MAX_ADD_AMOUNT_COINS_KEY = "maxAddamPoints";
    public static String FIREBSE_ALLOW_KEY = "firebAllow";
    public static String WITHDRAW_OPEN_TIME = "withdrawOpenTime";
    public static String WITHDRAW_NOTICE = "withdrawNotice";
    public static String ADD_FUND_NOTICE = "addFundNotice";
    public static String WITHDRAW_CLOSE_TIME = "withdrawCloseTime";
    public static String APP_LINK = "appLink";
    public static String APP_NOTICE = "appNotice";
    public static String SHARE_MESSAGE = "shareMessage";

    public static String KEY_DEVELOPER_MODE = "developerMode";
    public static String KEY_DEVELOPER_PIN = "developerPin";
    public static String KEY_DEVELOPER_MOBILE = "developerMobile";
    public static String KEY_DEVELOPER_PASSWORD = "developerPassword";

    public static android.content.SharedPreferences getshrpfffrefMthd(Context context){
        return context.getSharedPreferences(SHA_PREF_NAME, Context.MODE_PRIVATE);
    }

    //setter
    public static void setDevelopementData(Context context, String KEY, String data){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, data);
        editor.apply();
    }
    public static String getDeveloperData(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }
    public static void setSharedBooleanStatus(Context context, String KEY, boolean status){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putBoolean(KEY, status);
        editor.apply();
    }
    public static boolean getSharedBooleanStatus(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getBoolean(KEY, true);
    }
    public static void setPrefrenccccrrrrreStrngData(Context context, String KEY, String data){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, data);
        editor.apply();
    }
    public static String getPrfrnnnnnceinfo(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }
    public static void setBiiiinalData(Context context, String KEY, boolean data){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putBoolean(KEY, data);
        editor.apply();
    }
    public static boolean getBinallllObject(Context context, String KEY, boolean defaultValue){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getBoolean(KEY, defaultValue);
    }

    public static void setLiveeeeeeUser(Context context, boolean status){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putBoolean(LIVE_USER_KEY, status);
        editor.apply();
    }
    public static boolean getLiveeeeUser(Context context){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getBoolean(LIVE_USER_KEY, false);
    }

    public static void setMaxMiiiiinnData(Context context, String KEY, String data ){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, data);
        editor.apply();
    }
    public static String getMaxMiiinnnObject(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }
    public static void setRegisssstttterData(Context context, String KEY, String data ){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, data);
        editor.apply();
    }
    public static String getRegistrrrrrrttationObject(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }
    public static void setBankInnnttformation(Context context, String KEY, String details){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, details);
        editor.apply();
    }
    public static String getBankkkkkObject(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }

    public static void setPosteeeeeerrImages(Context context, String  KEY, String imageUrl){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, imageUrl);
        editor.apply();
    }
    public static String getPosteeeeerImage(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }

    public static void setContaaaaactUsInfo(Context context, String KEY , String details){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, details);
        editor.apply();
    }
    public static void setString(Context context, String KEY , String data){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, data);
        editor.apply();
    }
    public static String getContaaaaaactObject(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, null);
    }
    public static void setAddAmmmmmmountUPI(Context context, String KEY, String upi){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(KEY, upi);
        editor.apply();
    }
    public static String getAddAmmmmmmountUpiId(Context context, String KEY){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(KEY, "");
    }

    public static void setTrannnnnnssssmitCoins(Context context , boolean status){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putBoolean(TRANSMIT_COINS_KEY, status);
        editor.apply();
    }

    public static void setSigniiiiinnnSuccess(Context context, boolean status){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putBoolean(SIGNIN_SUCCESS_KEY, status);
        editor.apply();
    }
    public static boolean getsignInnnnnnSuccess(Context context){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getBoolean(SIGNIN_SUCCESS_KEY,false);
    }
    public static void setSigggggninTkn(Context context, String token){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(SIGNIN_TOKEN_KEY, token);
        editor.apply();
    }
    public static String getLogiiiinInToken(Context context){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(SIGNIN_TOKEN_KEY, null);
    }
    public static void setUseeeeerCoins(Context context, String points){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.putString(CUSTOMER_COINS_KEY, points);
        editor.apply();
    }
    public static String getCustttttomerCoins(Context context){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(CUSTOMER_COINS_KEY, "0");
    }
    public static boolean getTransssssmitCoins(Context context){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getBoolean(TRANSMIT_COINS_KEY, false);
    }
    public static String getString(Context context, String key){
        android.content.SharedPreferences sharedPreferences = getshrpfffrefMthd(context);
        return sharedPreferences.getString(key, "");
    }
    public static void setCllllleaninfo(Context context){
        android.content.SharedPreferences.Editor editor = getshrpfffrefMthd(context).edit();
        editor.clear();
        editor.apply();
    }

}
