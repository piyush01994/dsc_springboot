package com.dsc.dsclife.service;

import java.util.List;

import com.dsc.dsclife.dto.UserRegistrationDTO;

public interface UserReigstrationService {

	String savedata(UserRegistrationDTO dtodata);

	UserRegistrationDTO approveuser(long id, Boolean status);

	List<UserRegistrationDTO> allnonapproveddata(String tokenHeader);

	List<UserRegistrationDTO> allapproveddata(String tokenHeader);

	UserRegistrationDTO getdatabyid(long id);

	UserRegistrationDTO getdscdata(String tokenHeader);

}
