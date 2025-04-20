package com.coditas.HangMan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wordId;
    private String wordName;

    @OneToMany(mappedBy = "word",cascade = CascadeType.ALL)
    private List<Hint> hintList;

    @ManyToOne
    private Tournament tournament;

    public Word(String wordName) {
        this.wordName = wordName;
    }
}
