package in.kal_777.kal_777_777.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppDetailsModal {
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
        @SerializedName("banner_marquee")
        String banner_marquee;

        @Expose
        @SerializedName("contact_details")
        ContactDetails contact_details;

        @Expose
        @SerializedName("banner_image")
        BannerImages banner_image;

        @Expose
        @SerializedName("withdraw_open_time")
        String withdraw_open_time;

        @Expose
        @SerializedName("withdraw_close_time")
        String withdraw_close_time;

        @Expose
        @SerializedName("app_link")
        String app_link;

        @Expose
        @SerializedName("share_message")
        String share_message;

        @Expose
        @SerializedName("app_notice")
        String app_notice;

        @Expose
        @SerializedName("withdraw_notice")
        String withdraw_notice;

        @Expose
        @SerializedName("add_fund_notice")
        String add_fund_notice;

        public static class ContactDetails{
            @Expose
            @SerializedName("whatsapp_no")
            String whatsapp_no;

            @Expose
            @SerializedName("mobile_no_1")
            String mobile_no_1;


            @Expose
            @SerializedName("mobile_no_2")
            String mobile_no_2;

            @Expose
            @SerializedName("email_1")
            String email_1;

            @Expose
            @SerializedName("telegram_no")
            String telegram_no;


            public String getWhatsapp_no() {
                return whatsapp_no;
            }

            public String getMobile_no_1() {
                return mobile_no_1;
            }

            public String getMobile_no_2() {
                return mobile_no_2;
            }

            public String getEmail_1() {
                return email_1;
            }

            public String getTelegram_no() {
                return telegram_no;
            }
        }

        public static class BannerImages{
            @Expose
            @SerializedName("banner_img_1")
            String banner_img_1;

            @Expose
            @SerializedName("banner_img_2")
            String banner_img_2;

            @Expose
            @SerializedName("banner_img_3")
            String banner_img_3;

            public String getBanner_img_1() {
                return banner_img_1;
            }

            public String getBanner_img_2() {
                return banner_img_2;
            }

            public String getBanner_img_3() {
                return banner_img_3;
            }
        }

        public String getBanner_marquee() {
            return banner_marquee;
        }

        public ContactDetails getContact_details() {
            return contact_details;
        }

        public BannerImages getBanner_image() {
            return banner_image;
        }

        public String getWithdraw_open_time() {
            return withdraw_open_time;
        }

        public String getWithdraw_close_time() {
            return withdraw_close_time;
        }

        public String getApp_link() {
            return app_link;
        }

        public String getShare_message() {
            return share_message;
        }

        public String getApp_notice() {
            return app_notice;
        }

        public String getWithdraw_notice() {
            return withdraw_notice;
        }

        public String getAdd_fund_notice() {
            return add_fund_notice;
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
