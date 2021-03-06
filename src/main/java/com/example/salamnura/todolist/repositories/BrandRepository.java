package com.example.salamnura.todolist.repositories;

import com.example.salamnura.todolist.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BrandRepository  extends JpaRepository<Brand , Long> {

}
