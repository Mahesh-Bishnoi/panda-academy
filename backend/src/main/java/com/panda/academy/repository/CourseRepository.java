package com.panda.academy.repository;

import com.panda.academy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public List<Course> findAllBySemesterOrder(Integer semesterOrder);
}
