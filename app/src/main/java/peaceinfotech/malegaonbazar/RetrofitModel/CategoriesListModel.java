package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesListModel {

    @SerializedName("Cat_ID")
    @Expose
    private String catUid;

    @SerializedName("catid")
    @Expose
    private String catId;

    @SerializedName("categoryName")
    @Expose
    private String catName;

    @SerializedName("Description")
    @Expose
    private String catDesc;

    @SerializedName("createdDate")
    @Expose
    private String catCrDate;


    public String getCatUid() {
        return catUid;
    }

    public void setCatUid(String catUid) {
        this.catUid = catUid;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getCatCrDate() {
        return catCrDate;
    }

    public void setCatCrDate(String catCrDate) {
        this.catCrDate = catCrDate;
    }
}
