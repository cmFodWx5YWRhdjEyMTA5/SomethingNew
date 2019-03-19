package peaceinfotech.malegaonbazar.Vendor.Model;

public class RequestListModel {

    String name,price,product;

    public RequestListModel(String name, String price,String product) {
        this.name = name;
        this.price = price;
        this.product=product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
