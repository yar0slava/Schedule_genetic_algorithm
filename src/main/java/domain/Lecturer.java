package genetic_algorithm.domain;

public class Lecturer {

    Integer id;
    String name;

    public Lecturer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
