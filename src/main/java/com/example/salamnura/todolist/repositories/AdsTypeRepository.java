package com.example.salamnura.todolist.repositories;

import com.example.salamnura.todolist.entities.AdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AdsTypeRepository extends JpaRepository<AdType, Long> {

}
