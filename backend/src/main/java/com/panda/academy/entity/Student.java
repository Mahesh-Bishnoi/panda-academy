package com.panda.academy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data // Generates getters, setters, toString, equals, and hashCode
@Builder // Provides a fluent API for creating instances
@NoArgsConstructor // Required by JPA
@AllArgsConstructor // Needed for @Builder
public class Student {

    // --- Primary Key (INTEGER PRIMARY KEY AUTOINCREMENT) ---
    @Id
    // Crucial for SQLite auto-incrementing INTEGER PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // --- Core Fields ---
    @Column(name = "first_name", length = 50, nullable = false)
    @Size(max = 50)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    @Size(max = 50)
    @NotBlank
    private String lastName;

    @Column(name = "email", length = 100, unique = true)
    @Size(max = 100)
    @Email // Optional: Adds standard email format validation
    private String email;

    // Maps to INTEGER NOT NULL CHECK (grade_level BETWEEN 9 AND 12)
    @Column(name = "grade_level", nullable = false)
    @Min(value = 9)
    @Max(value = 12)
    @NotNull
    private Integer gradeLevel;

    @Column(name = "enrollment_year", nullable = false)
    @NotNull
    private Integer enrollmentYear;

    @Column(name = "expected_graduation_year")
    private Integer expectedGraduationYear;

    // Maps to VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'inactive', 'graduated'))
    // @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    // Use the converter explicitly if not auto-applied
    @Convert(converter = StudentStatusConverter.class)
    @Default // Sets default for Lombok @Builder
    private StudentStatus status = StudentStatus.ACTIVE;

    // Maps to DATETIME DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", updatable = false)
    @Default // Sets default for Lombok @Builder
    private LocalDateTime createdAt = LocalDateTime.now();
}