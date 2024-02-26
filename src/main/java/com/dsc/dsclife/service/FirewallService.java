package com.dsc.dsclife.service;

import java.io.IOException;

import com.dsc.dsclife.dto.DscRegisterDTO;
import com.dsc.dsclife.dto.FirewallEntryFormDto;

public interface FirewallService {

	byte[] savedata(FirewallEntryFormDto dtodata) throws IOException;

	byte[] getpdf(String tokenHeader) throws IOException;

	byte[] getfirewallpdf(long id) throws IOException;

	byte[] applicationform(String tokenHeader) throws IOException;

}
