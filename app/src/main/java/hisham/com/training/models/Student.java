package hisham.com.training.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Hisham on 11/14/2017.
 */

public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Integer avg;

    public Student() {
    }

    public Student(String name, Integer id, Integer age, Integer avg) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("avg", avg)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return new EqualsBuilder()
                .append(name, student.name)
                .append(id, student.id)
                .append(age, student.age)
                .append(avg, student.avg)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(id)
                .append(age)
                .append(avg)
                .toHashCode();
    }
}
