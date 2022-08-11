package com.example.firstproject.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.firstproject.Entity.Article;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 연결!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString()); --> println은 실서버의 성능에 큰 악영향을 줌, 그때 로깅으로 대체!

        //1. dto를 Entity로 변환!
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에게 Entity를 DB로 저장하게함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "";
    }
    @GetMapping("/articles/{id}") //요 id위치에는 변화가능함
    public String show(@PathVariable Long id, Model model){ //여기 인수인 id가 위에주소창의 변수로 활용된다 선언
        log.info("id: "+id);

        //1: id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null); //DB에서 반환받은 값이 클래스가 Article이 아닐결우, null반환

        //2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article",articleEntity);

        //3: 보여줄 페이지를 설정!
        return "/articles/show";
    }
}
