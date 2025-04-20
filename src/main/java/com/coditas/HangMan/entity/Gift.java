package com.coditas.HangMan.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftId;
    private String giftName;
    private int  giftValue;
    private boolean isDeleted;


    @OneToMany(mappedBy = "gift",cascade = CascadeType.ALL)
    private List<RedeemRequest> redeemRequestList;
}
