package peaceinfotech.malegaonbazar.Vendor.Model;

public class PieEnteries {

    private String label;
    private float values ;

    public PieEnteries(String label, float values) {
        this.label = label;
        this.values = values;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getValues() {
        return values;
    }

    public void setValues(float values) {
        this.values = values;
    }
}
