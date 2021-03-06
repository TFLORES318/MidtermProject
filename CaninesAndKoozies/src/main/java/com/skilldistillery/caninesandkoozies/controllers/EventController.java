package com.skilldistillery.caninesandkoozies.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.caninesandkoozies.data.EventDAOImpl;
import com.skilldistillery.caninesandkoozies.data.UserDAOImpl;
import com.skilldistillery.caninesandkoozies.data.VenueDAOImpl;
import com.skilldistillery.caninesandkoozies.entities.Comment;
import com.skilldistillery.caninesandkoozies.entities.Event;
import com.skilldistillery.caninesandkoozies.entities.User;
import com.skilldistillery.caninesandkoozies.entities.UserEvent;
import com.skilldistillery.caninesandkoozies.entities.Venue;

@Controller
public class EventController {

	@Autowired
	private EventDAOImpl eventDAOImpl;

	@Autowired
	private VenueDAOImpl venueDAOImpl;

	@Autowired
	private UserDAOImpl userDAOImpl;

	@RequestMapping(path = "createEvent.do")
	public ModelAndView createEvent(HttpSession session) {
		ModelAndView mv = new ModelAndView();

		User loggedInUser = (User) session.getAttribute("user");

		if (loggedInUser != null) {
			mv.addObject("user", loggedInUser);
			List<Venue> venues = venueDAOImpl.findAllVenues();
			mv.addObject("venues", venues);
			mv.setViewName("createEvent");
		} else {
			mv.setViewName("index");
		}

		return mv;
	}

	@RequestMapping(path = "forwardEventForCreation.do", params = { "venueId", "eventDate" })
	public ModelAndView eventForward(String eventDate, Event event, HttpSession session, int venueId) {
		ModelAndView mv = new ModelAndView();
		User loggedInUser = (User) session.getAttribute("user");
		Event newEvent = eventDAOImpl.createEvent(event, loggedInUser, venueId, eventDate);
		mv.addObject("event", newEvent);
		mv.setViewName("eventDetails");

		return mv;
	}

	@RequestMapping(path = "updateEvent.do")
	public ModelAndView updateEvent(int id, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		User loggedInUser = (User) session.getAttribute("user");
		Event toUpdate = eventDAOImpl.findEventById(id);

		if (loggedInUser != null) {
			mv.addObject("user", loggedInUser);
			List<Venue> venues = venueDAOImpl.findAllVenues();
			mv.addObject("venues", venues);
			mv.setViewName("createEvent");
		} else {
			mv.setViewName("index");
		}

		mv.addObject("event", toUpdate);
		mv.setViewName("updateEvent");

		return mv;
	}

	@RequestMapping(path = "forwardEventForUpdate.do")
	public ModelAndView forwardUpdate(String eventDate, String createDateToParse, Event event, HttpSession session,
			Integer userCreatedId) {
		ModelAndView mv = new ModelAndView();

		Event updated = eventDAOImpl.updateEvent(eventDate, createDateToParse, event, userCreatedId);
		User loggedInUser = (User) session.getAttribute("user");
		mv.addObject("user", loggedInUser);
		mv.addObject("event", updated);
		mv.setViewName("eventDetails");

		return mv;
	}

	@RequestMapping(path = "deleteEvent.do")
	public ModelAndView deleteEvent(int id) {
		ModelAndView mv = new ModelAndView();

		boolean result = eventDAOImpl.destroy(id);
		String printOut;
		if (result == true) {
			printOut = "Your event has been deleted.";
		} else {
			printOut = "We cannot delete your event at this time,";
		}
		mv.addObject("result", printOut);
		mv.setViewName("resultEventDelete");
		return mv;
	}

	@RequestMapping(path = "signUpForEvent.do")
	public ModelAndView signUpForEvent(int id, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User loggedInUser = (User) session.getAttribute("user");
		UserEvent userEvent = userDAOImpl.addEventToUserEventList(loggedInUser, id);
		Event event = eventDAOImpl.findEventById(id);
		mv.addObject("event", event);
//		mv.addObject("userEvent", userEvent);
		mv.addObject("user", loggedInUser);
		mv.setViewName("eventDetails");
		return mv;

	}
	
	@RequestMapping(path = "searchVenueId.do")
	public ModelAndView findVenue(int id) {
		ModelAndView mv = new ModelAndView();
		Venue venue = venueDAOImpl.findVenueById(id);
		mv.addObject("venue", venue);
		mv.setViewName("venueDetails");
		return mv;
	}

}
