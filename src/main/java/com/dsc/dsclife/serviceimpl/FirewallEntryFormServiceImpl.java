package com.dsc.dsclife.serviceimpl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.config.JwtHelper;
import com.dsc.dsclife.dto.FirewallEntryFormDto;
import com.dsc.dsclife.entity.FirewallEntryFormEntity;
import com.dsc.dsclife.entity.UserReigstrationEntity;
import com.dsc.dsclife.repository.FirewallEntryFormRepository;
import com.dsc.dsclife.repository.UserReigstrationRepository;
import com.dsc.dsclife.service.FirewallService;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import io.jsonwebtoken.Claims;
@Service
public class FirewallEntryFormServiceImpl  implements FirewallService{
	@Autowired
	private FirewallEntryFormRepository entryFormRepository;
	@Autowired
	private UserReigstrationRepository reigstrationRepository;
	@Autowired
	private JwtHelper helper;
	private static final Logger logger = LoggerFactory.getLogger(DscRegisterServiceImpl.class);

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public byte[] savedata(FirewallEntryFormDto dtodata) throws IOException {
		FirewallEntryFormEntity firewallEntryFormEntity = this.entryFormRepository.save(this.dtotoentity(dtodata));
		byte[] filedata = firwallpdfgenration(firewallEntryFormEntity.getId());
		 logger.info("firewall registration form genrated successfully", dtodata.getUserId());
		return filedata;
	}

	

	@Override
	public byte[] getpdf(String tokenHeader) throws IOException {	
		final Claims claims2 = helper.getAllClaimsFromToken( tokenHeader.substring(7));
		String userid = claims2.get("userid").toString();
		byte[] filedata = null;
			filedata = nodalofficerAuthorzationletter(userid);
			 logger.info("ndal officer certificate  form genrated successfully", userid);
			 return filedata;
		}
	
	

	

	@Override
	public byte[] getfirewallpdf(long id) throws IOException {
		byte[] filedata = firwallpdfgenration(id);
		 logger.info("firewall registration form genrated successfully", id);
		return filedata;
	}
	
	
	
	@Override
	public byte[] applicationform(String tokenHeader) throws IOException {	
		final Claims claims2 = helper.getAllClaimsFromToken( tokenHeader.substring(7));
		String userid = claims2.get("userid").toString();
		byte[] filedata = null;
				filedata = applicationformpdf(userid);
			 logger.info("Application form pdf  genrated successfully", userid);
			 return filedata;
		}
	
	
	


		// convert Dto to entity
		public FirewallEntryFormEntity dtotoentity(FirewallEntryFormDto FirewallEntryFormDto) {
			FirewallEntryFormEntity FirewallEntryFormEntity = this.modelMapper.map(FirewallEntryFormDto, FirewallEntryFormEntity.class);
			return FirewallEntryFormEntity;
		}

		// convert entity to dto
		public FirewallEntryFormDto entitytodto(FirewallEntryFormEntity FirewallEntryFormEntity) {
			FirewallEntryFormDto FirewallEntryFormDto = this.modelMapper.map(FirewallEntryFormEntity, FirewallEntryFormDto.class);
			return FirewallEntryFormDto;
		}
		
		
		private byte[] firwallpdfgenration(Long id) throws IOException {

			byte[] bytes = null;
			Date date = new Date();
			Optional<FirewallEntryFormEntity> firewalldata = this.entryFormRepository.findById(id);
			FirewallEntryFormEntity entydata = firewalldata.get();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter write = new PdfWriter(outputStream);
			Rectangle myPageSize = new Rectangle(816, 1056);
			PdfDocument pdoc = new PdfDocument(write);
			Document doc = new Document(pdoc, new PageSize(myPageSize));
			PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			Paragraph heading = new Paragraph(new Text("FIREWALL ENTRY FORM").setFont(bold)).setTextAlignment(TextAlignment.CENTER);
			doc.add(heading);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			float[] columnpmrwidth = { 500f, 500f };
			Table hedaing2 = new Table(columnpmrwidth);
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("Date:-" +date).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("userid:-" +entydata.getUserId()).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			doc.add(hedaing2);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			float[] column5Width= {450f,450f};
			Table tdetails3=new Table(column5Width);
			Text t23=new Text("1.	SERVER NAME ");
			tdetails3.addCell(new Cell().add(new Paragraph(t23.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getServerName()))));
			
