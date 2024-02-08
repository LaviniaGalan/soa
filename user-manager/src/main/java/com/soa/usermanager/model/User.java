package com.soa.usermanager.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String type;
}
