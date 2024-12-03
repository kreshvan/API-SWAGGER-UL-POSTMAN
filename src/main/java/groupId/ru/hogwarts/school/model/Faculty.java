package groupId.ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private List<Student> students;


    public Faculty(Long id, String name, String color, List<Student> students) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.students = students;
    }

    public Faculty(String  name, String color) {
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Student> setStudents(List<Student> students) {
        return students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Faculty [id=" + id + ", name=" + name + ", color=" + color + "]";
    }

    @Override
    public boolean equals(Object object) {
        return Objects.equals(id, ((Faculty) object).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
