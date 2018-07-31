package com.teste.jpaUiBinder.server.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {

	private static EntityManagerFactory emfInstance;

	private EMF() {}

	public static EntityManagerFactory getInstance(String persistenceUnitName) {
		if (emfInstance == null){
			emfInstance = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		return emfInstance;
	}
}