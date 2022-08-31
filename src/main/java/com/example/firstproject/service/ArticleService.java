package com.example.firstproject.service;

import com.example.firstproject.Entity.Article;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional //해당 메소드를 트랜잭션으로 묶는다!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //dto 묶음을 Entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
                /*
                -> 원래 이런 코드가 될 수 있었음.
                List<Article> articleList = new ArrayList<>();
                for(int i=0; i<dtos.size(); i++){
                    ArticleForm dto = dtos.get(i);
                    Article entity = dto.toEntity();
                    articleList.add(entity);
                }
                 */

        //Entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        //(ex)강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패!") );

        //결과값 반환

        return articleList;
    }
}
