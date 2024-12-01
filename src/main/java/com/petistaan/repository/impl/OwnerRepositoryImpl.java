package com.petistaan.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.petistaan.config.DatabaseConfig;
import com.petistaan.entity.Owner;
import com.petistaan.entity.Pet;
import com.petistaan.repository.OwnerRepository;

public class OwnerRepositoryImpl implements OwnerRepository {
	private final SessionFactory sessionFactory = DatabaseConfig.getSessionFactory();

	@Override
	public void saveOwner(Owner owner) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.persist(owner);
			transaction.commit();
		}
	}

	@Override
	public Owner findOwner(int ownerId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Owner.class, ownerId);
		}
	}

	@Override
	public Owner findOwnerWithPet(int ownerId) {
		try (Session session = sessionFactory.openSession()) {
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				Hibernate.initialize(owner.getPetList());
			}
			return owner;
		}
	}

	@Override
	public void updatePetDetails(int ownerId, int petId, String petName) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				owner.getPetList().stream().filter(pet -> pet.getId() == petId).findFirst()
						.ifPresent(pet -> pet.setName(petName));
			}
			session.merge(owner);
			transaction.commit();
		}

	}

	@Override
	public void deleteOwner(int ownerId) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				owner.getPetList().stream().filter(pet -> pet.getOwnerList().size() == 1).forEach(session::remove);
			}
			session.remove(owner);
			transaction.commit();
		}
	}

	@Override
	public void addPet(int ownerId, Pet pet) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				owner.getPetList().add(pet);
			}
			session.merge(owner);
			transaction.commit();
		}
	}

	@Override
	public void removePet(int ownerId, int petId) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				owner.getPetList().stream().filter(pet -> pet.getId() == petId)
						.filter(pet -> pet.getOwnerList().size() == 1).forEach(session::remove);
				owner.getPetList().removeIf(pet -> pet.getId() == petId);
			}
			session.merge(owner);
			transaction.commit();
		}
	}

	@Override
	public void addCoOwner(int petId, Owner owner) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Pet pet = session.get(Pet.class, petId);
			if (Objects.nonNull(pet)) {
				owner.getPetList().add(pet);
				session.persist(owner);
			}
			transaction.commit();
		}
	}

	@Override
	public void addCoOwner(int petId, int ownerId) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Pet pet = session.get(Pet.class, petId);
			Owner owner = session.get(Owner.class, ownerId);
			if (Objects.nonNull(pet)) {
				owner.getPetList().add(pet);
				session.persist(owner);
			}
			transaction.commit();
		}
	}

	@Override
	public Owner findOwnerUsingHql(int ownerId, boolean lazyLoadingPets) {
		String hql = lazyLoadingPets ? "SELECT o FROM Owner o JOIN FETCH o.petList WHERE o.id = :ownerId"
				: "SELECT o FROM Owner o WHERE o.id = :ownerId";
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(hql, Owner.class).setParameter("ownerId", ownerId).getSingleResultOrNull();
		}
	}

	@Override
	public List<Owner> findAllOwnersUsingHql(boolean lazyLoading) {
		String hql = lazyLoading ? "SELECT o FROM Owner o JOIN FETCH o.petList" : "SELECT o FROM Owner o";
		try (Session session = sessionFactory.openSession()) {
			return session.createSelectionQuery(hql, Owner.class).list();
		}
	}

	@Override
	public List<Owner> findOwnerByInitialsOfFirstName(String ownerInitials) {
		String hql = "SELECT o FROM Owner o WHERE UPPER(o.firstName) LIKE CONCAT(UPPER(:ownerInitials), '%')";
		try (Session session = sessionFactory.openSession()) {
			return session.createSelectionQuery(hql, Owner.class).setParameter("ownerInitials", ownerInitials).list();
		}
	}

	@Override
	public List<Owner> findOwnersByPetsBirthDate(LocalDate startDate, LocalDate endDate) {
		String hql = "SELECT o FROM Owner o WHERE element(o.petList).birthDate BETWEEN :startDate AND :endDate";
		try (Session session = sessionFactory.openSession()) {
			return session.createSelectionQuery(hql, Owner.class).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		}
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize) {
		// String hql = "SELECT o.id, o.firstName, o.lastName, element(o.petList).name FROM Owner o";
		// String hql = "SELECT o.id, o.firstName, o.lastName, GROUP_CONCAT(pl.name) FROM Owner o LEFT JOIN o.petList pl GROUP BY o.id";
		String hql = "SELECT o.id, o.firstName, o.lastName, listagg(pl.name, ', ') FROM Owner o JOIN o.petList pl GROUP BY o.id";
		try (Session session = sessionFactory.openSession()) {
			return session.createSelectionQuery(hql, Object[].class).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).list();
		}
	}
}
