package genetic_algorithm.domain;

public class ClassTime {

    String id;
    String time;

    public ClassTime(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time;
    }
}
