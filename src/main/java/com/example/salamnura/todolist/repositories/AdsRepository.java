package com.example.salamnura.todolist.repositories;

import com.example.salamnura.todolist.entities.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface AdsRepository extends JpaRepository<Ads, Long> {

    List<Ads> findAllByAdTypeId(Long id);
    List<Ads> findAllByBrandId(Long id);
    List<Ads> findAllByUserId(Long id);
    Ads findByName(String name);

}
