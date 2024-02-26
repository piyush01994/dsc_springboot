package com.dsc.dsclife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.dsclife.entity.OrganizationEntity;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

	Optional<OrganizationEntity> findByDepartmentid(long departmentid);

	List<OrganizationEntity> findAllByDeptType(long deptType);

	List<OrganizationEntity> findAllByDeptTypeAndStateCode(long deptType, long stateCode);

}
