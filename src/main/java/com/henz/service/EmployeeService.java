package com.henz.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henz.custom_exception.BusinessException;
import com.henz.entity.Employee;
import com.henz.repos.EmployeeRepo;

/*
 * 
 * BusinessExceptions are catched in Controller
 * 
 * */

@Service
public class EmployeeService implements EmployeeServiceInterface{
	
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee addEmployee(Employee e) {
		
		//never put validation with throwing exception inside a try block!!!
		
		if(e.getName().isEmpty() || e.getName().length() == 0) {
			throw new BusinessException("601","Please send proper name, its blank");
		}
		
		Employee savedEmp = null;
		
		try {
			
			savedEmp = this.employeeRepo.save(e); //if IllegalArgumentException or Exception occurs, catch it in next catch blocks
			
			
		}catch (IllegalArgumentException exception) {
			throw new BusinessException("602","given employee is null: "+exception.getMessage());
			
		}catch(Exception exception) {
			throw new BusinessException("603","Something went wrong in service layer while saving employee: "+exception.getMessage());
		}
		
		return savedEmp;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		List<Employee> empList = null;
		
		try {
			
			empList = this.employeeRepo.findAll(); //if exception occurs here, catch it in catch block
			
			
		}catch(Exception exception) {
			throw new BusinessException("605","Something went wrong in service layer while fetching all employees: "+exception.getMessage());
		}
		
		if(empList.isEmpty()) {
			throw new BusinessException("604","Employee list is empty, nothing to return");
		}
		
		return empList; //return list if no exception occurred
		
	}

	@Override
	public Employee getEmployeeById(Long empId) {
		
		Employee emp = null;
		
		try {
			
			emp = this.employeeRepo.findById(empId).get();
			
		} catch (IllegalArgumentException e) {
			throw new BusinessException("606","given employee is null, please send some id: "+e.getMessage());
			
		}catch(NoSuchElementException e) {
			throw new BusinessException("607","given employee id does not exist in DB: "+e.getMessage());
		}
		
		return emp;
	}

	@Override
	public void deleteEmployeeById(Long empId) {
		
		try {
			this.employeeRepo.deleteById(empId);
			
		} catch (IllegalArgumentException e) {
			throw new BusinessException("608","given employee id does not exist in DB: "+e.getMessage());
		}
	}
	
	

}
