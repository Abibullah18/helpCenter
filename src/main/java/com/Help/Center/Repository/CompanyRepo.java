package com.Help.Center.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Help.Center.Models.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
	Company findBycompanyName(String companyName);

}
