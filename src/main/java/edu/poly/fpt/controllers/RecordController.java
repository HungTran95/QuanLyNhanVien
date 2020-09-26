package edu.poly.fpt.controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.fpt.models.Record;
import edu.poly.fpt.models.Staff;

@Transactional
@Controller
@RequestMapping("/records")
public class RecordController {
	@Autowired 
	private EntityManagerFactory entityManagerFactory;
	
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("record", new Record());
		return "records/record";
		
	}
	
	@PostMapping("/insert")
	public String insert(ModelMap model, @ModelAttribute("record") Record record) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		try {
			record.setDate(new Date());
			session.save(record);
			model.addAttribute("message", "Thêm mới thành công!!");
		} catch (Exception e) {
			model.addAttribute("message", "Thêm mới thất bại!! " + e.getMessage());
			
		}
		return "records/record";
	}

	@RequestMapping("/reportofstaff")
	public String report(ModelMap model) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		String hql = "SELECT r.staff.id, s.name ,"
				+ "SUM(CASE WHEN r.type=1 THEN 1 ELSE 0 END) ,"
				+ "SUM(CASE WHEN r.type=0 THEN 1 ELSE 0 END) "
				+ "FROM Record r join r.staff s "
				+ "on r.staff.id = s.id GROUP BY s.name, r.staff.id";
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		
		model.addAttribute("records", list);
		
		return "records/reportofstaff";
	}
	
	@RequestMapping("/reportofdepart")
	public String reportofdepart(ModelMap model) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		String hql = "Select d.id, d.name," + 
				"SUM(CASE WHEN type=1 THEN 1 ELSE 0 END)," + 
				"SUM(CASE WHEN type=0 THEN 1 ELSE 0 END) " + 
				"From Record r join r.staff s on r.staff.id = s.id " + 
				"Join s.depart d on d.id = s.depart.id group by d.id,d.name";
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		
		model.addAttribute("records", list);
		
		return "records/reportofdepart";
	}
	
	@ModelAttribute("staffs")
	public List<Staff> getStaffs(){
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		String hql = "From Staff";
		Query query = session.createQuery(hql);
		List<Staff> list = query.list();
		return list;
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
		
		return "views/index";
	}
}

