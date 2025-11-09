package com.panda.academy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_course_history",
        // Define the composite unique constraint
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id", "semester_id"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseHistory {

    // --- Primary Key (INTEGER PRIMARY KEY AUTOINCREMENT) ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // --- Foreign Key Relationships ---

    // FOREIGN KEY (student_id) REFERENCES students(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull
    private Student student;

    // FOREIGN KEY (course_id) REFERENCES courses(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull
    private Course course;

    // FOREIGN KEY (semester_id) REFERENCES semesters(id)
    // NOTE: Requires a 'Semester' entity to exist
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    @NotNull
    private Semester semester;

    // --- Constraints ---

    // Maps to status VARCHAR(20) NOT NULL CHECK (status IN ('passed', 'failed'))
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @NotNull
    private CourseCompletionStatus status;

    // Maps to DATETIME DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", updatable = false)
    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

}