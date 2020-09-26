package edu.poly.fpt.controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.fpt.models.Users;
import edu.poly.fpt.services.UserService;

@Controller
@RequestMapping("/user")
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@GetMapping("/login")
	public String login() {
		return "users/login2";
	}
	
	@PostMapping("/login")
	public String login(ModelMap model, 
			@RequestParam("username") String name,
			@RequestParam("password") String pass ) {
		 Optional <Users> optUser =	userService.findById(name);
		 if(optUser.isPresent()) {
				if(optUser.get().getPassword().equals(pass)) {
					model.addAttribute("username", optUser.get());
					
					Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
					String hql = "select s.name, d.name, s.id from Record r " + 
							"join r.staff s on s.id = r.staff.id " + 
							"join s.depart d on s.depart.id = d.id " + 
							"group by s.name, d.name ,s.id " + 
							"order by (SUM(CASE WHEN r.type=1 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type=0 THEN 1 ELSE 0 END)) desc";
					Query query = session.createQuery(hql).setMaxResults(9);
					List<Object[]> list = query.list();
					
					model.addAttribute("awards",list);
					
					return"views/index";
				
				}
				else {
					model.addAttribute("mesages","Password failed");
					return "users/login2";
				}
			 }
			else {
				model.addAttribute("mesage","Username failed");
				return "users/login2";
			}
	
	}
	
	
	
}
