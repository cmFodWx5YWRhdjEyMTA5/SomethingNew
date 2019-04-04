package peaceinfotech.malegaonbazar.Vendor.Model;

public class ServicesListModel {
    private String serviceId,serviceName,serviceDesc;

    public ServicesListModel(String serviceId, String serviceName, String serviceDesc) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
    }

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
