package com.school.repository;

import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    Optional<Student> findByRollNumberAndDateOfBirth(String rollNumber, LocalDate dateOfBirth);
    boolean existsByRollNumber(String rollNumber);
    List<Student> findByClassTeacherId(Long teacherId);
    Optional<Student> findByUserId(Long userId);
}