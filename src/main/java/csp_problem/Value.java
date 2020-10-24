package csp_problem;

import domain.ClassTime;
import domain.Classroom;

import java.util.Objects;

public class Value {
    ClassTime classTime;
    Classroom classroom;

    public Value(ClassTime classTime, Classroom classroom) {
        this.classTime = classTime;
        this.classroom = classroom;
    }

    public ClassTime getClassTime() {
        return classTime;
    }

    public void setClassTime(ClassTime classTime) {
        this.classTime = classTime;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return classTime.getId().equals(value.classTime.getId()) &&
                classroom.getId().equals(value.classroom.getId());
    }

    @Override
    public String toString() {
        return "Value{" +
                "classTime=" + classTime +
                ", classroom=" + classroom +
                '}';
    }
}
