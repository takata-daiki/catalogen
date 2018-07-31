package com.azhtom.flowdiss.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * @author Daniel Soria <danielsoriacoronel@gmail.com>
 *
 */
public final class EMF {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("sunatdb");

    /**
     * Constructor vacio, para inpedir instanciar la clase.
     */
    private EMF() {
    }

    /**
     * @return EntityManagerFactory
     */
    public static EntityManagerFactory get() {
        return emf;
    }
}