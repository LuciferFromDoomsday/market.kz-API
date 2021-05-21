package com.example.salamnura.todolist.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name" , length = 200)

    private String name;

    @Column(name = "description" , length = 1000)
    private String description;

   @Column(name = "price" )

    private double price;

    @Column(name = "city" )

    private String city;

    @Column(name = "adress" , length = 1000)

    private String adress;


    @Column(name = "added_date" )

    private Date addedDate;

    @Column(name = "is_new")
    private Boolean isNew;

    @ManyToOne(fetch = FetchType.EAGER)
    private AdType adType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;



}

