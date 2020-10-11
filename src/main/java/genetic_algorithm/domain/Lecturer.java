package genetic_algorithm.domain;

public class Lecturer {
    Integer id;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    String name;

    @Override
    public String toString() {
        return name;
    }

    public Lecturer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
