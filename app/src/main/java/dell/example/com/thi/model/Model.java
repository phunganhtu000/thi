package dell.example.com.thi.model;

public class Model {
    String id;
    String name;

    public Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Model() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
