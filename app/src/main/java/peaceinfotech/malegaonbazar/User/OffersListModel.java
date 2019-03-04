package peaceinfotech.malegaonbazar.User;

public class OffersListModel {

    String offersName,offer,imageurl;

    public OffersListModel(String offersName, String offer, String imageurl) {
        this.offersName = offersName;
        this.offer = offer;
        this.imageurl = imageurl;
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
