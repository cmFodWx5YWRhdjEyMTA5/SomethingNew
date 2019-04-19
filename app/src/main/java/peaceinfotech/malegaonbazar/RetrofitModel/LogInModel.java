package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LogInModel {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("details")
    @Expose
    private LoginDetailsModel detailsModels =new LoginDetailsModel();


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginDetailsModel getDetailsModels() {
        return detailsModels;
    }

    public void setDetailsModels(LoginDetailsModel detailsModels) {
        this.detailsModels = detailsModels;
    }

    public class LoginDetailsModel{

        @SerializedName("vendor_id")
        @Expose
        public String vendorId;

        @SerializedName("user_id")
        @Expose
        public String userId;

        @SerializedName("role_id")
        @Expose
        public String roleID;

        @SerializedName("role_name")
        @Expose
        public String roleName;

        @SerializedName("category_id")
        @Expose
        public String catID;

        @SerializedName("category_name")
        @Expose
        public String catName;

        @SerializedName("fullname")
        @Expose
        public String fullName;

        @SerializedName("location")
        @Expose
        public  String location;

        @SerializedName("mobile")
        @Expose
        public String mobile;

        @SerializedName("brand")
        @Expose
        public String brand;

        @SerializedName("logo")
        @Expose
        public String imgLogoURL;

        @SerializedName("banner")
        @Expose
        public String imgBanURL;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("latitude")
        @Expose
        public String lat;

        @SerializedName("longitude")
        @Expose
        public String lng;

        @SerializedName("referrelcode")
        @Expose String referenceId;

        @SerializedName("profile")
        @Expose String profileUrl;

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public String getReferenceId() {
            return referenceId;
        }

        public void setReferenceId(String referenceId) {
            this.referenceId = referenceId;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoleID() {
            return roleID;
        }

        public void setRoleID(String roleID) {
            this.roleID = roleID;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getCatID() {
            return catID;
        }

        public void setCatID(String catID) {
            this.catID = catID;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getImgLogoURL() {
            return imgLogoURL;
        }

        public void setImgLogoURL(String imgLogoURL) {
            this.imgLogoURL = imgLogoURL;
        }

        public String getImgBanURL() {
            return imgBanURL;
        }

        public void setImgBanURL(String imgBanURL) {
            this.imgBanURL = imgBanURL;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

}
