package com.Help.Center.Controller;


import java.io.IOException;
import java.util.List;

import com.Help.Center.Models.Company;
import com.Help.Center.Models.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.Help.Center.Models.Roles;
 
import com.Help.Center.Models.Users;
import com.Help.Center.Repository.CompanyRepo;
import com.Help.Center.Repository.RolesRepo;
import com.Help.Center.Repository.ScopeRepo;
import com.Help.Center.Repository.UsersRepo;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {
	@Autowired
	public UsersRepo usersRepo;
	@Autowired
	public CompanyRepo companyRepo;
	@Autowired
	public RolesRepo rolesRepo;
	@Autowired
	public ScopeRepo scopeRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/users")
	ResponseEntity<Users> createUsers(@RequestBody Users users)  {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setDeleted(false);
		usersRepo.save(users);
		return ResponseEntity.ok().body(users);
	}
	@GetMapping("/users")
	 ResponseEntity<UserData> getAllUsers(Pageable pageable){
		UserData userData=new UserData();
		
		Page<Users> usersPage= usersRepo.findAll(pageable);
		List<Users>users=usersPage.getContent();
		Pagination paginated=Pagination.createPagination(usersPage);
		userData.setPagination(paginated); 
		userData.setUsers(users);
	   return ResponseEntity.ok().body(userData);
	
	}
	@GetMapping("/users/{id}") 
	public ResponseEntity<Users>getuser(@PathVariable (value = "id") Integer userId){
		Users users=usersRepo.findById(userId).orElseThrow(()->new RuntimeException("user does not match"));
		return ResponseEntity.ok().body(users);
	}
	@PutMapping("/users/{id}")
	public ResponseEntity<Users>updateUser(@PathVariable (value = "id") Integer userId ,@RequestBody Users userDetail){
		Users users=usersRepo.findById(userId).orElseThrow(()->new RuntimeException("userId does not match"));
		users.setAvatar(userDetail.getAvatar());
		users.setEmail(userDetail.getEmail());
		users.setFirstName(userDetail.getFirstName());
		users.setMiddleName(userDetail.getMiddleName());
		users.setLastName(userDetail.getLastName());
		users.setMobileNo(userDetail.getMobileNo());
		users.setAvatar(userDetail.getAvatar());
		users.setSuffix(userDetail.getSuffix());
		users.setUserType(userDetail.getUserType());
		users.setUserName(userDetail.getUserName());
		users.setPassword(userDetail.getPassword());
		usersRepo.save(users);
		return ResponseEntity.ok().body(users);
				
	}
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Users>deleteUsers(@PathVariable (value = "id")Integer userId){
		
		Users users=usersRepo.findById(userId).orElseThrow(()->new RuntimeException("userId does not Match"));
		users.setDeleted(true);
		usersRepo.save(users);
		return ResponseEntity.ok().body(users);
	}
	@PostMapping("/addRoleToUsers")
	Users addRoleToUsers(@RequestBody sampleInput input) {
		Users users=usersRepo.findById(input.getId1()).orElseThrow(()->new RuntimeException());
		Roles roles=rolesRepo.findById(input.getId2()).orElseThrow(()->new RuntimeException());
		users.setRoles(roles);
		return usersRepo.save(users);
		
	}

	@PostMapping("/signup")
	Users signup(@RequestBody Users users)
	{
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setDeleted(false);
		Company company=new Company();
		company.setCompanyName(users.getCompany().getCompanyName());
		companyRepo.save(company);
		users.setCompany(company);
		users.setActive(false);
		users.setAdmin(false);
		users.setUserType(UserType.CUSTOMER);
		return 	usersRepo.save(users);
	}

	
	
	
	
	
	

}
class sampleInput{
	public int id1;
	public int id2;
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}

}
class UserData{
	private List<Users> users;
	private Pagination pagination;
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
}
