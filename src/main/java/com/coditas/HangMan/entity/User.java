package com.coditas.HangMan.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userName"}),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String userPassword;
    private int otp;

    @OneToOne(mappedBy = "user")
    private Player player;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "userId")
            ,inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "roleId"))
    private Set<Role> roleSet;
}
