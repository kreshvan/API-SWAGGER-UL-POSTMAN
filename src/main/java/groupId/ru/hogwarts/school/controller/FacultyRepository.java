package groupId.ru.hogwarts.school.controller;

import groupId.ru.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color);
}

