package io.github.andresgois.generic;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryGeneric {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("ExampleDS");

	private EntityManagerFactoryGeneric() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
