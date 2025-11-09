package com.panda.academy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "room_types")
@Data // Generates getters, setters, toString, equals, and hashCode
@Builder // Provides a fluent API for creating instances
@NoArgsConstructor // Required by JPA
@AllArgsConstructor // Needed for @Builder
public class RoomType {

    // --- Primary Key (INTEGER PRIMARY KEY AUTOINCREMENT) ---
    @Id
    // Crucial for SQLite auto-incrementing INTEGER PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    @Size(max = 50)
    @NotBlank
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}