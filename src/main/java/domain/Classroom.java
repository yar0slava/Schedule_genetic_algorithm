package genetic_algorithm.domain;

public class Classroom {

    String id;
    Integer capacity;

    public Classroom(String id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return id;
    }
}
