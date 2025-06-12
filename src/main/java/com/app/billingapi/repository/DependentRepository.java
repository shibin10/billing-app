package com.shiel.campaignapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiel.campaignapi.entity.Dependent;

public interface DependentRepository  extends JpaRepository<Dependent,Long>{

	
}
