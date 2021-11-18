package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
@EnableTransactionManagement
@Transactional(readOnly = true)
public class UserDaoImpl{

    @PersistenceContext
    private EntityManager entityManager;

    private List<User> users;

    public List<User> index(){
        Query query=entityManager.createQuery("select u from User u",User.class);
        return query.getResultList();
    }

    public User show(int id){
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id=:id",User.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Transactional
    public void save (User user){
        entityManager.persist(user);
    }

    @Transactional
    public void update(int id, User updateUser){
        Query query = entityManager.createQuery("select u from User u where u.id=:id");
        query.setParameter("id",id);
        User userToUpdate = (User) query.getSingleResult();
        userToUpdate.setName(updateUser.getName());
        userToUpdate.setAge(updateUser.getAge());
        userToUpdate.setEmail(updateUser.getEmail());
        userToUpdate.setPassword(updateUser.getPassword());
    }

    @Transactional
    public void delete(int id){
        Query query = entityManager.createQuery("delete from User u where u.id=:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
