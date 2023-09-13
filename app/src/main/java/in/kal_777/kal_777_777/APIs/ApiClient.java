package in.kal_777.kal_777_777.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final ApiClient instance = null;
    private static ApiInterface retrofit;
    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public static ApiInterface getClient() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build().create(ApiInterface.class);
        }
        return retrofit;
    }
}
