package peaceinfotech.malegaonbazar.Vendor.Model;

public class OfferPreviewModel {

    String offerId,offerTitle,offerDesc,productPrice,min,max,start_date,end_date,terms,offerType,discount;

    public OfferPreviewModel(String offerId, String offerTitle, String offerDesc,String productPrice,String min, String max, String start_date, String end_date, String terms, String offerType, String discount) {
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerDesc = offerDesc;
        this.min = min;
        this.max = max;
        this.start_date = start_date;
        this.end_date = end_date;
        this.terms = terms;
        this.offerType = offerType;
        this.discount = discount;
        this.productPrice=productPrice;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}