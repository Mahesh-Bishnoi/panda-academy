package com.panda.academy;

import com.panda.academy.entity.TimeSlot;
import com.panda.academy.repository.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;

@SpringBootApplication
public class AcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademyApplication.class, args);
    }

    @Bean
    CommandLineRunner init(TimeSlotRepository timeSlotRepository) {
        return _ -> {
            if (timeSlotRepository.count() == 0) {
                Stream.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
                        .forEach(day -> Stream.of(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                                LocalTime.of(14, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(16, 0)
                        ).forEach(startTime -> timeSlotRepository.save(TimeSlot.builder()
                                .dayOfWeek(day)
                                .startTime(startTime)
                                .endTime(startTime.plusHours(1))
                                .build())));
            }
        };
    }
}
