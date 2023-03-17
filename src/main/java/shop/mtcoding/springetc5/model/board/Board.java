package shop.mtcoding.springetc5.model.board;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.springetc5.model.user.User;

@NoArgsConstructor
@Entity
@Setter
@Table(name = "board_tb") // 웬만하면 적어서 테이블 명 명시해주기 naitivequery 쓸때만
@Getter
public class Board {

    // n에 외래키가 있어야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // fetch 기본적으로는 EAGER 전략, lazy로 바꾸면 지연로딩해서 select 함
    @ManyToOne(fetch = FetchType.EAGER) // 앞에 many는 클래스꺼 뒤에 one은 필터꺼 
    private User user;

    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Board(Integer id, User user, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

