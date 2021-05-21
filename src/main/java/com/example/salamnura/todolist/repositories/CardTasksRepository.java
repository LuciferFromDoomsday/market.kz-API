package com.example.salamnura.todolist.repositories;

import com.example.salamnura.todolist.entities.Card;
import com.example.salamnura.todolist.entities.CardTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CardTasksRepository extends JpaRepository<CardTasks , Long> {

    List<CardTasks> findAllByCardId(Long id);

}
