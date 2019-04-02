package peaceinfotech.malegaonbazar.Signup.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeModel {


    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("category")
    @Expose
    private List<CategoriesListModel> categoriesListModels = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CategoriesListModel> getCategoriesListModels() {
        return categoriesListModels;
    }

    public void setCategoriesListModels(List<CategoriesListModel> categoriesListModels) {
        this.categoriesListModels = categoriesListModels;
    }
}
