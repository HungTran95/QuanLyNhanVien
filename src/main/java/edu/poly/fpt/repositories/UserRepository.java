package edu.poly.fpt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.fpt.models.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, String> {

}
