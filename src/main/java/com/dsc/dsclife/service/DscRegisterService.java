package com.dsc.dsclife.service;

import java.io.IOException;
import java.util.List;

import com.dsc.dsclife.dto.DscRegisterDTO;

public interface DscRegisterService {

	DscRegisterDTO savedata(DscRegisterDTO dtodata,String tokenHeader) throws IOException;

	List<DscRegisterDTO> getdata();

	List<DscRegisterDTO> getapproveddscdata();

	DscRegisterDTO approveddsc(DscRegisterDTO dscRegisterDTO) throws IOException;

	byte[] getfiles(Long id) throws IOException;

	List<DscRegisterDTO> getdscdata(String tokenHeader);

	byte[] getdsczip(Long id);

	

}
