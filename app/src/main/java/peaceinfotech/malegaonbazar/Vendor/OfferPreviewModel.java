package peaceinfotech.malegaonbazar.Vendor;

public class OfferPreviewModel  {

    String offersName,offer,details,min,max,uid;

    public OfferPreviewModel(String uid,String offersName, String offer,String min,String max,String details) {
        this.offersName = offersName;
        this.offer = offer;
        this.details = details;
        this.min=min;
        this.max=max;
        this.uid=uid;
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
}
