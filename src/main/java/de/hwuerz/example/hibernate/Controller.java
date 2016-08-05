package de.hwuerz.example.hibernate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Hendrik on 29.07.2016.
 */
@ManagedBean
@SessionScoped
public class Controller {


    @PersistenceContext(unitName = "MyPU")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<MyTable> get() {
        return entityManager.createNativeQuery("select * from MyTable", MyTable.class).getResultList();
    }

}
