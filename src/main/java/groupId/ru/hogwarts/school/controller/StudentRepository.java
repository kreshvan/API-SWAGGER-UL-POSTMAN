package groupId.ru.hogwarts.school.controller;

import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //int minNumber = 0;
    //int maxNumber = MAX_VALUE;
    List<Student> findByAgeBetween(int min, int max);
    @Query(value = "SELECT count", nativeQuery = true)
    List<GetSumAndId>getSummaStudentsById();







}
