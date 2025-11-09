package com.panda.academy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// NOTE: You must also define the RoomType entity for this to fully compile
// (e.g., public class RoomType { @Id private Long id; ... })

@Entity
@Table(name = "specializations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {

    // --- Primary Key (INTEGER PRIMARY KEY AUTOINCREMENT) ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    @Size(max = 50)
    @NotBlank
    private String name;

    // Maps to FOREIGN KEY (room_type_id) REFERENCES room_types(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType; // This relationship is now valid.

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}