package genetic_algorithm.domain;

public class Group {

    String id;
    Integer amount;
    String name;

    public Group(String id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getId() {
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
