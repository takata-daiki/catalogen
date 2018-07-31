package com.karta.pojazdu.ejb.manager;

import com.karta.pojazdu.cmp.PicturesBean;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wielik
 * Date: 17.02.13
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PicturesManager {
    private static final Logger logger = Logger.getLogger(PicturesManager.class);

    @PersistenceContext(name = "KartaPojazduPU")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<PicturesBean> getAllPictures() {
        Query query = entityManager.createQuery("SELECT i FROM PicturesBean i");
        return query.getResultList();
    }

}