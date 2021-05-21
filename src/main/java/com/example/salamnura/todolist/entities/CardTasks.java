package com.example.salamnura.todolist.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Table(name = "card_tasks")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CardTasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name ="task_text")
    private String taskText ;

    @Column(name ="addedDate")
    private Date addedDate;

    @Column(name ="is_done")
    private boolean done;

    @ManyToOne(fetch = FetchType.EAGER)
    Card card;

}
