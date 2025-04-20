package com.coditas.HangMan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hintId;
    private String hintName;

    @ManyToOne
    private Word word;

    public Hint(String hintName) {
        this.hintName = hintName;
    }
}
