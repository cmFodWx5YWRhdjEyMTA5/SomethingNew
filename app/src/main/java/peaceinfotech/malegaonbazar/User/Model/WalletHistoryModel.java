package peaceinfotech.malegaonbazar.User.Model;

public class WalletHistoryModel {

    String prodName,prodCost,prodLoc,prodDate;

    public WalletHistoryModel(String prodName, String prodCost, String prodLoc, String prodDate) {
        this.prodName = prodName;
        this.prodCost = prodCost;
        this.prodLoc = prodLoc;
        this.prodDate = prodDate;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCost() {
        return prodCost;
    }

    public void setProdCost(String prodCost) {
        this.prodCost = prodCost;
    }

    public String getProdLoc() {
        return prodLoc;
    }

    public void setProdLoc(String prodLoc) {
        this.prodLoc = prodLoc;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }
}
