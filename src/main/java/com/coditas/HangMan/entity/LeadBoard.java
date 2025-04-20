package com.coditas.HangMan.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class LeadBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leadBoardId;
    private float score;
    private long gameDuration;

    @ManyToOne
    private Player player;
    @ManyToOne
    private Tournament tournament;
    private Long playTime;

}
