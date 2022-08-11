package com.example.firstproject.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //이 어노테이션을 사용해야 DB가 해당 객체를 인식 가능!
@AllArgsConstructor //생성자 생성
@NoArgsConstructor //디폴트 생성자
@ToString
public class Article {

    @Id //PK칼럼 지정
    @GeneratedValue //시퀀스 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;
    @Column
    private String content;



}
