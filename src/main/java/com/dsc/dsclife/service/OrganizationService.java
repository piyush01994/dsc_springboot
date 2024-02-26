package com.dsc.dsclife.service;

import java.util.List;

import com.dsc.dsclife.dto.OrganizationDto;

public interface OrganizationService {

	List<OrganizationDto> getallorgnatization();

	String saveorgnizationdata(OrganizationDto dtodata);

	OrganizationDto getorgnizationdatabycode(Long orgcode);

	List<OrganizationDto> getallorgnatizationbyscodeanddepttype(long stateCode, long deptType);

}
