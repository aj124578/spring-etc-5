package shop.mtcoding.springetc5.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository { // <T> 로 바꿔보기
    
    private final EntityManager em;

    public Board finById(int id){
        return em.find(Board.class, id);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }
    // board도 entity로 바꿔서 귀찮게 안바꿔도 되게 만들기
    public Board save(Board board){ // user의 pk가 있으면 merge하고 없으면 save하게 하면 됨

        if (board.getId() == null) { // pk가 없다면
            em.persist(board); // save
        } else{// pk가 있으면 ※ else 이후 ~ return 부분까지는 어차피 더티체킹 할 것이기 때문에 쓸일이 없음
            Board boardPS = em.find( Board.class, board.getId());
            if (boardPS != null) {
                em.merge(board); // merge
            }else{
                System.out.println("잘못된 머지를 하셨어요!!");
            }
        }

        return board;
    }

    public void delete(Board board){
        em.remove(board);
    }
}
