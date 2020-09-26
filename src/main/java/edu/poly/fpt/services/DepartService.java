package edu.poly.fpt.services;

import java.util.List;
import java.util.Optional;

import edu.poly.fpt.models.Depart;

public interface DepartService {

	void deleteAll();

	void deleteAll(List<Depart> entities);

	void delete(Depart entity);

	void deleteById(Integer id);

	long count();

	Iterable<Depart> findAllById(Iterable<Integer> ids);

	Iterable<Depart> findAll();

	boolean existsById(Integer id);

	Optional<Depart> findById(Integer id);

	List<Depart> saveAll(Iterable<Depart> entities);

	Depart save(Depart entity);

	List<Depart> findByNameLikeOrderByName(String name);

}
