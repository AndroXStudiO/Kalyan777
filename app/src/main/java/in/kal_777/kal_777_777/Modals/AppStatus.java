package in.kal_777.kal_777_777.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppStatus {

    @Expose
    @SerializedName("message")
    String message;

    @Expose
    @SerializedName("code")
    String code;

    @Expose
    @SerializedName("status")
    String status;

    @Expose
    @SerializedName("data")
    Data data;

    public static class Data{
        @Expose
        @SerializedName("token")
        String token;

        @Expose
        @SerializedName("mobile")
        String mobile;

        @Expose
        @SerializedName("password")
        String password;

        @Expose
        @SerializedName("pin")
        String pin;

        public String getToken() {
            return token;
        }

        public String getMobile() {
            return mobile;
        }

        public String getPassword() {
            return password;
        }

        public String getPin() {
            return pin;
        }
    }



    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
