package com.example.salamnura.todolist.services.impl;


import com.example.salamnura.todolist.entities.Card;
import com.example.salamnura.todolist.entities.CardTasks;
import com.example.salamnura.todolist.repositories.CardRepository;
import com.example.salamnura.todolist.repositories.CardTasksRepository;
import com.example.salamnura.todolist.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardsRepository;

    @Autowired
    private CardTasksRepository cardTasksRepository;

    @Override
    public Card addCard(Card card) {
        return cardsRepository.save(card);
    }

    @Override
    public List<Card> getAllCards() {
        return cardsRepository.findAll();
    }

    @Override
    public List<Card> getAllCardsByName(String name) {
        name="%"+name+"%";
        return cardsRepository.findAllByNameLike(name);
    }

    @Override
    public Card getCard(Long id) {
        return cardsRepository.findById(id).get();
    }

    @Override
    public void deleteCard(Card card) {
        cardsRepository.delete(card);
    }

    @Override
    public Card saveCard(Card card) {
        return cardsRepository.save(card) ;
    }

    @Override
    public CardTasks addTask(CardTasks task) {
        return cardTasksRepository.save(task);
    }

    @Override
    public List<CardTasks> getAllTasks() {
        return cardTasksRepository.findAll();
    }

    @Override
    public CardTasks getTask(Long id) {
        return cardTasksRepository.findById(id).get();
    }

    @Override
    public void deleteTask(CardTasks task) {
        cardTasksRepository.delete(task);
    }

    @Override
    public CardTasks saveTask(CardTasks task) {
        return cardTasksRepository.save(task);
    }

    @Override
    public List<CardTasks> getAllTasksByCard(Long id) {
        return cardTasksRepository.findAllByCardId(id);
    }
}
