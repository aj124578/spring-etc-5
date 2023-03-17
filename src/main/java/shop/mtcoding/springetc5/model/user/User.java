package shop.mtcoding.springetc5.model.user;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@Setter
@Table(name = "user_tb") // 웬만하면 적어서 테이블 명 명시해주기 naitivequery 쓸때만
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @JsonIgnore // password는 노출시키지 않기 위해 붙여줌
    private String password;
    private String email;
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;

    }

    public void update(String password, String email){
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() { // @Tostring 어노테이션을 직접 쓰면 위험하기에 지금은 이렇게 써서 테스트해라
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", createdAt=" + createdAt + "]";
    }


    
}
