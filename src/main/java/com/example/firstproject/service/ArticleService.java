package com.example.firstproject.service;

import com.example.firstproject.Entity.Article;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1:수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id, article.toString()); //중괄호 안에 순서대로 찾아들어감

        //2:대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        //3:잘못된 요청 처리(대상이 없거나, id가 다른경우)
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article: {}",id, article.toString()); //중괄호 안에 순서대로 찾아들어감
            return null;
        }
        //4:업데이트
        target.patch(article); //요청한값이 중간에 비어있을경우 이미 있는 값으로 채워서 보냄
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //잘못된 요청 처리
        if(target == null){
            return null;
        }
        //대상 삭제
        articleRepository.delete(target);
        //데이터 반환
        return target;
    }
}
