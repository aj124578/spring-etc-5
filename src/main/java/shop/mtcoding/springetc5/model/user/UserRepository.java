package shop.mtcoding.springetc5.model.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User finById(int id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User save(User user){ // user의 pk가 있으면 merge하고 없으면 save하게 하면 됨

        if (user.getId() == null) { // pk가 없다면
            em.persist(user); // save
        } else{// pk가 있으면 ※ else 이후 ~ return 부분까지는 어차피 더티체킹 할 것이기 때문에 쓸일이 없음
            User userPS = em.find( User.class, user.getId());
            if (userPS != null) {
                em.merge(user); // merge
            }else{
                System.out.println("잘못된 머지를 하셨어요!!");
            }
        }

        return user;
    }

    public void delete(User user){
        em.remove(user);
    }
}
