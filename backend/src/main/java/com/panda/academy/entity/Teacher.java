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
@Table(name = "teachers")
@Data // Generates getters, setters, toString, equals, and hashCode
@Builder // Provides a fluent API for creating instances
@NoArgsConstructor // Required by JPA
@AllArgsConstructor // Needed for @Builder
public class Teacher {

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

    // Maps to FOREIGN KEY (specialization_id) REFERENCES specializations(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = false)
    @NotNull
    private Specialization specialization;

    @Column(name = "email", length = 100, unique = true)
    @Size(max = 100)
    @Email // Optional: Adds standard email format validation
    private String email;

    // Maps to INTEGER DEFAULT 4 CHECK (max_daily_hours <= 4)
    @Column(name = "max_daily_hours")
    @Max(value = 4, message = "Maximum daily hours cannot exceed 4")
    @Min(value = 0) // Assuming hours can't be negative
    @Default // Sets default for Lombok @Builder
    private Integer maxDailyHours = 4;

    // Maps to DATETIME DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", updatable = false)
    @Default // Sets default for Lombok @Builder
    private LocalDateTime createdAt = LocalDateTime.now();
}