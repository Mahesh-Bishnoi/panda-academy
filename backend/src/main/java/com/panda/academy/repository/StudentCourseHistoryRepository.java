package com.panda.academy.repository;

import com.panda.academy.entity.StudentCourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseHistoryRepository extends JpaRepository<StudentCourseHistory, Long> {
}
