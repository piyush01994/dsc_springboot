package com.dsc.dsclife.service;

import java.util.List;

import com.dsc.dsclife.dto.DepartmentTypeDto;

public interface DepartmentTypeService {

	List<DepartmentTypeDto> getalldepttype();

	DepartmentTypeDto savedeptype(DepartmentTypeDto dtodata);

	DepartmentTypeDto getalldepttypebyid(Long id);

}
