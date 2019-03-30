package peaceinfotech.malegaonbazar.Vendor.Model;

public class OfferPreviewModel  {

    String offersName,offer,details,min,max,uid,start_date,end_date,offerPrice,discPercent;


    public OfferPreviewModel(String uid,String offersName, String offer,String offerPrice,String discPercent,String min,String max,String details,String start_date,String end_date ) {
        this.offersName = offersName;
        this.offer = offer;
        this.offerPrice=offerPrice;
        this.discPercent=discPercent;
        this.details = details;
        this.min=min;
        this.max=max;
        this.uid=uid;
        this.start_date=start_date;
        this.end_date=end_date;


    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(String discPercent) {
        this.discPercent = discPercent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOffersName() {
        return offersName;
    }

    public void setOffersName(String offersName) {
        this.offersName = offersName;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
}
