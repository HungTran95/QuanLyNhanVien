package edu.poly.fpt.services;

import java.util.List;
import java.util.Optional;

import edu.poly.fpt.models.Users;

public interface UserService {

	void deleteAll();

	void deleteAll(List<Users> entities);

	void delete(Users entity);

	void deleteById(String id);

	long count();

	List<Users> findAllById(List<String>  ids);

	Iterable<Users> findAll();

	boolean existsById(String id);

	Optional<Users> findById(String id);

	List<Users> saveAll(List<Users>  entities);

	Users save(Users entity);

}
