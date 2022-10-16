package com.henz.service;

import java.util.List;

import com.henz.entity.Employee;

public interface EmployeeServiceInterface {

	Employee addEmployee(Employee e);

	List<Employee> getAllEmployees();

	Employee getEmployeeById(Long empId);

	void deleteEmployeeById(Long empId);

}
