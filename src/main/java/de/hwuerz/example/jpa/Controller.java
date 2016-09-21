package de.hwuerz.example.jpa;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

/**
 * Created by Hendrik on 29.07.2016.
 */
@ManagedBean
@SessionScoped
@Stateful
@Named
public class Controller {


    @PersistenceContext(unitName = "MyPU")
    private EntityManager entityManager;

//    @Resource
//    private UserTransaction utx;

    @SuppressWarnings("unchecked")
    public List<User> get() {
        return entityManager.createNativeQuery("select * from User", User.class).getResultList();
    }

    public void add() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        User user = new User();
        user.setName("New User2");

//        utx.begin();
        entityManager.persist(user);
//        utx.commit();
    }

    public void change() {
        get().forEach(user -> user.setName(user.getName() + "_"));
    }
}
