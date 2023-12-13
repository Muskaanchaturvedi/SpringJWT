package org.example.Services;

import org.bson.types.ObjectId;
import org.example.Model.Admin;
import org.example.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private TokenService tokenService;
    public AdminService(AdminRepository adminRepository, TokenService tokenService){
        this.adminRepository=adminRepository;
        this.tokenService=tokenService;
    }

    //SignUp Admin
    public String signUpAdmin(Admin admin){
        Admin savedAdmin=adminRepository.save(admin);
        return
                "{"+
                        "\"message\": \"Successfully created the admin\" ,\n"+
                        "\"data\":" + savedAdmin +
                        "}";
    }

    //SignIn Admin
    public String login(String email, String password){
        List<Admin> foundAdmins=adminRepository.findByEmail(email);
        if(foundAdmins.isEmpty()){
            return "Authentication failed User not found";
        } else if(!foundAdmins.get(0).getPassword().equals(password)) {
            return "Incorrect Password";
        }
        return "{\n" +
                "\"message\":"  + "\"Logged in Successfully!\",\n"+
                "\"data\":" +
                "{\n" +
                "name :" + foundAdmins.get(0).getName() +",\n"+
                "email :" + foundAdmins.get(0).getEmail() +",\n"+
                "token :" + tokenService.generateToken(foundAdmins.get(0).getId())
                +"}"
                +"}";
    }
    //get- Admin by id
    public Admin getAdmin(ObjectId id){
        Optional<Admin> optionalUser=adminRepository.findById(id);
        return optionalUser.orElseGet(optionalUser::get);
    }


}

