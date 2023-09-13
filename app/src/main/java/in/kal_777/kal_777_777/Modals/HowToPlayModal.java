package in.kal_777.kal_777_777.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HowToPlayModal {

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
    public static class Data{
        @Expose
        @SerializedName("how_to_play")
        String how_to_play;

        @Expose
        @SerializedName("video_link")
        String video_link;

        public String getHow_to_play() {
            return how_to_play;
        }

        public String getVideo_link() {
            return video_link;
        }
    }
}
