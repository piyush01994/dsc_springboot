package com.dsc.dsclife.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.dto.OrganizationDto;
import com.dsc.dsclife.entity.OrganizationEntity;
import com.dsc.dsclife.repository.OrganizationRepository;
import com.dsc.dsclife.service.OrganizationService;
import com.dsc.dsclife.validator.ResourceNotFoundException;

@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;

	ModelMapper modelMapper = new ModelMapper();
	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);

	@Override
	public List<OrganizationDto> getallorgnatization() {
		List<OrganizationEntity> entitydata = this.organizationRepository.findAll();
		logger.info("org data list succfully retrive");
		return entitydata.stream().map(OrganizationEntity -> entitytodto(OrganizationEntity))
				.collect(Collectors.toList());
	}

	@Override
	public String saveorgnizationdata(OrganizationDto dtodata) {
		OrganizationEntity saveddata = null;
		Optional<OrganizationEntity> entitydata = this.organizationRepository
				.findByDepartmentid(dtodata.getDepartmentid());
		if (entitydata.isEmpty()) {
			OrganizationEntity entydata = this.dtotoentity(dtodata);
			saveddata = this.organizationRepository.save(entydata);
			logger.info("org data  succfully stored");

		} else {
			OrganizationEntity entydata = entitydata.get();
			entydata.setStateCode(dtodata.getStateCode());
			entydata.setDepartmentid(dtodata.getDepartmentid());
			entydata.setDepartmentName(dtodata.getDepartmentName());
			entydata.setDeptType(dtodata.getDeptType());
			saveddata = this.organizationRepository.save(entydata);
			logger.info("org data list succfully updated");

		}
		return "Success";
	}

	@Override
	public OrganizationDto getorgnizationdatabycode(Long orgcode) {
		OrganizationEntity saveddata = null;
		Optional<OrganizationEntity> entitydata = this.organizationRepository.findByDepartmentid(orgcode);
		if (entitydata.isEmpty()) {
			throw new ResourceNotFoundException("ord code not found", null, orgcode);

		} else {
			saveddata = entitydata.get();
			logger.info("org data  succfully feteched");

		}
		return this.entitytodto(saveddata);
	}
	
	
	@Override
	public List<OrganizationDto> getallorgnatizationbyscodeanddepttype(long stateCode, long deptType) {
		List<OrganizationEntity> entitydata=null;
		if(stateCode ==0) {
		 entitydata = this.organizationRepository.findAllByDeptType(deptType);
		}
		else {
			 entitydata = this.organizationRepository.findAllByDeptTypeAndStateCode(deptType,stateCode);
		}
		logger.info("org data list succfully retrive");
		return entitydata.stream().map(OrganizationEntity -> entitytodto(OrganizationEntity))
				.collect(Collectors.toList());
	}

	// convert Dto to entity
	public OrganizationEntity dtotoentity(OrganizationDto OrganizationDto) {
		OrganizationEntity OrganizationEntity = this.modelMapper.map(OrganizationDto, OrganizationEntity.class);
		return OrganizationEntity;
	}

	// convert entity to dto
	public OrganizationDto entitytodto(OrganizationEntity OrganizationEntity) {
		OrganizationDto OrganizationDto = this.modelMapper.map(OrganizationEntity, OrganizationDto.class);
		 // Map related entities manually
	    if (OrganizationEntity.getDepartmentTypeEntity().getDepartmentType() != null) {
	    	OrganizationDto.setDeptypename(OrganizationEntity.getDepartmentTypeEntity().getDepartmentType());
	    }
	    if(OrganizationEntity.getStateEntity().getStateName()!=null) {
	    	OrganizationDto.setStateName(OrganizationEntity.getStateEntity().getStateName());
	    }
		return OrganizationDto;
	}

	

}
