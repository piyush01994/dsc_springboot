package com.dsc.dsclife.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.dto.Statedto;
import com.dsc.dsclife.entity.StateEntity;
import com.dsc.dsclife.repository.StateRepository;
import com.dsc.dsclife.service.StateService;
import com.dsc.dsclife.validator.ResourceNotFoundException;

@Service
public class StateServiceImpl implements StateService {

	ModelMapper modelMapper = new ModelMapper();
	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);
	@Autowired
	private StateRepository repository;

	@Override
	public List<Statedto> getallstatedata() {
		List<StateEntity> entitydata = this.repository.findAll();
		logger.info("state data list succfully retrive");
		return entitydata.stream().map(stateEntity -> entitytodto(stateEntity)).collect(Collectors.toList());
	}

	@Override
	public Statedto savestatedata(Statedto dtodata) {
		StateEntity saveddata = null;
		Optional<StateEntity> entitydata = this.repository.findBystateCode(dtodata.getStateCode());
		if (entitydata.isEmpty()) {
			StateEntity entydata = this.dtotoentity(dtodata);
			saveddata = this.repository.save(entydata);
			logger.info("state data  succfully stored");

		} else {
			StateEntity entydata = entitydata.get();
			entydata.setStateCode(dtodata.getStateCode());
			entydata.setStateName(dtodata.getStateName());
			entydata.setStatus(dtodata.isStatus());
			saveddata = this.repository.save(entydata);
			logger.info("state data list succfully updated");

		}
		return this.entitytodto(saveddata);
	}

	@Override
	public Statedto getstatedata(Long stateCode) {
		StateEntity entydata = null;
		Optional<StateEntity> entitydata = this.repository.findBystateCode(stateCode);
		if (entitydata.isEmpty()) {
			throw new ResourceNotFoundException("state code not found", null, stateCode);

		} else {
			entydata = entitydata.get();
			logger.info("state data  succfully fetched");
		}
		return this.entitytodto(entydata);
	}

	// convert Dto to entity
	public StateEntity dtotoentity(Statedto Statedto) {
		StateEntity StateEntity = this.modelMapper.map(Statedto, StateEntity.class);
		return StateEntity;
	}

	// convert entity to dto
	public Statedto entitytodto(StateEntity StateEntity) {
		Statedto Statedto = this.modelMapper.map(StateEntity, Statedto.class);
		return Statedto;
	}

	@Override
	public List<Statedto> getallstatedatabystatus() {
		List<StateEntity> entitydata = this.repository.findAllByStatus(true);
		logger.info("state data list succfully retrive");
		return entitydata.stream().map(stateEntity -> entitytodto(stateEntity)).collect(Collectors.toList());
	
	}

}
