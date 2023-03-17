package shop.mtcoding.springetc5.config.dummy;

import shop.mtcoding.springetc5.model.board.Board;
import shop.mtcoding.springetc5.model.user.User;

public class MyDummyEntity {
    

    protected User newUser(String username){
        return User.builder()
                .username(username)
                .password("1234")
                .email(username + "@nate.com")
                .build();
    }

    protected Board newBoard(String title, User user) {

        if (user.getId() == null) { // 회원가입을 안했는데 board를 못쓰게 하기
            System.out.println("영속화해서 넣어라!!");
            return null;
        }

        return Board.builder()
                .title(title)
                .content(title)
                .user(user)
                .build();
    }
}
