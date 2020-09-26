package edu.poly.fpt.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.fpt.dtos.StaffDto;
import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.Staff;
import edu.poly.fpt.services.StaffService;

@Controller
@RequestMapping("/staffs")
public class StaffController {
	@Autowired
	private StaffService staffService;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("staffs", staffService.findAll());
		return "staffs/list";
	}
	
	@GetMapping("/add")
	public String add(ModelMap model) {
		StaffDto staffDto = new StaffDto();
		
		model.addAttribute("staffDto", staffDto);
		return "staffs/addOrUpdate";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(ModelMap model,@PathVariable(name = "id") Long id) {
		staffService.deleteById(id);
		model.addAttribute("staffs", staffService.findAll());
		
		return "staffs/list";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable(name = "id") Long id) {
		Optional<Staff> optStaff = staffService.findById(id) ;
		if (optStaff.isPresent()) {
			StaffDto dto = new StaffDto();
			Staff staff = optStaff.get();
			dto.setBirthday(staff.getBirthday());
			dto.setDepartId(staff.getDepart().getId());
			dto.setName(staff.getName());
			dto.setEmail(staff.getEmail());
			dto.setGender(staff.getGender());
			dto.setPhone(staff.getPhone());
			dto.setNotes(staff.getNotes());
			dto.setSalary(staff.getSalary());
			dto.setLevell(staff.getLevell());
			dto.setId(staff.getId());
			model.addAttribute("staffDto",dto);
		} else {
			return list(model);
		}
		
		return "staffs/addOrUpdate";
	}
	
	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(ModelMap model,@Validated StaffDto staffDto,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("message", "Hãy nhập đầy đủ các trường dữ liệu");
			model.addAttribute("staffDto", staffDto);
			return "staffs/addOrUpdate";
		}
		if (staffDto.getId() != null && staffDto.getId()>0) {
			model.addAttribute("message", "Đã cập nhật thành công!");
		} else {
			model.addAttribute("message", "Thêm mới thành công!!");
		}
		
		Path path = Paths.get("images/");
		
		try (InputStream inputStream = staffDto.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(staffDto.getImage().getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			String filename = staffDto.getImage().getOriginalFilename();
		} catch (Exception e) {
			model.addAttribute("message", "Lỗi lưu file !" + e.getMessage());
		}
		
		Staff staff = new Staff();
		staff.setId(staffDto.getId());
		staff.setName(staffDto.getName());
		staff.setEmail(staffDto.getEmail());
		staff.setGender(staffDto.getGender());
		staff.setNotes(staffDto.getNotes());
		staff.setPhone(staffDto.getPhone());
		staff.setPhoto(staffDto.getImage().getOriginalFilename());
		staff.setSalary(staffDto.getSalary());
		staff.setBirthday(staffDto.getBirthday());
		staff.setLevell(staffDto.getLevell());
		Depart depart = new Depart();
		depart.setId(staffDto.getDepartId());
		staff.setDepart(depart);
		
		staffService.save(staff);
		model.addAttribute("staffDto", staffDto);
		return "staffs/addOrUpdate";
		
	}
	
	@ModelAttribute( name = "departs")
	public List<Depart> getDepart(){
		return staffService.findAllDeparts();
		
	}
	
	@RequestMapping("/find")
	public String find(ModelMap model,@RequestParam(defaultValue = "") String name) {
		List<Staff> list = staffService.findByNameLikeOrderByName(name);
		model.addAttribute("staffs",list);
		return "staffs/list";
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
