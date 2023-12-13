package org.example.Controllers;

import org.bson.types.ObjectId;
import org.example.Model.Admin;
import org.example.Model.Employee;
import org.example.Services.AdminService;
import org.example.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private EmployeeService employeeService;
    @Autowired
    public AdminController(AdminService adminService, EmployeeService employeeService){
        this.adminService=adminService;
        this.employeeService=employeeService;
    }

    //SignUp
    @PostMapping("/signup")
    public String SignUp(@RequestBody Admin admin){
        return adminService.signUpAdmin(admin);
    }

    //SignIn
    @PostMapping("/login")
    public String Login(@RequestBody Map<String,Object> map){
        return adminService.login(map.get("email").toString(),map.get("password").toString());
    }

    //Get Admin
    @GetMapping(value = "/get-admin")
    public Admin getAdmin(HttpServletRequest request){
        ObjectId adminId = (ObjectId) request.getAttribute("adminId");
        return adminService.getAdmin(adminId);
    }

    //create employee
    @PostMapping("/add-emp")
    public Employee createEmp(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    //read all employees
    @GetMapping("/getAllEmp")
    public List<Employee> getAll(){
        return employeeService.getAllEmployees();
    }

    //update employee
    @PutMapping("/updateEmp/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee){
        ObjectId objectId=new ObjectId(id);
        return employeeService.updateEmployee(objectId,employee);
    }

    //delete employee
    @DeleteMapping("/deleteEmp/{id}")
    public void deleteEmp(@PathVariable String id){
        ObjectId objectId=new ObjectId(id);
        employeeService.deleteEmployee(objectId);
    }
}
