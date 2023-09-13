package in.kal_777.kal_777_777.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonModal {

    @Expose
    @SerializedName("message")
    String message;

    @Expose
    @SerializedName("code")
    String code;

    @Expose
    @SerializedName("status")
    String status;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
