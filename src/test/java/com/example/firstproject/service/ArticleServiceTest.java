package com.example.firstproject.service;

import com.example.firstproject.Entity.Article;
import com.example.firstproject.dto.ArticleForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스는 스프링부트와 연동되어 테스팅된다
class ArticleServiceTest {
    @Autowired ArticleService articleService;

    @Test
    void index() {
        //예상 시나리오
        Article a = new Article(1L,"asdfasdfasdf","1111");
        Article b = new Article(2L,"asdfasdfasdf","1111");
        Article c = new Article(3L,"dsafsadgdf","1111");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        //실제 결과
        List<Article> articles = articleService.index();

        //비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_ID_입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(1L,"asdfasdfasdf","1111");
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected.toString(),article.toString());

    }

    @Test
    void show_실패_존재하지않는_ID_입력() {
        //예상
        Long id = 4L;
        Article expected = null;
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }
}