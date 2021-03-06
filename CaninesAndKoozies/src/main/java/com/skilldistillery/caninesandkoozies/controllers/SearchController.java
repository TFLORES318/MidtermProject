package com.skilldistillery.caninesandkoozies.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.caninesandkoozies.data.EventDAOImpl;
import com.skilldistillery.caninesandkoozies.data.UserDAOImpl;
import com.skilldistillery.caninesandkoozies.entities.Dog;
import com.skilldistillery.caninesandkoozies.entities.Event;
import com.skilldistillery.caninesandkoozies.entities.User;

@Controller
public class SearchController {
	
	@Autowired
	private EventDAOImpl eventDAOImpl;
	
	@Autowired
	private UserDAOImpl userDAOImpl;
	
	@RequestMapping(path="searchId.do")
	public ModelAndView searchId(int id) {
		ModelAndView mv = new ModelAndView();
		
		Event event = eventDAOImpl.findEventById(id);
		mv.addObject("event", event);
		mv.setViewName("eventDetails");
		return mv;
	}
	
	@RequestMapping(path = "search.do")
	public ModelAndView searchKeyword(String keyword) {
		ModelAndView mv = new ModelAndView();
		
		List<Event> events = eventDAOImpl.findEventsByKeyword(keyword);
		List<User> users = userDAOImpl.findUserByKeyword(keyword);
		
		mv.addObject("users", users);
		mv.addObject("events", events);
		
		mv.setViewName("searchResults");
		
		return mv;
	}
	
	@RequestMapping(path="viewTheirProfile.do")
	public ModelAndView otherUserProfile(int id, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		User lookedUpUser = userDAOImpl.findUserById(id);
		User loggedInUser = (User) session.getAttribute("user");
		List<Dog> dogs = userDAOImpl.findAllUserDogs(lookedUpUser.getId());
		List<Event> events = userDAOImpl.findAllUsersEvents(id);
		mv.addObject("user", loggedInUser);
		mv.addObject("lookedUpUser", lookedUpUser);
		mv.addObject("dogs", dogs);
		mv.addObject("events", events);
		mv.setViewName("viewTheirProfile");
		return mv;
	}
	
	@RequestMapping(path = "viewSinglesEvents.do")
	public ModelAndView singlesEvents() {
		ModelAndView mv = new ModelAndView();
		List<Event> singlesEvents = eventDAOImpl.eventsBySinglesOnlyPreference(true);
		mv.addObject("events", singlesEvents);
		mv.setViewName("browseEvents");
		return mv;
	}

}
