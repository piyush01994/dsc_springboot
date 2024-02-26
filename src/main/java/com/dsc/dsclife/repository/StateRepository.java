package com.dsc.dsclife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.dsclife.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Long>{

	Optional<StateEntity> findBystateCode(Long stateCode);

	List<StateEntity> findAllByStatus(boolean b);

}
