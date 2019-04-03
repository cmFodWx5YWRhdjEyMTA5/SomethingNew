package peaceinfotech.malegaonbazar.Signup.RetrofitModel.LogInModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {



    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("details")
    @Expose
    private List<DetailsModel> detailsModels = (List<DetailsModel>) new DetailsModel();


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DetailsModel> getDetailsModels() {
        return detailsModels;
    }

    public void setDetailsModels(List<DetailsModel> detailsModels) {
        this.detailsModels = detailsModels;
    }
}

