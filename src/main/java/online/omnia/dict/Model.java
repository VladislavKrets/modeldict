package online.omnia.dict;

/**
 * Created by lollipop on 25.08.2017.
 */
public class Model {
    private int id;
    private String value;

    public Model(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
