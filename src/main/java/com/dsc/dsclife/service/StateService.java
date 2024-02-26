package com.dsc.dsclife.service;

import java.util.List;

import com.dsc.dsclife.dto.Statedto;

public interface StateService {

	List<Statedto> getallstatedata();

	Statedto savestatedata(Statedto dtodata);

	Statedto getstatedata(Long stateCode);

	List<Statedto> getallstatedatabystatus();

}
