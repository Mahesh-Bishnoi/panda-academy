package com.panda.academy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleId implements Serializable {
    private Long courseId;
    private Long teacherId;
    private Long classroomId;
    private Long timeSlotId;
    private Long semesterId;
}
