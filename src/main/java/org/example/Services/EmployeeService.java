package org.example.Services;

import org.bson.types.ObjectId;
import org.example.Model.Employee;
import org.example.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    //create employee
    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    //read employee by id
    public Employee getEmployeeById(ObjectId empId){
        Optional<Employee>employeeFound= employeeRepository.findById(empId);
        return employeeFound.orElseGet(employeeFound::get);
    }

    //read all the employees
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //update employee
    public Employee updateEmployee(ObjectId empId, Employee editEmp){
        Employee tempEmployee=employeeRepository.findById(empId).orElseThrow(()->new RuntimeException("No such employee exist"));

        if(editEmp.getName()!=null | !editEmp.getName().isEmpty()){
            tempEmployee.setName(editEmp.getName());
        }
        if(editEmp.getDomain()!=null | !editEmp.getDomain().isEmpty()){
            tempEmployee.setDomain(editEmp.getDomain());
        }
        employeeRepository.save(tempEmployee);
        return tempEmployee;
    }

    //delete employee
    public void deleteEmployee(ObjectId empId){
        Optional<Employee> deleteEmployee=employeeRepository.findById(empId);
        if(deleteEmployee.isEmpty()){
            throw new RuntimeException("No such Employee Exists");
        }
        employeeRepository.deleteById(empId);
    }
}
