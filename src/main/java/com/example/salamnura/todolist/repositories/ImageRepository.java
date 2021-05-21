package com.example.salamnura.todolist.repositories;

import com.example.salamnura.todolist.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> getAllByAdsId(Long Id);
    Image getPictureByUrl(String url);

}
