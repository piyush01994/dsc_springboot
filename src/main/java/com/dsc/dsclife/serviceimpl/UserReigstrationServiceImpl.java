package com.dsc.dsclife.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.config.JwtHelper;
import com.dsc.dsclife.dto.UserRegistrationDTO;
import com.dsc.dsclife.entity.LoginEntity;
import com.dsc.dsclife.entity.UserReigstrationEntity;
import com.dsc.dsclife.repository.UserLoginRepository;
import com.dsc.dsclife.repository.UserReigstrationRepository;
import com.dsc.dsclife.service.UserReigstrationService;
import com.dsc.dsclife.validator.ResourceNotFoundException;

import io.jsonwebtoken.Claims;

@Service
public class UserReigstrationServiceImpl implements UserReigstrationService{
	
	@Autowired
	private UserReigstrationRepository reigstrationRepository;
	@Autowired
	private UserLoginRepository loginRepository;
	@Autowired
	private JwtHelper helper;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public String savedata(UserRegistrationDTO dtodata) {
		Optional<UserReigstrationEntity> isdataexist = this.reigstrationRepository.findByUserId(dtodata.getUserId());
		if (isdataexist.isEmpty()) {
			String password = passwordEncoder.encode(dtodata.getPassword());
			dtodata.setPassword(password);
			dtodata.setStatus(false);
			dtodata.setRole("ROLE_USER");
			reigstrationRepository.save(this.dtotoentity(dtodata));
		} else {
			throw new ResourceNotFoundException("user id already exist", isdataexist.get().getUserId(), 0);
		}

		return "Success";
	}
	
	

	@Override
	public UserRegistrationDTO approveuser(long id, Boolean status) {
		UserReigstrationEntity entydata = null;
		Optional<UserReigstrationEntity> isdataexist = this.reigstrationRepository.findById(id);
		if (isdataexist.isEmpty()) {
			throw new ResourceNotFoundException("user not exist", "" + id, id);
		} else {
			entydata = isdataexist.get();
			entydata.setStatus(status);
			entydata =reigstrationRepository.save(entydata);
			if (status) {
				LoginEntity logindata = new LoginEntity();
				logindata.setEmailid(entydata.getEmailid());
				logindata.setMobile(entydata.getMobile());
				logindata.setUserId(entydata.getUserId());
				logindata.setDesignation(entydata.getDesignation());
				logindata.setOrgnizationid(entydata.getOrganizationId());
				logindata.setPassword(entydata.getPassword());
				logindata.setPhotoId(entydata.getPhotoId());
				logindata.setRole(entydata.getRole());
				logindata.setStateCode(entydata.getDepartmentRegistrationEntity().getOrganizationEntity().getStateEntity().getStateCode());
				logindata.setStatus(status);
				logindata.setUserName(entydata.getUserName());
				logindata.setAddressss(entydata.getAddressss());
				loginRepository.save(logindata);

			}
		}
		return this.entitytodto(entydata);
	}
	
	
	
	
	// convert Dto to entity
				public UserReigstrationEntity dtotoentity(UserRegistrationDTO UserRegistrationDTO) {
					UserReigstrationEntity UserReigstrationEntity = this.modelMapper.map(UserRegistrationDTO,
							UserReigstrationEntity.class);
				
					return UserReigstrationEntity;
				}
				
				// convert entity to dto
				public UserRegistrationDTO entitytodto(UserReigstrationEntity UserReigstrationEntity) {
					UserRegistrationDTO UserRegistrationDTO = this.modelMapper.map(UserReigstrationEntity,
							UserRegistrationDTO.class);
					if(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getStateEntity().getStateName()!=null) {
						UserRegistrationDTO.setStateName(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getStateEntity().getStateName());
					}
					if(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentName()!=null){
						UserRegistrationDTO.setOrgnizationName(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentName());
					}
					if(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType()!=null){
						UserRegistrationDTO.setDepttype(UserReigstrationEntity.getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType());
					}
					return UserRegistrationDTO;
				}



				@Override
				public List<UserRegistrationDTO> allnonapproveddata(String tokenHeader) {
					final Claims claims2 = helper.getAllClaimsFromToken( tokenHeader.substring(7));
					String userid = claims2.get("userid").toString();	
					Long deptid = this.loginRepository.findOrgnizationidByUserId(userid);
				List<UserReigstrationEntity>entydata = this.reigstrationRepository.findByOrganizationIdAndStatus(deptid,false);
					
				  List<UserRegistrationDTO> noApprovedDataDTOs = entydata.stream()
			                .map(this::entitytodto) // Assuming you have a method named entityToDTO
			                .collect(Collectors.toList());
				return noApprovedDataDTOs;
				}



				@Override
				public List<UserRegistrationDTO> allapproveddata(String tokenHeader) {
					final Claims claims2 = helper.getAllClaimsFromToken( tokenHeader.substring(7));
					String userid = claims2.get("userid").toString();
					Long deptid = this.loginRepository.findOrgnizationidByUserId(userid);
					List<UserReigstrationEntity>entydata = this.reigstrationRepository.findByOrganizationIdAndStatus(deptid,true);
					 List<UserRegistrationDTO> approvedatat = entydata.stream()
				                .map(this::entitytodto) // Assuming you have a method named entityToDTO
				                .collect(Collectors.toList());
					return approvedatat;
				}



				@Override
				public UserRegistrationDTO getdatabyid(long id) {
					UserReigstrationEntity entydata = null;
					Optional<UserReigstrationEntity> isdataexist = this.reigstrationRepository.findById(id);
					if (isdataexist.isEmpty()) {
						throw new ResourceNotFoundException("user not exist", "" + id, id);
					} else {
						entydata = isdataexist.get();
					}
					return this.entitytodto(entydata);
				}



				@Override
				public UserRegistrationDTO getdscdata(String tokenHeader) {
					UserRegistrationDTO dtodata = null;
					final Claims claims2 = helper.getAllClaimsFromToken( tokenHeader.substring(7));
					String userid = claims2.get("userid").toString();
				
					Optional<UserReigstrationEntity> isdataexist = this.reigstrationRepository.findByUserId(userid);
					if (isdataexist.isEmpty()) {
						throw new ResourceNotFoundException("user not exist", "" + userid, 0);
					} else {
						dtodata = this.entitytodto(isdataexist.get());
						dtodata.setOrgnizationName(isdataexist.get().getDepartmentRegistrationEntity().getOrganizationName());
					}
					
					return dtodata;
				}









}
