package peaceinfotech.malegaonbazar.Vendor.Model;

public class HistoryModel {
    public String productName, productCost , buyerName, paymentMode;

    public HistoryModel(String productName, String productCost, String buyerName, String paymentMode) {
        this.productName = productName;
        this.productCost = productCost;
        this.buyerName = buyerName;
        this.paymentMode=paymentMode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
