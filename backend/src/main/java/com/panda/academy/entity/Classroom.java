package com.panda.academy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "classrooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {

    @Id
    // Maps to INTEGER PRIMARY KEY AUTOINCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @Column(name = "capacity")
    // Maps to INTEGER DEFAULT 10 CHECK (capacity <= 10)
    @Builder.Default
    private Integer capacity = 10; // Set the default value directly in Java

    @Column(name = "equipment")
    private String equipment;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "created_at", updatable = false)
    // DATETIME DEFAULT CURRENT_TIMESTAMP
    private LocalDateTime createdAt;
}