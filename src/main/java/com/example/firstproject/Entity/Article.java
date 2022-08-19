package com.example.firstproject.Entity;

import lombok.*;

import javax.persistence.*;

@Entity //이 어노테이션을 사용해야 DB가 해당 객체를 인식 가능!
@AllArgsConstructor //생성자 생성
@NoArgsConstructor //디폴트 생성자
@ToString
@Getter
@Setter
public class Article {

    @Id //PK칼럼 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스 자동 생성 어노테이션 괄호 안에는 이제 DB의 값을 읽어와서 그다음부터 생성할것이다라는 선언!
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article){
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }



}
