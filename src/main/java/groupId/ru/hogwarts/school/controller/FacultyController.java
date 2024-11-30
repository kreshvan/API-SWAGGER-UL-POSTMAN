package groupId.ru.hogwarts.school.controller;


import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("/{id}/get")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);

    }

    @PostMapping("/add/")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);


    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Faculty> editFaculty(@PathVariable long id, @RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(id, faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteDelete(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Faculty> findFacultyByNameOrColorIgnoreCase(@RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "color", required = false) String color) {
        return facultyService.findFacultyByNameOrColorIgnoreCase(name, color);
    }

    @GetMapping("/get/student/{id}")
    public Faculty findFacultyByStudentId(@PathVariable("id") long id) {

        return facultyService.findFacultyByStudentId(id);
    }


}
