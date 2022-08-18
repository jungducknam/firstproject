package com.example.firstproject.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.firstproject.Entity.Article;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
        return "redirect:/articles/"+saved.getId();
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

    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "/articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){ //id 를 가져오긴 할건데, 어디서? 바로 URL에 있는 {id}에서! @pathvariable을 통해서, 이때 파라미터의 이름이 GetMapping안의 변수 이름과 동일해야함!
        //수정할 데이터를 가져오기. 그러려면 데이터를 꺼내와야겠지?
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록! 모델을 사용하려면 파라미터도 모델을 받아와야겠지?
        model.addAttribute("article",articleEntity);

        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){ //요청 준 페이지의 form데이터를 DTO로 받아온다.
        log.info(form.toString());

        //1.DTO를 엔티티로 변환한다!
        Article articleEntity = form.toEntity();

        //2.엔티티를 DB로 저장한다!
        //2-1. DB에서 기존 데이터를 가져온다!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2. 기존 데이터의 값을 갱신한다!
        if(target!=null){
            articleRepository.save(articleEntity); //엔티티가 DB로 갱신!
        }

        //3.수정 결과 페이지로 리다이렉트 한다!
        return "redirect:/articles/"+target.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청이 들어왔습니다. "+id);

        //1. 삭제 대상을 가져온다!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2. 대상을 삭제 한다!
        if( articleEntity != null){
            articleRepository.delete(articleEntity);
            //또는 articleRepository.deleteById(id);
            rttr.addFlashAttribute("msg","데이터가 정상적으로 삭제되었습니다."); //rttr은 리다이렉트 되었을 때 화면으로 날아간다. addFlashAttribute는 일회성 메시지를 보낼때 유용.
        }

        //3. 결과 페이지로 리다이렉트 한다!
        return "redirect:/articles";
    }
}
