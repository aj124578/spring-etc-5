package shop.mtcoding.springetc5.model.board;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.springetc5.config.dummy.MyDummyEntity;
import shop.mtcoding.springetc5.model.user.User;
import shop.mtcoding.springetc5.model.user.UserRepository;


// test에 트랜잭션이 되어있는지 들어가서 보기 -> 격리가 안되면 롤백이 안돼서 독립적으로 테스트 못함
@DataJpaTest // db 관련된거 다 띄움 근데 UserRepository는 안뜸
@Import({UserRepository.class, BoardRepository.class}) // 그래서 import로 띄우면 됨 -> 배열로 만들어져있어서 {}로 여러개 띄우면 됨 ex) 서비스, repository 두개 다 한다던지
public class BoardRepositoryTest extends MyDummyEntity { // protected이기 때문에 상속받은 얘만 쓸 수 있음
    
    @Autowired // 테스트는 무조건 autowired 써야됨
    private BoardRepository boardRepository;

    @Autowired // 테스트는 무조건 autowired 써야됨
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();  // auto increment를 초기화 시키기 위해서 해줌
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();  // auto increment를 초기화 시키기 위해서 해줌
    }

    @Test
    public void save_test(){
        // given 1
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        
        
        // given 2
        Board board = newBoard("제목1", userPS);
        
        // when
        Board boardPS = boardRepository.save(board);
        System.out.println("테스트 : " + boardPS);

        // then
        assertThat(boardPS.getId()).isEqualTo(1);
        assertThat(boardPS.getUser().getId()).isEqualTo(1);

    }
    
    @Test
    public void update_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar"); // 비영속 상태
        User userPS = userRepository.save(user); // 영속화 시킴. PS 붙은거는 컨텍트 거친것
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);

        // given 2 - request 데이터
        String title = "제목12";
        String content = "내용12";


        // when
        boardPS.update(title, content); // board를 변경해서 영속화 시킨걸 그냥 flush 함
        em.flush(); // 트랜잭션 종료시 자동 발동됨


        // then
        Board findBoardPS = boardRepository.finById(1);
        assertThat(findBoardPS.getContent()).isEqualTo("내용12");
    }

    @Test
    public void update_dutty_checking_test() { // 영속성 컨텍트에 있는 값이 바뀐것을 더티체킹이 일어났을때 flush가 되는것을 체크하기 위해서
        // given 1 - DB에 영속화
        User user = newUser("ssar"); // 비영속 상태
        User userPS = userRepository.save(user); // 영속화 시킴. PS 붙은거는 컨텍트를 거친것

        // given 2 - request 데이터
        String password = "5678";
        String email = "ssar@gmail.com";

        // when
        userPS.update(password, email); // 변경 감지를 지금은 내가 해준 것
        em.flush(); // 영속성이 된걸 db로 flush 해주는것

        // then
        User updateUserPS = userRepository.finById(1);
        assertThat(userPS.getPassword()).isEqualTo("5678");
    }

    @Test
    public void delete_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar"); // 비영속 상태
        User userPS = userRepository.save(user); // 영속화 시킴. PS 붙은거는 영속성 컨텍트를 거친것
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);

        // given 2 - request queryString 데이터
        // em.clear();
        int id = 1;
        Board findBoardPS = boardRepository.finById(id); // 영속성 컨텍트에 있기 때문에 db에 select가 날아가는게 아니라 영속성 컨텍트에서 캐싱해서 가져오는것

        // when
        boardRepository.delete(findBoardPS);

        // then 
        Board deleteBoardPS = boardRepository.finById(1); // delete는 없는것을 삭제해도 오류가 안터짐 
        assertThat(deleteBoardPS).isNull();// 그러므로 null인것을 찾아서 비교하면 삭제가 안된것을 알 수 있음
    }

    @Test
    public void findById_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        boardRepository.save(board);

        // given 2 - request queryString 데이터
        int id = 1;

        // when
        Board findBoardPS = boardRepository.finById(id);

        // then
        assertThat(findBoardPS.getUser().getUsername()).isEqualTo("ssar");
        assertThat(findBoardPS.getTitle()).isEqualTo("제목1");
    }


    @Test
    public void findAll_test() {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        List<Board> boardList = Arrays.asList(newBoard("ssar", userPS), newBoard("cos", userPS));
        boardList.stream().forEach((board)->{
            boardRepository.save(board);
        });        

        
        // when
        List<Board> boardListPS = boardRepository.findAll();

        // then
        assertThat(boardListPS.size()).isEqualTo(2);
    }

}
