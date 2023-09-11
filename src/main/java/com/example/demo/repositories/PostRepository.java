package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query(name = "select p from posts p where lower(p.title) like concat('%',:keyword,'%')")
    List<Post> searchByTitle(String keyword);
}
