package peaceinfotech.malegaonbazar.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StateListModel {

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("states")
    @Expose
    private List<stateList> stateLists = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<stateList> getStateLists() {
        return stateLists;
    }

    public void setStateLists(List<stateList> stateLists) {
        this.stateLists = stateLists;
    }



    public class stateList{

        @SerializedName("name")
        @Expose
        private String stateName;

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }
    }
}
