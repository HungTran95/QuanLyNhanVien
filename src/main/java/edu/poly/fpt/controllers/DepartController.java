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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.services.DepartService;

@Controller
@RequestMapping("/departs")
public class DepartController {
	@Autowired
	private DepartService departService;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("depart",new Depart());
		return "departs/AddOrUpdate";
	}
	@PostMapping("/saveOrUpdate")
	public String saveoOrUpdate(ModelMap model, Depart depart) {
		String message = "Thêm mới thành công phòng ban!";
		if (depart.getId() != null && depart.getId() > 0 ) {
			message = "Cập nhật thành công phòng ban";
		}
		
		departService.save(depart);
		
		model.addAttribute(depart);
		model.addAttribute("message", message);
		return "departs/AddOrUpdate";
	}
	@GetMapping("/delete/{id}")
	public String delete(ModelMap model,@PathVariable(name = "id") Integer id) {
		departService.deleteById(id);
		return "departs/list";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable(name = "id") Integer id) {
		Optional<Depart> optDepart =departService.findById(id) ;
		if (optDepart.isPresent()) {
			model.addAttribute("depart",optDepart.get());
		} else {
			return list(model);
		}
		
		return "departs/AddOrUpdate";
	}
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		List<Depart> list = (List<Depart>) departService.findAll();
		model.addAttribute("departs",list);
		return "departs/list";
	}
	
	@RequestMapping("/find")
	public String find(ModelMap model,@RequestParam(defaultValue = "") String name) {
		List<Depart> list = departService.findByNameLikeOrderByName(name);
		model.addAttribute("departs",list);
		return "departs/list";
	}
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
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
}
