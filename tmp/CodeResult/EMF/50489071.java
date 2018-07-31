package com.smappee.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
	 private static EntityManagerFactory emf = Persistence.createEntityManagerFactory( /*"transactions-optional*/ "RESOURCE_LOCAL" );
	 //"RESOURCE_LOCAL";	"transactions-optional"
	 
	 private EMF()
	  {}
	 
	 public static EntityManagerFactory get()
	  { return emf;
	  }
	 
     
}
