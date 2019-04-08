package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityListModel {

    @SerializedName("response")
    @Expose
    private String response;



    @SerializedName("cities")
    @Expose
    private List<cityLists> cityLists = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CityListModel.cityLists> getCityLists() {
        return cityLists;
    }

    public void setCityLists(List<CityListModel.cityLists> cityLists) {
        this.cityLists = cityLists;
    }

    public class cityLists{

        @SerializedName("name")
        @Expose
        private  String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
