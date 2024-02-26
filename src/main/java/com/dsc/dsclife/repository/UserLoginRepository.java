package com.dsc.dsclife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dsc.dsclife.entity.LoginEntity;

public interface UserLoginRepository extends JpaRepository<LoginEntity,String>{

	LoginEntity findByUserId(String userid);
	@Query(value = "select orgnizationid from tbl_logindetails where user_id=:userid",nativeQuery = true)
	Long findOrgnizationidByUserId(@Param("userid") String userid);

}
