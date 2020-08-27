package ru.habrahabr.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Employee {

	@Id
	private String id;

	@DBRef
	private Employee parentEmployee;

	private String fullName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getParentEmployee() {
		return parentEmployee;
	}

	public void setParentEmployee(Employee parentEmployee) {
		this.parentEmployee = parentEmployee;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return fullName;
	}
}
