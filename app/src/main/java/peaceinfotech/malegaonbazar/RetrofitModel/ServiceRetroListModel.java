package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRetroListModel {


    @SerializedName("serviceid")
    @Expose
    private String serviceId;

    @SerializedName("serviceName")
    @Expose
    private String serviceName;

    @SerializedName("Description")
    @Expose
    private String serviceDesc;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }
}
