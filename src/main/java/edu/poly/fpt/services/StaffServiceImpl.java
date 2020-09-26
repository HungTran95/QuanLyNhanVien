package edu.poly.fpt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.Staff;
import edu.poly.fpt.repositories.DepartRepository;
import edu.poly.fpt.repositories.StaffRepository;

@Service
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private DepartRepository departRepository;
	
	@Override
	public List<Depart> findAllDeparts(){
		return (List<Depart>) departRepository.findAll();
	}
	
	@Override
	public Staff save(Staff entity) {
		return staffRepository.save(entity);
	}

	@Override
	public List<Staff> saveAll(Iterable<Staff> entities) {
		return( List<Staff>) staffRepository.saveAll(entities);
	}
 
	@Override
	public Optional<Staff> findById(Long id) {
		return staffRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return staffRepository.existsById(id);
	}

	@Override
	public Iterable<Staff> findAll() {
		return staffRepository.findAll();
	}

	@Override
	public Iterable<Staff> findAllById(Iterable<Long> ids) {
		return staffRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return staffRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		staffRepository.deleteById(id);
	}

	@Override
	public void delete(Staff entity) {
		staffRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Staff> entities) {
		staffRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		staffRepository.deleteAll();
	}

	@Override
	public List<Staff> findByNameLikeOrderByName(String name) {
		return staffRepository.findByNameLikeOrderByName("%" + name + "%");
	}
	
	
}
