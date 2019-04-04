package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServiceHomeModel {

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("services")
    @Expose
    private List<ServiceRetroListModel> serviceListModels = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ServiceRetroListModel> getServiceListModels() {
        return serviceListModels;
    }

    public void setServiceListModels(List<ServiceRetroListModel> serviceListModels) {
        this.serviceListModels = serviceListModels;
    }
}
