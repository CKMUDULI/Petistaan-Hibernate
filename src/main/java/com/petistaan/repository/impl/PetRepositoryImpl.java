package com.petistaan.repository.impl;

import java.util.List;
import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.petistaan.config.DatabaseConfig;
import com.petistaan.entity.Pet;
import com.petistaan.repository.PetRepository;

public class PetRepositoryImpl implements PetRepository {
	private SessionFactory sessionFactory = DatabaseConfig.getSessionFactory();

	@Override
	public Pet findPet(int petId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Pet.class, petId);
		}
	}

	@Override
	public Pet findPetWithOwner(int petId) {
		try (Session session = sessionFactory.openSession()) {
			Pet pet = session.get(Pet.class, petId);
			if (Objects.nonNull(pet)) {
				Hibernate.initialize(pet.getOwnerList());
			}
			return pet;
		}
	}

	@Override
	public Pet findPetUsingHql(int petId, boolean lazyLoadingOwners) {
		String hql = lazyLoadingOwners ? "SELECT p FROM Pet p JOIN FETCH p.ownerList WHERE p.id = :petId"
				: "SELECT p FROM Pet p WHERE p.id = :petId";
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(hql, Pet.class).setParameter("petId", petId).getSingleResultOrNull();
		}
	}

	@Override
	public List<Pet> findAllPetsUsingHql(boolean lazyLoading) {
		String hql = lazyLoading ? "SELECT p FROM Pet p JOIN FETCH p.ownerList" : "SELECT p FROM Pet p";
		try (Session session = sessionFactory.openSession()) {
			return session.createSelectionQuery(hql, Pet.class).list();
		}
	}

	@Override
	public double calculateAveragePetAge() {
		String hql = "SELECT CAST(AVG(YEAR(CURRENT_DATE) - YEAR(p.birthDate)) AS INTEGER) FROM Pet p";
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(hql, Integer.class).getSingleResultOrNull();
		}
	}
}
