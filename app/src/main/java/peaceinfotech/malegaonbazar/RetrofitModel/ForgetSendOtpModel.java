package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetSendOtpModel {
    @SerializedName("response")
    @Expose
    private String  reponse;

    @SerializedName("message")
    @Expose
    private String  message;

    @SerializedName("Otp")
    @Expose
    private int  otp;

    @SerializedName("mobile")
    @Expose
    private String  mobile;

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
