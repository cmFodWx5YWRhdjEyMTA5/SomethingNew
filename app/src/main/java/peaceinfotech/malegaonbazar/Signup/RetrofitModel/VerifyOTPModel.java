package peaceinfotech.malegaonbazar.Signup.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOTPModel {


    @SerializedName("response")
    @Expose
    private String  reponse;

    @SerializedName("message")
    @Expose
    private String  message;

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
}