			Text t24=new Text("2.	PRIVATE IP");
			tdetails3.addCell(new Cell().add(new Paragraph(t24.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getPrivateIp()))));
			
			Text t25=new Text("3.	PUBLIC IP");
			tdetails3.addCell(new Cell().add(new Paragraph(t25.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getPublicIp()))));
			
			Text t26=new Text("4.	TYPE OF SERVER ");
			tdetails3.addCell(new Cell().add(new Paragraph(t26.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getTypeOfServer()))));
			
			Text t27=new Text("5. ACCESS TO BE ALLOWED FROM ANY NICNET OR SPECIFIC IP RANGE");
			tdetails3.addCell(new Cell().add(new Paragraph(t27.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getAccessToBeAllowedAnyspecificipRange()))));
			
			
			Text t28=new Text("6.	PORT SERVICES TO BE ALLOWED ");
			tdetails3.addCell(new Cell().add(new Paragraph(t28.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getPortServiceToAllowed()))));
			
			
			Text t29=new Text("7.	PURPOSE");
			tdetails3.addCell(new Cell().add(new Paragraph(t29.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getPurpose()))));
			
			
			Text t30=new Text("8.	NAME OF THE APPLICATINT ");
			tdetails3.addCell(new Cell().add(new Paragraph(t30.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getNameOfApplicaint()))));
			
			
			Text t31=new Text("9.	PHONE NUMBER  ");
			tdetails3.addCell(new Cell().add(new Paragraph(t31.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getPhoneNumber()))));
			
			
			Text t32=new Text("9.	EMAIL ");
			tdetails3.addCell(new Cell().add(new Paragraph(t32.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getEmail()))));
			
			
			doc.add(tdetails3);
			
			for(int i=0;i<4;i++)
			{
				doc.add(new Paragraph(" "));
			}
			Paragraph signature  = new Paragraph(new Text("Signautre and stamp of department").setFont(bold)).setTextAlignment(TextAlignment.LEFT);
			doc.add(signature);		
			
			doc.close();
			bytes = outputStream.toByteArray();

			return bytes;
		}




		private byte[] nodalofficerAuthorzationletter(String userid) throws IOException {
			byte[] bytes = null;
			Date date = new Date();
			Optional<UserReigstrationEntity> userdata = this.reigstrationRepository.findByUserId(userid);
			UserReigstrationEntity entydata = userdata.get();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter write = new PdfWriter(outputStream);
			Rectangle myPageSize = new Rectangle(816, 1056);
			PdfDocument pdoc = new PdfDocument(write);
			Document doc = new Document(pdoc, new PageSize(myPageSize));
			PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			Paragraph heading = new Paragraph(new Text("NODAL OFFICER AUTHORIZATION LETTER").setFont(bold)).setTextAlignment(TextAlignment.CENTER);
			doc.add(heading);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			float[] columnpmrwidth = { 500f, 500f };
			Table hedaing2 = new Table(columnpmrwidth);
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("Date:-" +date).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("userid:-" +entydata.getUserId()).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			doc.add(hedaing2);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			
			Table hedaing3 = new Table(columnpmrwidth);
			hedaing3.addCell(new Cell(1,2).add(new Paragraph(new Text("To whom so Ever It May Concern").setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
			doc.add(hedaing3);
			
			for(int i=0;i<4;i++)
			{
				doc.add(new Paragraph(" "));
			}
			Paragraph heading4 = new Paragraph(new Text("i hereby authorize "+entydata.getUserName() +" to apply as Nodal Officer for DSC LIFEE Services on behalf of our organization. I certify the physcial verfication of the applicatiant and confirm that the information submitted by him/her is correct the best of my knowledge")).setTextAlignment(TextAlignment.CENTER);
			doc.add(heading4);
			for(int i=0;i<10;i++)
			{
				doc.add(new Paragraph(" "));
			}
			
			Paragraph signature  = new Paragraph(new Text("Signautre and stamp of Authorizing person").setFont(bold)).setTextAlignment(TextAlignment.RIGHT);
			doc.add(signature);		
			
			doc.close();
			bytes = outputStream.toByteArray();

			return bytes;
		}
		
		
		
		private byte[] applicationformpdf(String userid) throws IOException {

			byte[] bytes = null;
			Date date = new Date();
			Optional<UserReigstrationEntity> userdata = this.reigstrationRepository.findByUserId(userid);
			UserReigstrationEntity entydata = userdata.get();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter write = new PdfWriter(outputStream);
			Rectangle myPageSize = new Rectangle(816, 1056);
			PdfDocument pdoc = new PdfDocument(write);
			Document doc = new Document(pdoc, new PageSize(myPageSize));
			PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			Paragraph heading = new Paragraph(new Text("DSC LIFE APPLICATION FORM").setFont(bold)).setTextAlignment(TextAlignment.CENTER).setFontSize(16);
			Paragraph heading3 = new Paragraph(new Text("(For Goverment Organization)").setFont(bold)).setTextAlignment(TextAlignment.CENTER).setFontSize(12);
			
			doc.add(heading);
			doc.add(heading3);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			float[] columnpmrwidth = { 500f, 500f };
			Table hedaing2 = new Table(columnpmrwidth);
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("Date:-" +date).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("User Name:-" +entydata.getUserId()).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			hedaing2.addCell(new Cell().add(new Paragraph(new Text("Application Number:-" +entydata.getEmployeeId()).setFont(bold))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
			
			doc.add(hedaing2);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			
			Paragraph heading4 = new Paragraph(new Text("(Organization Information)").setFont(bold)).setTextAlignment(TextAlignment.CENTER).setFontSize(12).setUnderline(1f, -2f);;
			doc.add(heading4);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			
			float[] column5Width= {450f,450f};
			Table tdetails3=new Table(column5Width);
			Text t23=new Text("	ORGANISATION NAME ");
			tdetails3.addCell(new Cell().add(new Paragraph(t23.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getDepartmentRegistrationEntity().getOrganizationEntity().getDepartmentName()))));
			
			
			Text t25=new Text("HOD");
			tdetails3.addCell(new Cell().add(new Paragraph(t25.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getDepartmentRegistrationEntity().getHodName()!=null?entydata.getDepartmentRegistrationEntity().getHodName():""))));
			
			Text t26=new Text("DESIGNATION ");
			tdetails3.addCell(new Cell().add(new Paragraph(t26.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getDepartmentRegistrationEntity().getDesignation()!=null?entydata.getDepartmentRegistrationEntity().getDesignation():""))));
			
			Text t27=new Text("PHONE NUMBER");
			tdetails3.addCell(new Cell().add(new Paragraph(t27.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getDepartmentRegistrationEntity().getMobile()))));
			
			
			Text t28=new Text("EMAIL ID ");
			tdetails3.addCell(new Cell().add(new Paragraph(t28.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getDepartmentRegistrationEntity().getEmailId()))));
			
			
			Text t29=new Text("ADDRESS");
			tdetails3.addCell(new Cell().add(new Paragraph(t29.setFont(bold))));
			tdetails3.addCell(new Cell().add(new Paragraph(new Text(entydata.getDepartmentRegistrationEntity().getAddress()))));
		
						
			doc.add(tdetails3);
			
			for(int i=0;i<4;i++)
			{
				doc.add(new Paragraph(" "));
			}
			Paragraph heading5 = new Paragraph(new Text("(Applicant Information)").setFont(bold)).setTextAlignment(TextAlignment.CENTER).setFontSize(12).setUnderline(1f, -2f);;
			doc.add(heading5);
			for (int i = 0; i < 4; i++) {
				doc.add(new Paragraph(" "));
			}
			
			float[] column5Width1= {450f,450f};
			Table tdetails33=new Table(column5Width1);
			Text t1=new Text(" NAME ");
			tdetails33.addCell(new Cell().add(new Paragraph(t1.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(entydata.getUserName()))));
			
			Text t2=new Text("DESIGNATION");
			tdetails33.addCell(new Cell().add(new Paragraph(t2.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getDesignation()))));
			
			Text t3=new Text("EMPLOYEE ID");
			tdetails33.addCell(new Cell().add(new Paragraph(t3.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getEmployeeId()))));
			
			Text t4=new Text("DOB ");
			tdetails33.addCell(new Cell().add(new Paragraph(t4.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getDateOfBirth()))));
			
			Text t5=new Text("PHONE NUMBER");
			tdetails33.addCell(new Cell().add(new Paragraph(t5.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getMobile()))));
			
			
			Text t6=new Text("EMAIL ID ");
			tdetails33.addCell(new Cell().add(new Paragraph(t6.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getEmailid()))));
			
			
			Text t8=new Text("STATE");
			tdetails33.addCell(new Cell().add(new Paragraph(t8.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(""+entydata.getStateCode()))));
		
			
			Text t7=new Text("ADDRESS");
			tdetails33.addCell(new Cell().add(new Paragraph(t7.setFont(bold))));
			tdetails33.addCell(new Cell().add(new Paragraph(new Text(entydata.getAddressss()!=null?entydata.getAddressss():""))));
		
						
			doc.add(tdetails33);
			
			for(int i=0;i<4;i++)
			{
				doc.add(new Paragraph(" "));
			}
			
			
			Paragraph signature  = new Paragraph(new Text("Signautre and stamp of department").setFont(bold)).setTextAlignment(TextAlignment.LEFT);
			doc.add(signature);		
			
			doc.close();
			bytes = outputStream.toByteArray();

			return bytes;
		}

		
}
