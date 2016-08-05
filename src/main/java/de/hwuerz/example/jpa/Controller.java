package de.hwuerz.example.jpa;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

/**
 * Created by Hendrik on 29.07.2016.
 */
@ManagedBean
@SessionScoped
public class Controller {


    @PersistenceContext(unitName = "MyPU")
    private EntityManager entityManager;

    @Resource
    private UserTransaction utx;

    @SuppressWarnings("unchecked")
    public List<User> get() {
        return entityManager.createNativeQuery("select * from User", User.class).getResultList();
    }

    public void add() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        User user = new User();
        user.setName("New User");

        utx.begin();
        entityManager.persist(user);
        utx.commit();
    }

}
