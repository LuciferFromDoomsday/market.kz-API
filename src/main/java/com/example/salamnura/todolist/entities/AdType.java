package com.example.salamnura.todolist.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name ="ad_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id ;

    @Column(name ="name")
    private String name ;

    @Column(name = "image_url" , length = 10000)
    private String imageUrl;


}
