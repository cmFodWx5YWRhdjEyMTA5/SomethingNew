package peaceinfotech.malegaonbazar.Vendor;

public class OfferPreviewModel  {

    String offersName,offer,imageurl,min,max;

    public OfferPreviewModel(String offersName, String offer, String imageurl,String min,String max) {
        this.offersName = offersName;
        this.offer = offer;
        this.imageurl = imageurl;
        this.min=min;
        this.max=max;
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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
}
