package peaceinfotech.malegaonbazar.User.Model;

public class FavouriteListModel {
    public String title,votes,contact,email,address,offersNum;
    public float rating;

    public FavouriteListModel(String title, String votes, String contact, String email, String address, String offersNum, float rating) {
        this.title = title;
        this.votes = votes;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.offersNum = offersNum;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOffersNum() {
        return offersNum;
    }

    public void setOffersNum(String offersNum) {
        this.offersNum = offersNum;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
