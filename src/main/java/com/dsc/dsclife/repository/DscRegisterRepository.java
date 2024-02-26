package com.dsc.dsclife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.dsclife.entity.DscRegisterEntity;

public interface DscRegisterRepository  extends JpaRepository<DscRegisterEntity, Long>{

	List<DscRegisterEntity> findAllByStatus(Boolean status);

	List<DscRegisterEntity> findAllByStatusAndRejectReasonIsNull(boolean b);

	List<DscRegisterEntity> findAllByUserId(String userid);

	Optional<DscRegisterEntity> findByUserId(String userid);

}
