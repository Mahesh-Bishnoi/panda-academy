package com.panda.academy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "semesters",
        // Define the composite unique constraint
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "year"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Semester {

    // --- Primary Key (INTEGER PRIMARY KEY AUTOINCREMENT) ---
    @Id
    // Crucial for SQLite auto-incrementing INTEGER PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // --- Core Fields ---
    @Column(name = "name", length = 20, nullable = false)
    @Size(max = 20)
    @NotBlank
    // NOTE: The complex CHECK constraint on (name, order_in_year)
    // is best left to the database, but you can enforce valid names here.
    private String name;

    @Column(name = "year", nullable = false)
    @NotNull
    private Integer year;

    // Maps to INTEGER NOT NULL CHECK (order_in_year IN (1, 2))
    @Column(name = "order_in_year", nullable = false)
    @Min(value = 1) // 1=Fall
    @Max(value = 2) // 2=Spring
    @NotNull
    private Integer orderInYear;

    // Maps to DATE
    @Column(name = "start_date")
    private LocalDate startDate;

    // Maps to DATE
    @Column(name = "end_date")
    private LocalDate endDate;

    // Maps to BOOLEAN DEFAULT FALSE
    // SQLite stores BOOLEAN as INTEGER (0 or 1), which JPA handles seamlessly
    @Column(name = "is_active")
    @Default
    private Boolean isActive = Boolean.FALSE;

    // Maps to DATETIME DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", updatable = false)
    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // NOTE: The complex cross-column CHECK constraint on (name, order_in_year)
    // is often left to the database, as implementing it with standard
    // Java Bean Validation would require a custom class-level validator.
}