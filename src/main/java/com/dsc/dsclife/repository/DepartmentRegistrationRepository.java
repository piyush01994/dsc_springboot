package com.dsc.dsclife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.dsclife.entity.DepartmentRegistrationEntity;

public interface DepartmentRegistrationRepository extends JpaRepository<DepartmentRegistrationEntity, Long>{

	Optional<DepartmentRegistrationEntity> findByUserId(String userId);

	List<DepartmentRegistrationEntity> findByStatus(Boolean status);

	List<DepartmentRegistrationEntity> findByStatusAndRejectedReasonNull(boolean b);

	List<DepartmentRegistrationEntity> findByStatusOrRejectedReasonNotNull(boolean b);


}
