package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.model.Faculty;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FacultyService  {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Long id, Faculty faculty);

    void deleteFaculty(long id);

    List<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByStudentId(long id);
}
