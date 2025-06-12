package com.shiel.campaignapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shiel.campaignapi.dto.DependentDto;
import com.shiel.campaignapi.entity.Dependent;
import com.shiel.campaignapi.repository.DependentRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class DependentsService {
	private final DependentRepository dependentRepository;

	public DependentsService(DependentRepository dependentRepository) {
		this.dependentRepository = dependentRepository;
	}

	public Dependent updateDependent(DependentDto dependentDto) {
		try {
			Optional<Dependent> dependentOptional = dependentRepository.findById(dependentDto.getDependentId());
			if (dependentOptional.isPresent()) {
				Dependent dependent = dependentOptional.get();

				dependent.setName(dependentDto.getName());
				dependent.setPlace(dependentDto.getPlace());
				dependent.setGender(dependentDto.getGender());
				dependent.setAge(dependentDto.getAge());
				dependent.setRelation(dependentDto.getRelation());

				return dependentRepository.save(dependent);
			} else {
				throw new RuntimeException("Dependent not found with id " + dependentDto.getDependentId());
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public DependentDto deleteDependentById(@Valid Long dependentId) {
	    Optional<Dependent> optionalDependent = dependentRepository.findById(dependentId);
	    if (optionalDependent.isPresent()) {
	        Dependent dependent = optionalDependent.get();
	        dependent.setBookingId(null);  // Set bookingId to null

	        dependentRepository.save(dependent);  // Save the updated dependent to persist the null value
	        dependentRepository.flush();
	        dependentRepository.delete(dependent);  // Delete the dependent
	        dependentRepository.flush();
	        return mapToDependentDto(dependent);
	    } else {
	        return null;
	    }
	}


	private DependentDto mapToDependentDto(Dependent dependent) {
		DependentDto dependentDto = new DependentDto();
		dependentDto.setDependentId(dependent.getDependentId());
		dependentDto.setName(dependent.getName());
		dependentDto.setPlace(dependent.getPlace());
		dependentDto.setGender(dependent.getGender());
		dependentDto.setAge(dependent.getAge());
		dependentDto.setRelation(dependent.getRelation());

		return dependentDto;
	}

}
