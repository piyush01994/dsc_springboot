package com.dsc.dsclife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.dsclife.entity.DepartmentRegistrationEntity;
import com.dsc.dsclife.entity.UserReigstrationEntity;

public interface UserReigstrationRepository extends JpaRepository<UserReigstrationEntity, Long>{

	

	Optional<UserReigstrationEntity> findByUserId(String userId);

	List<UserReigstrationEntity> findByOrganizationIdAndStatus(Long deptid, boolean status);

}
