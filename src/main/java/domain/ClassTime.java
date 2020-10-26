package domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTime classTime = (ClassTime) o;
        return Objects.equals(id, classTime.id) &&
                Objects.equals(time, classTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }
}
