package peaceinfotech.malegaonbazar.User.Model;

public class OffersListModel {

    String offersName,offer,imageurl;

    int offerPercent,offersPrice;

    public OffersListModel(String offersName, String offer, int offerPercent,String imageurl,int offersPrice) {
        this.offersName = offersName;
        this.offer = offer;
        this.imageurl = imageurl;
        this.offersPrice=offersPrice;
        this.offerPercent=offerPercent;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }

    public int getOffersPrice() {
        return offersPrice;
    }

    public void setOffersPrice(int offersPrice) {
        this.offersPrice = offersPrice;
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
}
