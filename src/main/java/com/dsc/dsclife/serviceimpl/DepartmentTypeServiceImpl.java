package com.dsc.dsclife.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.dto.DepartmentTypeDto;
import com.dsc.dsclife.dto.Statedto;
import com.dsc.dsclife.entity.DepartmentTypeEntity;
import com.dsc.dsclife.entity.StateEntity;
import com.dsc.dsclife.repository.DepartmentTypeRepository;
import com.dsc.dsclife.service.DepartmentTypeService;

@Service
public class DepartmentTypeServiceImpl implements DepartmentTypeService {
	
	ModelMapper modelMapper = new ModelMapper();
	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);
	
	
	@Autowired
	private DepartmentTypeRepository departmentTypeRepository;

	@Override
	public List<DepartmentTypeDto> getalldepttype() {
		List<DepartmentTypeEntity> entitydata = this.departmentTypeRepository.findAll();
		logger.info("depttype data list succfully retrive");
		return entitydata.stream().map(entitydata1 -> entitytodto(entitydata1)).collect(Collectors.toList());
	
	}

	@Override
	public DepartmentTypeDto savedeptype(DepartmentTypeDto dtodata) {
		DepartmentTypeEntity saveddata = null;
		if(dtodata.getId()==null)
		  dtodata.setId((long) 0);	
		Optional<DepartmentTypeEntity> entitydata = this.departmentTypeRepository.findById(dtodata.getId());
		if (entitydata.isEmpty()) {
			DepartmentTypeEntity entydata = this.dtotoentity(dtodata);
			saveddata = this.departmentTypeRepository.save(entydata);
			logger.info("dept type data  succfully stored");

		} else {
			DepartmentTypeEntity entydata = entitydata.get();
			entydata.setDepartmentType(dtodata.getDepartmentType());
			entydata.setStatus(dtodata.isStatus());
			saveddata = this.departmentTypeRepository.save(entydata);
			logger.info("dept type data list succfully updated");

		}
		return this.entitytodto(saveddata);
	}

	@Override
	public DepartmentTypeDto getalldepttypebyid(Long id) {
		DepartmentTypeEntity entydata = this.departmentTypeRepository.findById(id).get();	
		return this.entitytodto(entydata);
	}
	

	
	// convert Dto to entity
		public DepartmentTypeEntity dtotoentity(DepartmentTypeDto DepartmentTypeDto) {
			DepartmentTypeEntity DepartmentTypeEntity = this.modelMapper.map(DepartmentTypeDto, DepartmentTypeEntity.class);
			return DepartmentTypeEntity;
		}

		// convert entity to dto
		public DepartmentTypeDto entitytodto(DepartmentTypeEntity DepartmentTypeEntity) {
			DepartmentTypeDto DepartmentTypeDto = this.modelMapper.map(DepartmentTypeEntity, DepartmentTypeDto.class);
			return DepartmentTypeDto;
		}
}
