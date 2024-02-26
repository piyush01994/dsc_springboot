package com.dsc.dsclife.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.dto.DepartmentRegistrationDTO;
import com.dsc.dsclife.dto.DepartmentddlDTO;
import com.dsc.dsclife.entity.DepartmentRegistrationEntity;
import com.dsc.dsclife.entity.LoginEntity;
import com.dsc.dsclife.repository.DepartmentRegistrationRepository;
import com.dsc.dsclife.repository.UserLoginRepository;
import com.dsc.dsclife.service.DepartmentRegistrationService;
import com.dsc.dsclife.validator.ResourceNotFoundException;

@Service
public class DepartmentRegistrationServiceImpl  implements DepartmentRegistrationService{
	
	@Autowired
	private DepartmentRegistrationRepository departmentRegistrationRepository;
	@Autowired
	private UserLoginRepository loginRepository;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public String savedata(DepartmentRegistrationDTO dtodata) {
		DepartmentRegistrationEntity entydata = null;
		if(dtodata.getStateCode()==null)
			dtodata.setStateCode(0);
		Optional<DepartmentRegistrationEntity> isdataexist = this.departmentRegistrationRepository.findByUserId(dtodata.getUserId());
		if (isdataexist.isEmpty()) {
			String password = passwordEncoder.encode(dtodata.getPassword());
			dtodata.setPassword(password);
			dtodata.setStatus(false);
			dtodata.setRole("ROLE_DEPT");
			DepartmentRegistrationEntity entydata1 =this.dtotoentity(dtodata);
			entydata = departmentRegistrationRepository.save(entydata1);
			 logger.info("New department  registration data saved with USER ID: {}", dtodata.getUserId());
		} else {
			throw new ResourceNotFoundException("user id already exist", isdataexist.get().getUserId(), 0);
			
		}

		return "success";
	}
	
	

	@Override
	public DepartmentRegistrationDTO approveddata(long id, Boolean status,String reason) {
		
		DepartmentRegistrationEntity entydata = null;
		Optional<DepartmentRegistrationEntity> isdataexist = this.departmentRegistrationRepository.findById(id);
		if (isdataexist.isEmpty()) {
			throw new ResourceNotFoundException("user not exist", "" + id, id);
		} else {
			entydata = isdataexist.get();
			entydata.setStatus(status);
			entydata.setRejectedReason(reason);
			entydata =departmentRegistrationRepository.save(entydata);
			if (status) {
				LoginEntity logindata = new LoginEntity();
				logindata.setEmailid(entydata.getEmailId());
				logindata.setMobile(entydata.getMobile());
				logindata.setUserId(entydata.getUserId());
				logindata.setDesignation(entydata.getDesignation());
				logindata.setOrgnizationid(entydata.getOrganizationId());
				logindata.setPassword(entydata.getPassword());
				logindata.setRole(entydata.getRole());
				logindata.setStateCode(entydata.getOrganizationEntity().getStateEntity().getStateCode());
				logindata.setStatus(status);
				logindata.setUserName(entydata.getHodName());
				loginRepository.save(logindata);
				 logger.info("New department  registration approved with  USER ID: {}",""+ id);

			}
			
		}
		return this.entitytodto(entydata);
	}

	
	@Override
	public List<DepartmentRegistrationDTO> allnonapproveddata() {
	List<DepartmentRegistrationEntity>noapproveddata = this.departmentRegistrationRepository.findByStatusAndRejectedReasonNull(false);
	List<DepartmentRegistrationDTO> noApprovedDataDTOs = noapproveddata.stream().map(this::entitytodto).collect(Collectors.toList());
	 logger.info("all non approved data stream in list ");
	return noApprovedDataDTOs;
	}
	
	@Override
	public List<DepartmentRegistrationDTO> allapproveddata() {
		List<DepartmentRegistrationEntity>noapproveddata = this.departmentRegistrationRepository.findByStatusOrRejectedReasonNotNull(true);
		List<DepartmentRegistrationDTO> ApprovedDataDTOs = noapproveddata.stream().map(this::entitytodto).collect(Collectors.toList());
		 logger.info("all  approved data stream in list ");
		return ApprovedDataDTOs;
	}
	@Override
	public DepartmentRegistrationDTO getdatabyid(long id) {
		DepartmentRegistrationEntity entydata = null;
		Optional<DepartmentRegistrationEntity> isdataexist = this.departmentRegistrationRepository.findById(id);
		if (isdataexist.isEmpty()) {
			throw new ResourceNotFoundException("user not exist", "" + id, id);
		} else {
			entydata = isdataexist.get();
		}
		return this.entitytodto(entydata);
	}

	
	// convert Dto to entity
			public DepartmentRegistrationEntity dtotoentity(DepartmentRegistrationDTO DepartmentRegistrationDTO) {
				DepartmentRegistrationEntity DepartmentRegistrationEntity = this.modelMapper.map(DepartmentRegistrationDTO,
						DepartmentRegistrationEntity.class);
				return DepartmentRegistrationEntity;
			}
			
			// convert entity to dto
			public DepartmentRegistrationDTO entitytodto(DepartmentRegistrationEntity DepartmentRegistrationEntity) {
				DepartmentRegistrationDTO DepartmentRegistrationDTO = this.modelMapper.map(DepartmentRegistrationEntity,
						DepartmentRegistrationDTO.class);
				if(DepartmentRegistrationEntity.getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType()!=null) {
					DepartmentRegistrationDTO.setDeptType(DepartmentRegistrationEntity.getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType());
				}
				if(DepartmentRegistrationEntity.getOrganizationEntity().getStateEntity().getStateName()!=null) {
					DepartmentRegistrationDTO.setStateName(DepartmentRegistrationEntity.getOrganizationEntity().getStateEntity().getStateName());
				}
				if(DepartmentRegistrationEntity.getOrganizationEntity().getDepartmentName()!=null) {
					DepartmentRegistrationDTO.setOrganizationName(DepartmentRegistrationEntity.getOrganizationEntity().getDepartmentName());
				}
				return DepartmentRegistrationDTO;
			}



			@Override
			public List<DepartmentddlDTO> allapproveddatadepartment() {
				List<DepartmentddlDTO> activedata =new ArrayList<DepartmentddlDTO>();
				List<DepartmentRegistrationEntity> activeorg = this.departmentRegistrationRepository.findByStatus(true);
				DepartmentddlDTO dtodata = new DepartmentddlDTO();
				for(DepartmentRegistrationEntity entydata :activeorg) {				
					dtodata.setDeartmentid(entydata.getOrganizationId());
					dtodata.setDepartmentName(entydata.getOrganizationEntity().getDepartmentName());
					activedata.add(dtodata);
				}
				 logger.info("all  approved data stream in list ");
				
				return activedata;
			}



		


			



			







			
}
