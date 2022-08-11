package com.example.firstproject.repository;

import com.example.firstproject.Entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {

    ArrayList findAll();
}
