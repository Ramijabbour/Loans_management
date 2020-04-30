package com.example.CloseLoans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

public class  CloseLoansReportDocs implements CreateCloseDocTemplate {
	
	public CloseLoansReportDocs() {
	}
	
	  @Override
	  public String CreateCloseLoanDoc(Loans Loan , Vouchers voucher , List<Vouchers> allvouchers) {


	        String Path= System.getProperty("user.dir")+"\\";
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        Date date = new Date();
	        String Filename="close_Loan_"+Loan.getLoanID()+dateFormat.format(date)  ;
	        String returnPath = Path+Filename+".docx";
	        //Blank Document
	        XWPFDocument document = new XWPFDocument();
	        //Write the Document in file system
	        FileOutputStream out = null;
	        try {
	          	File file = new File(Path+Filename+".docx");
	        	file.createNewFile();
	            out = new FileOutputStream(new File(Path+Filename+".docx"));
	        } catch (Exception e ) {
	            e.printStackTrace();
	        }

	        //create paragraph
	        XWPFParagraph paragraph = document.createParagraph();
	        CTP ctp = paragraph.getCTP();
	        CTPPr ctppr = ctp.getPPr();
	        if (ctppr == null) ctppr = ctp.addNewPPr();
	        ctppr.addNewBidi().setVal(STOnOff.ON);
	        //Tiltle
	        XWPFRun paragraphtitle = paragraph.createRun();
	        paragraphtitle.setBold(true);

	        // paragraphOneRunOne.setItalic(true);
	        paragraphtitle.setText("الجمهورية العربية السورية");
	        paragraphtitle.addBreak();
	        paragraphtitle.setText("مصرف سورية المركزي");
	        paragraphtitle.setFontFamily("Arabic Typesetting");
	        paragraphtitle.setFontSize(26);
	        paragraphtitle.setColor("000000");
	        paragraphtitle.addBreak();
	        paragraphtitle.setText("قسم  التسليف");
	        paragraphtitle.addBreak();
	        
	        
	        XWPFParagraph paragraph1 = document.createParagraph();
	        CTP ctp1 = paragraph1.getCTP();
	        CTPPr ctppr1 = ctp1.getPPr();
	        if (ctppr1 == null) ctppr1 = ctp1.addNewPPr();
	        ctppr1.addNewBidi().setVal(STOnOff.ON);
	        paragraph1.setAlignment(ParagraphAlignment.CENTER);
	        
	        //Tiltle
	        XWPFRun paragraphtitle1 = paragraph1.createRun();
	        paragraphtitle1.setBold(true);

	        // paragraphOneRunOne.setItalic(true);
	        paragraphtitle1.addBreak();
	        paragraphtitle1.addBreak();
	        
	        paragraphtitle1.setText("اشعار اغلاق");
	        paragraphtitle1.setFontSize(20);
	        paragraphtitle1.setColor("000000");
	        paragraphtitle1.addBreak();
	               

	        
	     
	        //Body
	        XWPFParagraph paragraph3 =  document.createParagraph();
	        //adding arabic lang
	        CTP ctp3 = paragraph3.getCTP();
	        CTPPr ctppr3 = ctp3.getPPr();
	        if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
	        ctppr3.addNewBidi().setVal(STOnOff.ON);        
	        XWPFRun paragraphbody = paragraph3.createRun();
	        paragraphbody.setBold(false);
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();
	        paragraphbody.setText("المصرف : " + Loan.getBranche().getBank().getBankName() + " - " + Loan.getBranche().getBranchName()  );
	        paragraphbody.addTab();
	        paragraphbody.addTab();
	        paragraphbody.setText(" -" + Loan.getFinanceType() + "- ");
	        paragraphbody.addTab();
	        paragraphbody.addTab();
	        paragraphbody.setText(Loan.getPurpose());
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();    
	        paragraphbody.setText( "يرجى من قسم الحسابات الجارية بيان إمكانية إغلاق السلفة التالية : ");
	        paragraphbody.addBreak();

	        paragraphbody.setColor("101010");
	        paragraphbody.setFontSize(16);
	        paragraphbody.addBreak();
	        
	        
	        			XWPFTable table = document.createTable(2, 6);
	        		
	        			table.setCellMargins(100, 200, 100, 200);
	        			// write to first row, first column
	        			XWPFParagraph p00 = table.getRow(0).getCell(0).getParagraphs().get(0);
	        	        CTP ctp00 = p00.getCTP();
	        	        CTPPr ctppr00 = ctp00.getPPr();
	        	        if (ctppr00 == null) ctppr00 = ctp00.addNewPPr();
	        	        ctppr00.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r00 = p00.createRun();
	        			r00.setText("أجل السلفة" );
	        			r00.setColor("101010");
	        	        r00.setFontSize(14);
	        			
	        			
	        			
	        			XWPFParagraph p01 = table.getRow(0).getCell(1).getParagraphs().get(0);
	        	        CTP ctp01 = p01.getCTP();
	        	        CTPPr ctppr01 = ctp01.getPPr();
	        	        if (ctppr01 == null) ctppr01 = ctp01.addNewPPr();
	        	        ctppr01.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r01 = p01.createRun();
	        			r01.setText("الاعتماد المفتوح مجددا" );
	        			r01.setColor("101010");
	        	        r01.setFontSize(14);
	        			
	        			XWPFParagraph p02 = table.getRow(0).getCell(2).getParagraphs().get(0);
	        	        CTP ctp02 = p02.getCTP();
	        	        CTPPr ctppr02 = ctp02.getPPr();
	        	        if (ctppr02 == null) ctppr02 = ctp02.addNewPPr();
	        	        ctppr02.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r02 = p02.createRun();
	        			r02.setText("التنزيلات من الاعتماد المفتوح" );
	        			r02.setColor("101010");
	        	        r02.setFontSize(14);
	        			
	        			XWPFParagraph p03 = table.getRow(0).getCell(3).getParagraphs().get(0);
	        	        CTP ctp03 = p03.getCTP();
	        	        CTPPr ctppr03 = ctp03.getPPr();
	        	        if (ctppr03 == null) ctppr03 = ctp03.addNewPPr();
	        	        ctppr03.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r03 = p03.createRun();
	        			r03.setText("االاعتماد المفتوح سابقا" );
	        			r03.setColor("101010");
	        	        r03.setFontSize(14);
	        			
	        			XWPFParagraph p04 = table.getRow(0).getCell(4).getParagraphs().get(0);
	        	        CTP ctp04 = p04.getCTP();
	        	        CTPPr ctppr04 = ctp04.getPPr();
	        	        if (ctppr04 == null) ctppr04 = ctp04.addNewPPr();
	        	        ctppr04.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r04 = p04.createRun();
	        			r04.setText("رقم عقد الرهن" );
	        			r04.setColor("101010");
	        	        r04.setFontSize(14);
	        			
	        		

	        			XWPFParagraph p05 = table.getRow(0).getCell(5).getParagraphs().get(0);
	        	        CTP ctp05 = p05.getCTP();
	        	        CTPPr ctppr05 = ctp05.getPPr();
	        	        if (ctppr05 == null) ctppr05 = ctp05.addNewPPr();
	        	        ctppr05.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r05 = p05.createRun();
	        			r05.setText("المستفيد" );
	        			r05.setColor("101010");
	        	        r05.setFontSize(14);
	        			

	        			// write to first row, first column
	        			XWPFParagraph p10 = table.getRow(1).getCell(0).getParagraphs().get(0);
	        	        CTP ctp10 = p10.getCTP();
	        	        CTPPr ctppr10 = ctp10.getPPr();
	        	        if (ctppr10 == null) ctppr10 = ctp10.addNewPPr();
	        	        ctppr10.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r10 = p10.createRun();
	        			r10.setText(Loan.getLoanDate()); // اجل السلفة 
	        			r10.setColor("101010");
	        	        r10.setFontSize(14);
	        			
	        			
	        			
	        			XWPFParagraph p11 = table.getRow(1).getCell(1).getParagraphs().get(0);
	        	        CTP ctp11 = p11.getCTP();
	        	        CTPPr ctppr11 = ctp11.getPPr();
	        	        if (ctppr11 == null) ctppr11 = ctp11.addNewPPr();
	        	        ctppr11.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r11 = p11.createRun();
	        			int x = 0 ;
	        			
	        			for(Vouchers v : allvouchers)
	        			{
	        				if(!v.getStatus().equalsIgnoreCase("paid"))
	        				{
	        					 x += Integer.parseInt(v.getVoucherAmmount());
	        				}
	        			}
	        			
	        			r11.setText(""+x); // الاعتماد المفتوح مجددا 
	        			r11.setColor("101010");
	        	        r01.setFontSize(14);
	        			
	        			XWPFParagraph p12 = table.getRow(1).getCell(2).getParagraphs().get(0);
	        	        CTP ctp12 = p12.getCTP();
	        	        CTPPr ctppr12 = ctp12.getPPr();
	        	        if (ctppr12 == null) ctppr12 = ctp12.addNewPPr();
	        	        ctppr12.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r12 = p12.createRun();
	        			r12.setText(""+voucher.getVoucherAmmount()); // تنزيلات من اعتماد مفتوح 
	        			r12.setColor("101010");
	        	        r12.setFontSize(14);
	        			
	        			XWPFParagraph p13 = table.getRow(1).getCell(3).getParagraphs().get(0);
	        	        CTP ctp13 = p13.getCTP();
	        	        CTPPr ctppr13 = ctp13.getPPr();
	        	        if (ctppr13 == null) ctppr13 = ctp13.addNewPPr();
	        	        ctppr13.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r13 = p13.createRun();
	        			r13.setText(""+Loan.getTotalAmmount()); // الاعتماد المفتوح سابقا
	        			r13.setColor("101010");
	        	        r13.setFontSize(14);
	        			
	        			XWPFParagraph p14 = table.getRow(1).getCell(4).getParagraphs().get(0);
	        	        CTP ctp14 = p14.getCTP();
	        	        CTPPr ctppr14 = ctp14.getPPr();
	        	        if (ctppr14 == null) ctppr14 = ctp14.addNewPPr();
	        	        ctppr14.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r14 = p14.createRun();
	        			r14.setText(""+Loan.getLoanID()); // رقم الرهن
	        			r14.setColor("101010");
	        	        r14.setFontSize(14);
	        			
	        		

	        			XWPFParagraph p15 = table.getRow(1).getCell(5).getParagraphs().get(0);
	        	        CTP ctp15 = p15.getCTP();
	        	        CTPPr ctppr15 = ctp15.getPPr();
	        	        if (ctppr15 == null) ctppr15 = ctp15.addNewPPr();
	        	        ctppr15.addNewBidi().setVal(STOnOff.ON);        	        
	        			XWPFRun r15 = p15.createRun();
	        			r15.setText(Loan.getClient().getClientName() ); // المستفيد
	        			r15.setColor("101010");
	        	        r15.setFontSize(14);
	        			
	      
	        	        
	        		
	      
	        
	        XWPFParagraph p4 = document.createParagraph();
	        CTP ctp4 = p4.getCTP();
	        CTPPr ctppr4 = ctp4.getPPr();
	        if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
	        ctppr4.addNewBidi().setVal(STOnOff.ON);        	        
			XWPFRun r4 = p4.createRun();
	        
	        
			r4.addBreak();
	        r4.setText( " فقط " + "  __________________________________: " + "ليرة سورية لا غير");
	        r4.addBreak();
	        r4.addBreak();
	        r4.addBreak();

	        r4.addBreak();
	        r4.addTab();
	        
	        r4.setText( "دمشق في "+dateFormat.format(date));
	        r4.addBreak();
	        r4.addBreak();
	        r4.addBreak();
	        r4.addBreak();
	        r4.addBreak();
	        r4.addBreak();
	        r4.addTab();
	        r4.addTab();
	        r4.addTab();
	        r4.addTab();
	        r4.setText( "رئيس قسم التسليف ");
	        r4.addTab();
	        r4.setText( "مدير فرع مصرف سورية المركزي");
	        
	     
	        r4.setBold(false);
	        r4.setColor("101010");
	        r4.setFontSize(14);
	      
	   
	        try {
				document.write(out);
		        out.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	         return returnPath ; 



	    }
}

