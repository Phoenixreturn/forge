package ru.habrahabr.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.habrahabr.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	List<Employee> findByFullNameLike(String fullName);

	@Query(value = "{ 'parentEmployee.id' : ?0 }")
	List<Employee> findEmployees(String id);
}