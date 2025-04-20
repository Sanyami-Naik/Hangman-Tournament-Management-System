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
public class RedeemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long redeemRequestId;
    private String redeemRequestStatus="PENDING";
    @ManyToOne
    private Player player;
    @ManyToOne
    private Gift gift;
}
