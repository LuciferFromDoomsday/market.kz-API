package com.example.salamnura.todolist.services;

import com.example.salamnura.todolist.entities.Card;
import com.example.salamnura.todolist.entities.CardTasks;

import java.util.List;

public interface CardService {

    Card addCard(Card card);
    List<Card> getAllCards();
    List<Card> getAllCardsByName(String name);
    Card getCard(Long id);
    void deleteCard(Card card);
    Card saveCard(Card card);
    CardTasks addTask(CardTasks task);
    List<CardTasks> getAllTasks();
    CardTasks getTask(Long id);
    void deleteTask(CardTasks task);
    CardTasks saveTask(CardTasks task);
    List<CardTasks> getAllTasksByCard(Long id);

}
