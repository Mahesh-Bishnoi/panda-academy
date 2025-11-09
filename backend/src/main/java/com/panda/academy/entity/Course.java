package com.panda.academy.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 10, nullable = false, unique = true)
    @Size(max = 10)
    @NotBlank
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    @Size(max = 100)
    @NotBlank
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Maps to DECIMAL(3,1) and CHECK (credits > 0)
    @Column(name = "credits", precision = 3, scale = 1, nullable = false)
    @DecimalMin(value = "0.1", message = "Credits must be greater than 0")
    @NotNull
    private BigDecimal credits;

    // Maps to INTEGER NOT NULL CHECK (hours_per_week BETWEEN 2 AND 6)
    @Column(name = "hours_per_week", nullable = false)
    @Min(value = 2)
    @Max(value = 6)
    @NotNull
    private Integer hoursPerWeek;

    // --- Foreign Keys ---

    // Maps to FOREIGN KEY (specialization_id) REFERENCES specializations(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = false)
    @NotNull
    private Specialization specialization; // This now correctly points to the new Specialization class

    // Maps to FOREIGN KEY (prerequisite_id) REFERENCES courses(id) (Self-referencing)
    // Many Courses can have the same prerequisite (One to Many from Prerequisite to Courses)
    // One Course can have one Prerequisite (Many to One from Course to Prerequisite)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prerequisite_id") // prerequisite_id is nullable in the DB
    private Course prerequisite;

    // --- Enum and Other Constraints ---

    // Maps to course_type VARCHAR(20) NOT NULL CHECK (course_type IN ('core', 'elective'))
    // EnumType.STRING is used to store 'CORE' or 'ELECTIVE' instead of a fragile ordinal number.
    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", length = 20, nullable = false)
    @NotNull
    private CourseType courseType;

    // Maps to grade_level_min INTEGER CHECK (grade_level_min BETWEEN 9 AND 12)
    @Column(name = "grade_level_min")
    @Min(value = 9)
    @Max(value = 12)
    private Integer gradeLevelMin;

    // Maps to grade_level_max INTEGER CHECK (grade_level_max BETWEEN 9 AND 12)
    @Column(name = "grade_level_max")
    @Min(value = 9)
    @Max(value = 12)
    private Integer gradeLevelMax;

    // Maps to semester_order INTEGER NOT NULL CHECK (semester_order IN (1, 2))
    // We can use an Integer for this simple check, or an Enum if you prefer.
    @Column(name = "semester_order", nullable = false)
    @Min(value = 1)
    @Max(value = 2)
    @NotNull
    private Integer semesterOrder; // 1 for Fall, 2 for Spring

    @Column(name = "created_at", updatable = false)
    // DATETIME DEFAULT CURRENT_TIMESTAMP
    private LocalDateTime createdAt;

    // NOTE: The CHECK (grade_level_max >= grade_level_min) is a complex,
    // cross-field validation. This MUST be enforced at the **database level** // and ideally also using a custom Java Bean Validation constraint (omitted here
    // for simplicity, but recommended for production code).
}