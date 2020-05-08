package com.example.OpenLoans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import com.example.Loans.Loans;

public class  RegularOpenLoansReportDocs implements CreateOpenDocTemplate {
	
	public RegularOpenLoansReportDocs() {
	}
	
	  @Override
	  public String CreateOpenLoanDoc(Loans Loan) {


	        String Path= System.getProperty("user.dir")+"\\";
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        Date date = new Date();
	        String Filename="Regopen_Loan_"+Loan.getId()+dateFormat.format(date)  ;
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
	         
	         paragraphtitle1.setText("قيد  نظامي فتح سلفة");
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
	         paragraphbody.setText("السلفة  رقم  : " );
	         paragraphbody.addTab();
	         paragraphbody.setText(""+Loan.getLoanNumber());
	         paragraphbody.addTab();
	         paragraphbody.setText("التاريخ :  ");
	         paragraphbody.addTab();
	         paragraphbody.setText(Loan.getLoanDate());
	         paragraphbody.addTab();
	         paragraphbody.addTab();
	         paragraphbody.setText("المصرف : " + Loan.getBranche().getBank().getBankName() + " - " + Loan.getBranche().getBranchName()  );
	         paragraphbody.addBreak();
	         paragraphbody.addBreak();
	         paragraphbody.setText("لصالح : " + Loan.getClient().getClientName());
	         paragraphbody.addBreak();
	         paragraphbody.addBreak();
	         paragraphbody.addBreak();
	         paragraphbody.addTab();
	         paragraphbody.addTab();
	         paragraphbody.setText("الجهة المدينة : الضمانات لقاء القروض والسلف");
	         paragraphbody.addBreak();
	         paragraphbody.addTab();
	         paragraphbody.addTab();
	         paragraphbody.addTab();
	         paragraphbody.setText("السندات التجارية");
	         paragraphbody.addBreak();
	         paragraphbody.addBreak();
	   
	         
	         
	         paragraphbody.setColor("101010");
	         paragraphbody.setFontSize(16);
	         
	         
	         XWPFParagraph p2 = document.createParagraph();
	         CTP ctp2 = p2.getCTP();
	         CTPPr ctppr2 = ctp2.getPPr();
	         if (ctppr2 == null) ctppr2 = ctp2.addNewPPr();
	         ctppr2.addNewBidi().setVal(STOnOff.ON);  
	         p2.setAlignment(ParagraphAlignment.CENTER);
	 		XWPFRun r4 = p2.createRun();
	         

	         r4.setText( "عدد السندات :   ");
	         r4.addTab();
	         r4.setText(Loan.getNumberOfVoucher());
	         r4.addTab();
	         r4.setText(Loan.getTotalAmmount());
	         r4.addBreak();
	         
	         r4.setColor("101010");
	         r4.setFontSize(16);

	         
	         
	         XWPFParagraph p3 = document.createParagraph();
	         CTP ctp33 = p3.getCTP();
	         CTPPr ctppr33 = ctp33.getPPr();
	         if (ctppr33 == null) ctppr33 = ctp33.addNewPPr();
	         ctppr33.addNewBidi().setVal(STOnOff.ON);  
	         p3.setAlignment(ParagraphAlignment.CENTER);

	 		XWPFRun r3 = p3.createRun();

	 		r3.setText(" الجهة الدائنة  :    مودعو السندات الموضوعة" );
	 		r3.addBreak();
	 		r3.addTab();
	 		r3.addTab();
	 		r3.addTab();
	 		r3.setText("ضمانة للقروض والسلف");
	        r3.setColor("101010");
	        r3.setFontSize(16);
	        
	        
	        
	        XWPFParagraph p4 = document.createParagraph();
	        CTP ctp22 = p4.getCTP();
	        CTPPr ctppr22 = ctp22.getPPr();
	        if (ctppr22 == null) ctppr22 = ctp22.addNewPPr();
	        ctppr22.addNewBidi().setVal(STOnOff.ON);  
	        p4.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r2 = p4.createRun();
	        
	        r2.setText( "عدد السندات :  " );
	        r2.addTab();
	        r2.setText(Loan.getNumberOfVoucher());
	        r2.addTab();
	        r2.setText(Loan.getTotalAmmount());
	        r2.addBreak();
	        
	        r2.setText("_____________________________________________");
	        r2.addBreak();
	        r2.addBreak();
	       
	        r2.addBreak();
	        r2.addBreak();
	        r2.addBreak();
	       
	        r2.setText("المجموع : ");
	        r2.addTab();
	        r2.setText(Loan.getTotalAmmount());
	        r2.addTab();
	        r2.addTab();
	        r2.setText(Loan.getTotalAmmount());
	               
	        
	        
	        r2.setColor("101010");
	        r2.setFontSize(16);
	       
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

