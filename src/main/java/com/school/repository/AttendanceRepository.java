package com.school.repository;

import com.school.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByStudentRollNumber(String rollNumber);
    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate from, LocalDate to);
    boolean existsByStudentIdAndDate(Long studentId, LocalDate date);
}