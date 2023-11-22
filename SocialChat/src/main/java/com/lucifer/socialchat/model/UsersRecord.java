package com.lucifer.socialchat.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UsersRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate date;
    private LocalTime joinTime;
    private LocalTime leaveTime;

    public UsersRecord() {
        // Set the date to the current system date when creating a new instance
        this.date = LocalDate.now();
    }
}
