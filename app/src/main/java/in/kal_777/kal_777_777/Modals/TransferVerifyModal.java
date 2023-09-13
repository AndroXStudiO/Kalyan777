package in.kal_777.kal_777_777.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferVerifyModal {

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
        @SerializedName("name")
        String name;

        public String getName() {
            return name;
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
