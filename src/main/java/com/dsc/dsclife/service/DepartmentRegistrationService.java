package com.dsc.dsclife.service;

import java.util.List;

import com.dsc.dsclife.dto.DepartmentRegistrationDTO;
import com.dsc.dsclife.dto.DepartmentddlDTO;

public interface DepartmentRegistrationService {

	String savedata(DepartmentRegistrationDTO dtodata);

	DepartmentRegistrationDTO approveddata(long id, Boolean status, String reason);

	List<DepartmentRegistrationDTO> allnonapproveddata();

	List<DepartmentRegistrationDTO> allapproveddata();

	DepartmentRegistrationDTO getdatabyid(long id);

	List<DepartmentddlDTO> allapproveddatadepartment();


}
