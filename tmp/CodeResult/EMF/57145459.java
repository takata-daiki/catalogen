package net.kodra.supereasy.jpa.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF
{

  private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Super Easy datastore");

  private EMF()
  {
  }

  public static EntityManagerFactory getCreatedFactory()
  {
    return emf;
  }

}
