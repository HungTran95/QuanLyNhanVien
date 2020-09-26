package edu.poly.fpt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.repositories.DepartRepository;

@Service
public class DepartServiceImpl implements DepartService{
	@Autowired
	private DepartRepository departRepository;

	@Override
	public Depart save(Depart entity) {
		return departRepository.save(entity);
	}

	@Override
	public List<Depart> saveAll(Iterable<Depart> entities) {
		return (List<Depart>) departRepository.saveAll(entities);
	}
	
	
	@Override
	public List<Depart> findByNameLikeOrderByName(String name) {
		return departRepository.findByNameLikeOrderByName( "%" + name + "%");
	}

	@Override
	public Optional<Depart> findById(Integer id) {
		return departRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return departRepository.existsById(id);
	}

	@Override
	public Iterable<Depart> findAll() {
		return departRepository.findAll();
	}

	@Override
	public Iterable<Depart> findAllById(Iterable<Integer> ids) {
		return departRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return departRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		departRepository.deleteById(id);
	}

	@Override
	public void delete(Depart entity) {
		departRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Depart> entities) {
		departRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		departRepository.deleteAll();
	}
	
	
}
