package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetailsModel {




    @SerializedName("user_id")
    @Expose
    String userId;

    @SerializedName("vendor_id")
    @Expose
    String vendorId;

    @SerializedName("role_id")
    @Expose
    String roleId;

    @SerializedName("role_name")
    @Expose
    String roleName;

    @SerializedName("fullname")
    @Expose
    String fullName;

    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("referrelcode")
    @Expose
    String referenceId;

    @SerializedName("brand")
    @Expose
    String brand;

    @SerializedName("logo")
    @Expose
    String imgLogoUrl;

    @SerializedName("banner")
    @Expose
    String imgBannerUrl;

    @SerializedName("email")
    @Expose
    String email;


    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl;
    }

    public String getImgBannerUrl() {
        return imgBannerUrl;
    }

    public void setImgBannerUrl(String imgBannerUrl) {
        this.imgBannerUrl = imgBannerUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}

