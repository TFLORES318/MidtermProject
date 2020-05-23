package com.skilldistillery.caninesandkoozies.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.caninesandkoozies.entities.Event;
import com.skilldistillery.caninesandkoozies.entities.User;
import com.skilldistillery.caninesandkoozies.entities.Venue;

@Service
@Transactional
public class EventDAOImpl implements EventDAO {

	@PersistenceContext
	private EntityManager em;

	public Event createEvent(Event event) {

		event.setUserCreated(em.find(User.class, event.getUserCreated()));
		event.setVenue(em.find(Venue.class, event.getVenue()));

		em.persist(event);
		em.flush();
		em.close();

		return event;
	}

	public List<Event> findAllEvents() {
		String jpql = "SELECT e FROM Event e";
		List<Event> events;
		events = em.createQuery(jpql, Event.class).getResultList();

		em.close();
		return events;
	}

	public List<Event> eventsBySinglesOnlyPreference(Boolean singlesOnly) {
		List<Event> events;
		String jpql = "SELECT e FROM Event e WHERE e.singleOnlyPreference = :true";
		events = em.createQuery(jpql, Event.class).setParameter("true", singlesOnly).getResultList();

		return events;
	}

	public List<Event> eventsByDogSizePreference(String dogSizePref) {
		List<Event> events;
		String jpql = "SELECT e FROM Event e WHERE e.dog_size_preference = :dogSize";
		events = em.createQuery(jpql, Event.class).setParameter("dogSize", dogSizePref).getResultList();

		return events;
	}

	public Event updateEvent(Event event) {

		Event updatedEvent = em.find(Event.class, event.getId());
		updatedEvent.setName(event.getName());
		updatedEvent.setEventDateTime(event.getEventDateTime());
		updatedEvent.setDogSizePreference(event.getDogSizePreference());
		updatedEvent.setSingleOnlyPreference(event.isSingleOnlyPreference());
		updatedEvent.setPictureURL(event.getPictureURL());
		updatedEvent.setDescription(event.getDescription());
		updatedEvent.setCreateDate(event.getCreateDate());
		updatedEvent.setUpdateDate(event.getUpdateDate());
		updatedEvent.setComments(event.getComments());
		updatedEvent.setVenue(event.getVenue());
		updatedEvent.setUserCreated(event.getUserCreated());
		updatedEvent.setUserEvents(event.getUserEvents());

		em.flush();
		em.close();

		return updatedEvent;
	}

	public boolean destroy(int id) {
		Event event = em.find(Event.class, id);

		em.remove(event);

		boolean stillContains = em.contains(event);

		em.flush();
		em.close();

		return !stillContains;
	}

}