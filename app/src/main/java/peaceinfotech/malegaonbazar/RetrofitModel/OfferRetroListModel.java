package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OfferRetroListModel {

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("offers")
    @Expose
    public List<offerlistModel> offerlistModels = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<offerlistModel> getOfferlistModels() {
        return offerlistModels;
    }

    public void setOfferlistModels(List<offerlistModel> offerlistModels) {
        this.offerlistModels = offerlistModels;
    }

    public class offerlistModel{

        @SerializedName("offerid")
        @Expose
        private String offerId;

        @SerializedName("title")
        @Expose
        private String offerTitle;

        @SerializedName("description")
        @Expose
        private String offerDesc;

        @SerializedName("min_trans")
        @Expose
        private String offerMinTrans;

        @SerializedName("max_trans")
        @Expose
        private String offerMaxTrans;

        @SerializedName("start_date")
        @Expose
        private String offerStartDate;

        @SerializedName("end_date")
        @Expose
        private String offerEndDate;

        @SerializedName("termcondition")
        @Expose
        private String termCondition;

        @SerializedName("offertype")
        @Expose
        private String offerType;

        @SerializedName("discount")
        @Expose
        private String offerDiscount;

        @SerializedName("price")
        @Expose
        private String price;


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

        public String getOfferMinTrans() {
            return offerMinTrans;
        }

        public void setOfferMinTrans(String offerMinTrans) {
            this.offerMinTrans = offerMinTrans;
        }

        public String getOfferMaxTrans() {
            return offerMaxTrans;
        }

        public void setOfferMaxTrans(String offerMaxTrans) {
            this.offerMaxTrans = offerMaxTrans;
        }

        public String getOfferStartDate() {
            return offerStartDate;
        }

        public void setOfferStartDate(String offerStartDate) {
            this.offerStartDate = offerStartDate;
        }

        public String getOfferEndDate() {
            return offerEndDate;
        }

        public void setOfferEndDate(String offerEndDate) {
            this.offerEndDate = offerEndDate;
        }

        public String getTermCondition() {
            return termCondition;
        }

        public void setTermCondition(String termCondition) {
            this.termCondition = termCondition;
        }

        public String getOfferType() {
            return offerType;
        }

        public void setOfferType(String offerType) {
            this.offerType = offerType;
        }

        public String getOfferDiscount() {
            return offerDiscount;
        }

        public void setOfferDiscount(String offerDiscount) {
            this.offerDiscount = offerDiscount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
