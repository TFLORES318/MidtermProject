package com.skilldistillery.caninesandkoozies.data;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.caninesandkoozies.entities.Address;
import com.skilldistillery.caninesandkoozies.entities.Comment;
import com.skilldistillery.caninesandkoozies.entities.Dog;
import com.skilldistillery.caninesandkoozies.entities.Event;
import com.skilldistillery.caninesandkoozies.entities.User;
import com.skilldistillery.caninesandkoozies.entities.UserEvent;
import com.skilldistillery.caninesandkoozies.entities.UserEventId;




@Service
@Transactional
public class UserDAOImpl implements UserDAO{
	
	@PersistenceContext
	private EntityManager em;

	
	@Override
	public User createUser(User newUser, String bday, Address address) {
		LocalDate ld = LocalDate.parse(bday);
		newUser.setBirthDate(ld);
		em.persist(newUser);
		em.persist(address);
		newUser.setAddress(address);
		em.flush();
		
		return newUser;
	}
	
	@Override
	public User updateUser(int id, User user, Address address) {
		
		User updatedUser = em.find(User.class, id);
		
		Address addressToUpdate = updatedUser.getAddress();
		
		addressToUpdate.setStreet(address.getStreet());
		addressToUpdate.setCity(address.getCity());
		addressToUpdate.setState(address.getState());
		addressToUpdate.setZipCode(address.getZipCode());
		
		updatedUser.setFname(user.getFname());
		updatedUser.setLname(user.getLname());
//		updatedUser.setAddress(user.getAddress());
		updatedUser.setAlcoholPreference(user.getAlcoholPreference());
		updatedUser.setRelationshipStatus(user.getRelationshipStatus());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setUsername(user.getUsername());
		updatedUser.setPassword(user.getPassword());
		updatedUser.setUserPicture(user.getUserPicture());
		em.flush();
		return updatedUser;
	}
	
	@Override
	public boolean deleteUser(int id) {
		User user = em.find(User.class, id);
		List<Comment> comments = user.getComments();
		if (comments != null) {
			for (Comment comment : comments) {
				em.remove(comment);
				em.flush();
			}
		}
		
		List<Dog> dogs = user.getDogs();
		if (dogs != null) {
			for (Dog dog : dogs) {
				em.remove(dog);
				em.flush();
			}
		}
		
		List<UserEvent> userEvents = user.getUserEvents();
		if (userEvents != null) {
			for (UserEvent userEvent : userEvents) {
				em.remove(userEvent);
				em.flush();
			}
		}
		
		List<Event> eventsCreated = user.getEvents();
		if (eventsCreated != null) {
			for (Event event : eventsCreated) {
				em.remove(event);
				em.flush();
			}
		}
		em.remove(user);
		em.flush();
		boolean stillContains = em.contains(em.find(User.class, id));
		System.out.println(stillContains);
		return !stillContains;
	}

	@Override
	public Dog createDog(Dog newDog) {
		newDog.setUser(em.find(User.class, newDog.getUser()));
		em.persist(newDog);
		em.flush();
		
		return newDog;
	}
	
	@Override
	public Dog updateDog(int id, Dog dog) {
		Dog updatedDog = em.find(Dog.class, id);
		
		updatedDog.setName(dog.getName());
		updatedDog.setSize(dog.getSize());
		updatedDog.setDogPicture(dog.getDogPicture());
		updatedDog.setBreed(dog.getBreed());
		return updatedDog;
	}
	
	@Override
	public boolean deleteDog(int id) {
		em.remove(em.find(Dog.class, id));
		em.flush();
		boolean stillContains = em.contains(em.find(Dog.class, id));
		System.out.println(stillContains);
		return false;
	}
	

	@Override
	public List<Dog> findAllUserDogs(int id) {
		String jpql = "SELECT d from Dog d where d.user.id =:id";
		List<Dog> dogs;
		dogs = em.createQuery(jpql, Dog.class).setParameter("id", id).getResultList();
		return dogs;
	}

	public List<Event> findAllUsersEvents(int id) {
		String jpql = "SELECT e from Event e JOIN UserEvent ue ON e.id = ue.event.id JOIN User u on u.id = ue.user.id WHERE u.id = :id ";
		List<Event>events;
		events=em.createQuery(jpql, Event.class).setParameter("id", id).getResultList();
		return events;
		}

	@Override
	public Event createEvent(Event newEvent) {
		em.persist(newEvent);
		em.flush();
		return newEvent;
	}

	@Override
	public Event updateEvent(int id, Event event) {
		Event updatedEvent = em.find(Event.class, id);
		
		updatedEvent.setDescription(event.getDescription());
		updatedEvent.setVenue(event.getVenue());
		updatedEvent.setEventDateTime(event.getEventDateTime());
		updatedEvent.setDogSizePreference(event.getDogSizePreference());
		updatedEvent.setName(event.getName());
		updatedEvent.setPictureURL(event.getPictureURL());
		updatedEvent.setSingleOnlyPreference(event.isSingleOnlyPreference());
		
		return updatedEvent;
	}

	@Override
	public List<Event> findAllCreatedEvents(int id) {
		String jpql = "SELECT e from Event e where e.userCreated.id =:id";
		List<Event> events;
		events = em.createQuery(jpql, Event.class).setParameter("id", id).getResultList();
		return events;
	}

	@Override
	public boolean deleteEvent(int id) {
		em.remove(em.find(Event.class, id));
		em.flush();
		boolean stillContains = em.contains(em.find(Event.class, id));
		System.out.println(stillContains);
		return !stillContains;
}
	
	public UserEvent addEventToUserEventList(User user, int id) {
		User managedUser = em.find(User.class, user.getId());
		UserEventId ueid = new UserEventId();
		UserEvent userEvent = new UserEvent();
		ueid.setUserId(user.getId());
		ueid.setEventId(id);
		userEvent.setUser(managedUser);
		Event event = em.find(Event.class, id);
		userEvent.setEvent(event);
		userEvent.setId(ueid);
		managedUser.addUserEvent(userEvent);
		event.addUserEvent(userEvent);
		em.persist(userEvent);
//		List<UserEvent> events = new ArrayList<>();
//		Event event = em.find(Event.class, id);
//		UserEventId userEvent = em.find(UserEventId.class, id);
//		userEvent.setEvent(event);
//		user.addUserEvent(userEvent);
//		events.add(userEvent);
//		em.persist(userEvent);
		return userEvent;
	}
	
	public void removeEventFromUserEventList(int id, User user) {
		Event event = em.find(Event.class, id);
		User managedUser = em.find(User.class, user.getId());
		
		List <UserEvent> usersEvents = managedUser.getUserEvents();
		
		for (UserEvent userEvent : usersEvents) {
			if (userEvent.getEvent().equals(event)) {
				em.remove(userEvent);
			}
		}
		
		em.persist(managedUser);
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		String jpql = "SELECT u FROM User u WHERE u.username =:un and u.password =:p";
		User user = em.createQuery(jpql, User.class).setParameter("un", username).setParameter("p", password).getSingleResult();
		return user;
	}

	@Override
	public User findUserById(int id){
		return em.find(User.class, id);
		}

	@Override
	public List<User> findUserByKeyword(String keyword) {
		List<User> resultPool = null;
		String jpql = "SELECT search FROM User search WHERE search.username like :key or search.fname like :key or search.lname like :key";
		resultPool = em.createQuery(jpql, User.class).setParameter("key", "%" + keyword + "%").getResultList();
		return resultPool;
	}


}

