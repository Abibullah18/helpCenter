package com.Help.Center.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Help.Center.Models.Scope;
import com.Help.Center.Repository.ScopeRepo;
@RestController
@CrossOrigin(origins = "*")
public class ScopeController {
	@Autowired
	public ScopeRepo scopeRepo;
	
	//SCOPE TABLE CONTROLLER
		@PostMapping("/scope")
		Scope createScope(@RequestBody Scope scope) {
			return scopeRepo.save(scope);
		}
		@GetMapping("/scope")
		List<Scope>getScope(){
			return scopeRepo.findAll();
		}
		@GetMapping("/scope/{id}")
		Scope getByScopeId(@PathVariable (value = "id")Integer scopeId){
			Scope scope=scopeRepo.findById(scopeId).orElseThrow(()->new RuntimeException());
			return scope;
		}

}
