package com.dsc.dsclife.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.config.JwtHelper;
import com.dsc.dsclife.dto.DscRegisterDTO;
import com.dsc.dsclife.dto.UserRegistrationDTO;
import com.dsc.dsclife.entity.DscRegisterEntity;
import com.dsc.dsclife.entity.FirewallEntryFormEntity;
import com.dsc.dsclife.repository.DscRegisterRepository;
import com.dsc.dsclife.service.DscRegisterService;
import com.dsc.dsclife.validator.ResourceNotFoundException;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;

import io.jsonwebtoken.Claims;

@Service
public class DscRegisterServiceImpl implements DscRegisterService {
	@Autowired
	private DscRegisterRepository dscRegisterRepository;

	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);
	@Autowired
	private JwtHelper helper;
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public DscRegisterDTO savedata(DscRegisterDTO dtodata, String tokenHeader) throws IOException {
		DscRegisterEntity entydata = null;
		final Claims claims2 = helper.getAllClaimsFromToken(tokenHeader.substring(7));
		String userid = claims2.get("userid").toString();
		Optional<DscRegisterEntity> isdataexist = this.dscRegisterRepository.findById(dtodata.getDscId());
		if (isdataexist.isEmpty()) {
			entydata = this.dtotoentity(dtodata);
			entydata.setAuthoriationLetter(dtodata.getAuthoriationLetter().getBytes());
			entydata.setFirewallEntryform(dtodata.getFirewallEntryform().getBytes());
			entydata.setIdCard(dtodata.getIdCard().getBytes());
			entydata.setLogoForDsc(dtodata.getLogoForDsc().getBytes());
			entydata.setLogoForStamp(dtodata.getLogoForStamp().getBytes());
			entydata.setStagingOrliveDceCertificate(dtodata.getStagingOrliveDceCertificate().getBytes());
			entydata.setStatus(false);
			entydata.setUserId(userid);
			entydata = this.dscRegisterRepository.save(entydata);
			logger.info("New DSC registration data saved with DSC ID: {}", dtodata.getDscId());

		} else {
			entydata = isdataexist.get();
			entydata.setAuthoriationLetter(dtodata.getAuthoriationLetter().getBytes());
			entydata.setFirewallEntryform(dtodata.getFirewallEntryform().getBytes());
			entydata.setIdCard(dtodata.getIdCard().getBytes());
			entydata.setLogoForDsc(dtodata.getLogoForDsc().getBytes());
			entydata.setLogoForStamp(dtodata.getLogoForStamp().getBytes());
			entydata.setStagingOrliveDceCertificate(dtodata.getStagingOrliveDceCertificate().getBytes());
			entydata.setStatus(false);
			entydata.setUserId(userid);
			entydata.setRejectReason(null);
			entydata = this.dscRegisterRepository.save(this.dtotoentity(dtodata));
			logger.info("Existing DSC registration data updated for DSC ID: {}", dtodata.getDscId());

		}

		return this.entitytodto(entydata);
	}

	@Override
	public List<DscRegisterDTO> getdata() {
		List<DscRegisterEntity> entitydata = this.dscRegisterRepository.findAllByStatusAndRejectReasonIsNull(false);
		List<DscRegisterDTO> noApprovedDataDTOs = entitydata.stream().map(this::entitytodto)
				.collect(Collectors.toList());
		return noApprovedDataDTOs;
	}

	@Override
	public List<DscRegisterDTO> getapproveddscdata() {
		List<DscRegisterEntity> entitydata = this.dscRegisterRepository.findAllByStatus(true);
		List<DscRegisterDTO> getapproveddscdata = entitydata.stream().map(this::entitytodto)
				.collect(Collectors.toList());
		return getapproveddscdata;
	}

	@Override
	public DscRegisterDTO approveddsc(DscRegisterDTO dtodata) throws IOException {
		DscRegisterEntity entydata = null;
		Optional<DscRegisterEntity> isdataexist = this.dscRegisterRepository.findById(dtodata.getDscId());
		if (isdataexist.isEmpty()) {
			throw new ResourceNotFoundException("dsc detail not found", "" + dtodata.getDscId(), 0);

		} else {
			if (dtodata.getStatus()) {
				entydata = isdataexist.get();
				entydata.setDscJar(dtodata.getDscJar().getBytes());
				entydata.setPassword(dtodata.getPassword());
				entydata.setSerialNumber(dtodata.getSerialNumber());
				// entydata.setPrivateKey(dtodata.getPrivateKey().getBytes());
				// entydata.setPublicKey(dtodata.getPublicKey().getBytes());
				entydata.setStatus(dtodata.getStatus());
				entydata.setFileName(dtodata.getFileName());
				entydata.setValidity(dtodata.getValidity());
				entydata = this.dscRegisterRepository.save(entydata);
				logger.info("Existing DSC registration data updated for DSC ID: {}", dtodata.getDscId());
			} else {
				entydata = isdataexist.get();
				entydata.setStatus(dtodata.getStatus());
				entydata.setRejectReason(dtodata.getRejectReason());
				entydata = this.dscRegisterRepository.save(this.dtotoentity(dtodata));
				logger.info("Existing DSC registration data updated for DSC ID: {}", dtodata.getDscId());
			}
		}

		return this.entitytodto(entydata);
	}

	@Override
	public List<DscRegisterDTO> getdscdata(String tokenHeader) {
		final Claims claims2 = helper.getAllClaimsFromToken(tokenHeader.substring(7));
		String userid = claims2.get("userid").toString();
		List<DscRegisterEntity> dscdata = this.dscRegisterRepository.findAllByUserId(userid);
		List<DscRegisterDTO> approvedatat = dscdata.stream().map(this::entitytodto1).collect(Collectors.toList());
		return approvedatat;
	}

	@Override
	public byte[] getdsczip(Long id) {
		byte[] zipdata = null;
		Optional<DscRegisterEntity> dscdata = this.dscRegisterRepository.findById(id);
		if (dscdata.isPresent()) {
			zipdata = dscdata.get().getDscJar();
		} else {
			throw new ResourceNotFoundException("dsc not exist", "data not found", id);
		}
		return zipdata;
	}

	// convert Dto to entity
	public DscRegisterEntity dtotoentity(DscRegisterDTO DscRegisterDTO) {
		DscRegisterEntity DscRegisterEntity = this.modelMapper.map(DscRegisterDTO, DscRegisterEntity.class);
		return DscRegisterEntity;
	}

	// convert entity to dto
	public DscRegisterDTO entitytodto(DscRegisterEntity DscRegisterEntity) {
		DscRegisterDTO DscRegisterDTO = this.modelMapper.map(DscRegisterEntity, DscRegisterDTO.class);
		DscRegisterDTO.setDeptname(DscRegisterEntity.getLoginEntity().getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentName());
		DscRegisterDTO.setDeptType(DscRegisterEntity.getLoginEntity().getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType());
		DscRegisterDTO.setEmailid(DscRegisterEntity.getLoginEntity().getEmailid());
		DscRegisterDTO.setPhonenumber(DscRegisterEntity.getLoginEntity().getMobile());
		DscRegisterDTO.setUserName(DscRegisterEntity.getLoginEntity().getUserName());
		
		return DscRegisterDTO;
	}
	// convert entity to dto
		public DscRegisterDTO entitytodto1(DscRegisterEntity DscRegisterEntity) {
			DscRegisterDTO DscRegisterDTO = this.modelMapper.map(DscRegisterEntity, DscRegisterDTO.class);
			DscRegisterDTO.setDeptname(DscRegisterEntity.getLoginEntity().getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentName());
			DscRegisterDTO.setDeptType(DscRegisterEntity.getLoginEntity().getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentTypeEntity().getDepartmentType());
			DscRegisterDTO.setEmailid(DscRegisterEntity.getLoginEntity().getEmailid());
			DscRegisterDTO.setPhonenumber(DscRegisterEntity.getLoginEntity().getMobile());
			DscRegisterDTO.setUserName(DscRegisterEntity.getLoginEntity().getUserName());
			DscRegisterDTO.setPassword(null);
			DscRegisterDTO.setSerialNumber(null);
			
			return DscRegisterDTO;
		}

	@Override
	public byte[] getfiles(Long id) throws IOException {
		byte[] file = allpdffile(id);
		return file;
	}

	private byte[] allpdffile(Long id) throws IOException {

		byte[] bytes;
		Date date = new Date();
		Optional<DscRegisterEntity> entityData1 = this.dscRegisterRepository.findById(id);
		DscRegisterEntity entityData = entityData1.get();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = new PdfWriter(outputStream);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDocument);

		// Set font size, color, and alignment
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		float fontSize = 22;
		Color blueColor = Color.BLUE;
		TextAlignment alignment = TextAlignment.CENTER;

		// Create a Paragraph with formatted text
		Paragraph paragraph = new Paragraph()
		    .add(new Text("Document of user for DSC Approval")
		        .setFont(font)
		        .setFontSize(fontSize)
		        .setFontColor(blueColor))
		    .setTextAlignment(alignment);

		// Add the paragraph to the document
		Paragraph paragraph1 = new Paragraph()
			    .add(new Text("userid:-" + entityData.getUserId())
			        .setFont(font)
			        .setFontSize(fontSize)
			        .setFontColor(blueColor))
			    .setTextAlignment(alignment);

			// Add the paragraph to the document
		document.add(paragraph);

		for (int i = 0; i < 4; i++) {
			document.add(new Paragraph(" "));
		}

		float[] columnPmrWidth = { 500f, 500f };
		Table heading2 = new Table(columnPmrWidth);
		heading2.addCell(new Cell().add(new Paragraph(
				new Text("Date:-" + date).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD)))
				.setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)));
		heading2.addCell(new Cell().add(new Paragraph(new Text("userid:-" + entityData.getUserId())
				.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD))).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT)));
		document.add(heading2);

		for (int i = 0; i < 4; i++) {
			document.add(new Paragraph(" "));
		}

		// application form----
		if(entityData.getApplicationForm()!=null) {
		PdfReader existingPdfReader = new PdfReader(new ByteArrayInputStream(entityData.getApplicationForm()));
		PdfDocument existingPdfDocument = new PdfDocument(existingPdfReader);
		for (int i = 0; i <= existingPdfDocument.getNumberOfPages(); i++) {
			pdfDocument.addPage(existingPdfDocument.getPage(i).copyTo(pdfDocument));
		}
		existingPdfDocument.close();
		}
		// AUTHORZIATION LETTER form----
		if(entityData.getAuthoriationLetter()!=null) {
		PdfReader existingPdfReader1 = new PdfReader(new ByteArrayInputStream(entityData.getAuthoriationLetter()));
		PdfDocument existingPdfDocument1 = new PdfDocument(existingPdfReader1);
		for (int i = 1; i <= existingPdfDocument1.getNumberOfPages(); i++) {
			pdfDocument.addPage(existingPdfDocument1.getPage(i).copyTo(pdfDocument));
		}
		existingPdfDocument1.close();
		}
		// id card form----
		if(entityData.getIdCard()!=null) {
		PdfReader existingPdfReader2 = new PdfReader(new ByteArrayInputStream(entityData.getIdCard()));
		PdfDocument existingPdfDocument2 = new PdfDocument(existingPdfReader2);
		for (int i = 2; i <= existingPdfDocument2.getNumberOfPages(); i++) {
			pdfDocument.addPage(existingPdfDocument2.getPage(i).copyTo(pdfDocument));
		}
		existingPdfDocument2.close();
		}
		// firwall entry form form----
		if(entityData.getFirewallEntryform()!=null) {
		PdfReader existingPdfReader3 = new PdfReader(new ByteArrayInputStream(entityData.getFirewallEntryform()));
		PdfDocument existingPdfDocument3 = new PdfDocument(existingPdfReader3);
		for (int i = 3; i <= existingPdfDocument3.getNumberOfPages(); i++) {
			pdfDocument.addPage(existingPdfDocument3.getPage(i).copyTo(pdfDocument));
		}
		existingPdfDocument3.close();

		}
		// statging certificate entry form form----
		if(entityData.getStagingOrliveDceCertificate()!=null) {
		PdfReader existingPdfReader4 = new PdfReader(
				new ByteArrayInputStream(entityData.getStagingOrliveDceCertificate()));
		PdfDocument existingPdfDocument4 = new PdfDocument(existingPdfReader4);
		for (int i = 4; i <= existingPdfDocument4.getNumberOfPages(); i++) {
			pdfDocument.addPage(existingPdfDocument4.getPage(i).copyTo(pdfDocument));
		}
		existingPdfDocument4.close();
		}

		// Close the main document
		document.close();

		bytes = outputStream.toByteArray();

		return bytes;
	}

}
