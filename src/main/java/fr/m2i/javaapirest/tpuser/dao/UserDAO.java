
package fr.m2i.javaapirest.tpuser.dao;

import fr.m2i.javaapirest.tpuser.exception.UserDaoErrorException;
import fr.m2i.javaapirest.tpuser.exception.UserNotFoundException;
import fr.m2i.javaapirest.tpuser.model.User;
import fr.m2i.javaapirest.tpuser.util.SessionHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UserDAO {
    
    private EntityManager entityManager;
    
    public UserDAO(){
        entityManager = SessionHelper.getEntityManager();
    }
    
     public List<User> findAll() {
        Query findAllQuery = entityManager.createQuery("select u from User u");
        return findAllQuery.getResultList();
    }
     
     public User findById(Integer id){
         User founded = null;
        try{
            founded = entityManager.find(User.class, id);
        }catch(Exception e){
            throw new UserDaoErrorException("An error occurred ", e.getCause());
        }
        if (founded == null) {
            throw new UserNotFoundException("User was not found");
        }
        return founded;
    }
     
     public User findByEmail(String email) { // email est unique

        if (email == null || "".equals(email)) {
            System.out.println("L'email n'est pas valide !");
            return null;
        }
        try{
            TypedQuery<User> findByUserQuery = entityManager.createQuery("select u from User u where u.email = ?1", User.class);
            findByUserQuery.setParameter(1, email);

            return findByUserQuery.getSingleResult();
         }catch(Exception e){
             throw new UserDaoErrorException("An error occurred ", e.getCause());
         }
    }
     
     public List<User> findByEmailOrLastname(String str, int count) { 
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email = ?1 OR u.lastname=?2", User.class);
        query.setMaxResults(count);
        query.setParameter(1, str);
        query.setParameter(2, str);

        return query.getResultList();       
    }
     
     public boolean checkValidityUserData(User user){
         if(user == null || user.getLastname()==null || "".equals(user.getLastname())
            || user.getFirstname()==null || "".equals(user.getFirstname())
            || user.getEmail()==null || "".equals(user.getEmail())
            || user.getPassword()==null || "".equals(user.getPassword())
            || user.getRole()==null || "".equals(user.getRole())){
             
             return false;
         }
         return true;
     }
     
     public User create(User user) {

        if (user == null) {
            System.out.println("L'objet user ne peut pas être null");
            return null;
        }

        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();
            
            entityManager.persist(user);
            
            tx.commit();
            
            return findByEmail(user.getEmail());
            
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new UserDaoErrorException("An error occurred ", e.getCause());
        }
    }
     
      public User update(Integer id, User user) {

        User userToUpdate = findById(id);
        
        if (userToUpdate == null) {
            throw new UserNotFoundException("User was not found");
        }

        userToUpdate.copy(user);

        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.merge(userToUpdate);

            tx.commit();
            return findById(id);
            
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new UserDaoErrorException("An error occurred ", e.getCause());
            
        }
    }
      
       public boolean delete(Integer id) {
        
        User userToDelete = findById(id);

        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.remove(userToDelete);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new UserDaoErrorException("An error occurred ", e.getCause());

        }
        
    }
    
}
