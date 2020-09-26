package edu.poly.fpt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.fpt.models.Users;
import edu.poly.fpt.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
@Autowired
UserRepository  userRepository;

@Override
public Users save(Users entity) {
	return userRepository.save(entity);
}

@Override
public List<Users> saveAll(List<Users>  entities) {
	return (List<Users>) userRepository.saveAll(entities);
}

@Override
public Optional<Users> findById(String id) {
	return userRepository.findById(id);
}

@Override
public boolean existsById(String id) {
	return userRepository.existsById(id);
}

@Override
public Iterable<Users> findAll() {
	return userRepository.findAll();
}

@Override
public List<Users>  findAllById(List<String>  ids) {
	return (List<Users>) userRepository.findAllById(ids);
}

@Override
public long count() {
	return userRepository.count();
}

@Override
public void deleteById(String id) {
	userRepository.deleteById(id);
}

@Override
public void delete(Users entity) {
	userRepository.delete(entity);
}

@Override
public void deleteAll(List<Users> entities) {
	userRepository.deleteAll(entities);
}

@Override
public void deleteAll() {
	userRepository.deleteAll();
}

}
