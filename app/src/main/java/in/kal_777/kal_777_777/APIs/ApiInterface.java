package in.kal_777.kal_777_777.APIs;

import in.kal_777.kal_777_777.Modals.AppStatus;
import in.kal_777.kal_777_777.Modals.CommonModal;
import in.kal_777.kal_777_777.Modals.LoginModal;
import in.kal_777.kal_777_777.Modals.AppDetailsModal;
import in.kal_777.kal_777_777.Modals.HowToPlayModal;
import in.kal_777.kal_777_777.Modals.MainGameList;
import in.kal_777.kal_777_777.Modals.GameRateListModal;
import in.kal_777.kal_777_777.Modals.GaliWinModal;
import in.kal_777.kal_777_777.Modals.GaliGameList;
import in.kal_777.kal_777_777.Modals.StarlineWinModal;
import in.kal_777.kal_777_777.Modals.StarlineGameModel;
import in.kal_777.kal_777_777.Modals.WalletStatementModal;
import in.kal_777.kal_777_777.Modals.WinHistoryModal;
import in.kal_777.kal_777_777.Modals.TransferVerifyModal;
import in.kal_777.kal_777_777.Modals.UserStatus;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST(ApiUrl.app_live_status)
    Call<AppStatus> appLiveStatus(
            @Field("string") String appData
    );
    @FormUrlEncoded
    @POST(ApiUrl.signup)
    Call<CommonModal> signup(
            @Field("full_name") String full_name,
            @Field("mobile") String mobile,
            @Field("pin") String pin,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(ApiUrl.verify_user)
    Call<LoginModal> verify_user(
            @Field("mobile") String mobile,
            @Field("mobile_token") String mobile_token,
            @Field("otp") String otp
    );


    @FormUrlEncoded
    @POST(ApiUrl.login)
    Call<LoginModal> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(ApiUrl.login_pin)
    Call<LoginModal> login_pin(
            @Header("token") String token,
            @Field("pin") String pin
    );

    @FormUrlEncoded
    @POST(ApiUrl.user_status)
    Call<UserStatus> user_status(
            @Header("token") String token,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST(ApiUrl.forgot_password)
    Call<LoginModal> forgotPassword(
            @Field("mobile") String mobile
    );
    @FormUrlEncoded
    @POST(ApiUrl.forgot_pin)
    Call<LoginModal> forgotPin(
            @Field("mobile") String mobile
    );
    @FormUrlEncoded
    @POST(ApiUrl.resendOtp)
    Call<CommonModal> resendOtp(
            @Field("mobile") String mobile
    );
    @FormUrlEncoded
    @POST(ApiUrl.verifyOtp)
    Call<LoginModal> verifyOtp(
            @Field("mobile") String mobile,
            @Field("otp") String otp
    );
    @FormUrlEncoded
    @POST(ApiUrl.forgot_password_verify)
    Call<LoginModal> forgot_password_verify(
            @Header("token") String token,
            @Field("mobile") String mobile,
            @Field("mobile_token") String mobile_token,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST(ApiUrl.create_pin)
    Call<LoginModal> createPin(
            @Header("token") String token,
            @Field("mobile") String mobile,
            @Field("mobile_token") String mobile_token,
            @Field("pin") String pin

    );

    @FormUrlEncoded
    @POST(ApiUrl.update_profile)
    Call<LoginModal> update_profile(
            @Header("token") String token,
            @Field("email") String email,
            @Field("name") String name
    );


    @FormUrlEncoded
    @POST(ApiUrl.update_firebase_token)
    Call<CommonModal> update_firebase_token(
            @Header("token") String token,
            @Field("token_id") String token_id
    );

    @FormUrlEncoded
    @POST(ApiUrl.app_details)
    Call<AppDetailsModal> app_details(
            @Field("string") String app
    );

    @FormUrlEncoded
    @POST(ApiUrl.how_to_play)
    Call<HowToPlayModal> how_to_play(
            @Header("token") String token,
            @Field("string") String play
    );


    @FormUrlEncoded
    @POST(ApiUrl.update_phonepe)
    Call<CommonModal> update_phonepe(
            @Header("token") String token,
            @Field("phonepe") String phonepe
    );

    @FormUrlEncoded
    @POST(ApiUrl.update_bank_details)
    Call<CommonModal> update_bank_details(
            @Header("token") String token,
            @Field("account_holder_name") String account_holder_name,
            @Field("account_no") String account_no,
            @Field("ifsc_code") String ifsc_code,
            @Field("bank_name") String bank_name,
            @Field("branch_address") String branch_address
    );

    @FormUrlEncoded
    @POST(ApiUrl.get_user_details)
    Call<LoginModal> get_user_details(
            @Header("token") String token,
            @Field("string") String user
    );

    @FormUrlEncoded
    @POST(ApiUrl.update_gpay)
    Call<CommonModal> update_gpay(
            @Header("token") String token,
            @Field("gpay") String gpay
    );

    @FormUrlEncoded
    @POST(ApiUrl.update_paytm)
    Call<CommonModal> update_paytm(
            @Header("token") String token,
            @Field("paytm") String paytm
    );


    @FormUrlEncoded
    @POST(ApiUrl.add_fund)
    Call<CommonModal> add_fund(
            @Header("token") String token,
            @Field("points") String points,
            @Field("trans_status") String trans_status,
            @Field("trans_id") String trans_id,
            @Field("payment_app") String payment_app
    );


    @FormUrlEncoded
    @POST(ApiUrl.transfer_verify)
    Call<TransferVerifyModal> transfer_verify(
            @Header("token") String token,
            @Field("user_number") String user_number
    );


    @FormUrlEncoded
    @POST(ApiUrl.main_game_list)
    Call<MainGameList> main_game_list(
            @Header("token") String token,
            @Field("game") String game
    );

    @FormUrlEncoded
    @POST(ApiUrl.wallet_statement)
    Call<WalletStatementModal> wallet_statement(
            @Header("token") String token,
            @Field("state") String state
    );

    @FormUrlEncoded
    @POST(ApiUrl.place_bid)
    Call<CommonModal> place_bid(
            @Header("token") String token,
            @Field("game_bids") String game_bids
    );
    @FormUrlEncoded
    @POST(ApiUrl.game_rate_list)
    Call<GameRateListModal> game_rate_list(
            @Header("token") String token,
            @Field("list") String list
    );

    @FormUrlEncoded
    @POST(ApiUrl.transfer_points)
    Call<CommonModal> transfer_points(
            @Header("token") String token,
            @Field("points") String points,
            @Field("user_number") String user_number
    );

    @FormUrlEncoded
    @POST(ApiUrl.withdraw)
    Call<CommonModal> withdraw(
            @Header("token") String token,
            @Field("points") String points,
            @Field("method") String method
    );

    @FormUrlEncoded
    @POST(ApiUrl.bid_history)
    Call<WinHistoryModal> bid_history(
            @Header("token") String token,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
    @FormUrlEncoded
    @POST(ApiUrl.win_history)
    Call<WinHistoryModal> win_history(
            @Header("token") String token,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );



    @FormUrlEncoded
    @POST(ApiUrl.withdraw_statement)
    Call<WalletStatementModal> withdraw_statement(
            @Header("token") String token,
            @Field("state") String state
    );

    @FormUrlEncoded
    @POST(ApiUrl.starline_game)
    Call<StarlineGameModel> starline_game(
            @Header("token") String token,
            @Field("game") String game
    );

    @FormUrlEncoded
    @POST(ApiUrl.starline_place_bid)
    Call<CommonModal> starline_place_bid(
            @Header("token") String token,
            @Field("game_bids") String game_bids
    );

    @FormUrlEncoded
    @POST(ApiUrl.starline_bid_history)
    Call<StarlineWinModal> starline_bid_history(
            @Header("token") String token,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded
    @POST(ApiUrl.starline_win_history)
    Call<StarlineWinModal> starline_win_history(
            @Header("token") String token,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded
    @POST(ApiUrl.gali_disawar_win_history)
    Call<GaliWinModal> gali_disawar_win_history(
            @Header("token") String token,
            @Field("from_date") String fromDate,
            @Field("to_date") String toDate
    );

    @FormUrlEncoded
    @POST(ApiUrl.gali_disawar_game)
    Call<GaliGameList> gali_disawar_game(
            @Header("token") String token,
            @Field("string") String string
    );

    @FormUrlEncoded
    @POST(ApiUrl.gali_disawar_bid_history)
    Call<GaliWinModal> gali_disawar_bid_history(
            @Header("token") String token,
            @Field("from_date") String fromDate,
            @Field("to_date") String toDate
    );

    @FormUrlEncoded
    @POST(ApiUrl.gali_disawar_place_bid)
    Call<CommonModal> gali_disawar_place_bid(
            @Header("token") String token,
            @Field("game_bids") String gameBids
    );
}
