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
import com.example.MQ.SettledChaque;

public class  OpenLoansContrantDocs implements CreateOpenDocTemplate {
	
	public OpenLoansContrantDocs() {
	}
	/*
	  @Override
	  public void CreateOpenLoanDoc(Loans Loan) {


	        String Path= System.getProperty("user.dir")+"\\";
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        Date date = new Date();
	        String Filename=Loan.+dateFormat.format(date);
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
	        CTP ctp1 = paragraph.getCTP();
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
	        
	        paragraphtitle1.setText("سلفة فتح قيد");
	        paragraphtitle1.setFontSize(20);
	        paragraphtitle1.setColor("000000");
	        paragraphtitle1.addBreak();
	               

	        
	     
	        //Body
	        XWPFParagraph paragraph3 =  document.createParagraph();
	        //adding arabic lang
	        CTP ctp3 = paragraph.getCTP();
	        CTPPr ctppr3 = ctp3.getPPr();
	        if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
	        ctppr3.addNewBidi().setVal(STOnOff.ON);
	        paragraph3.setAlignment(ParagraphAlignment.RIGHT);
	        XWPFRun paragraphbody = paragraph3.createRun();
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();
	        paragraphbody.setText("\'"+Bank + "\'"+ "    :  المصرف  " + dateFormat.format(date) + "     :   التاريخ "+"        "  + LoanID +"     :   رقم السلفة "  );
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();       
	        paragraphbody.setText( "\'"+Client + "\'"+ "    :  لصالح ");
	        paragraphbody.addBreak();
	        paragraphbody.setText( "\' "+from + " \'"+ "    :  الجهة المدينة ");
	        paragraphbody.addBreak();
	        paragraphbody.setText(Bank);
	        paragraphbody.addBreak();
	        paragraphbody.setColor("101010");
	        paragraphbody.setFontSize(16);
	        paragraphbody.addBreak();
	        
	        
	        XWPFParagraph paragraph4 = document.createParagraph();
	        CTP ctp4 = paragraph.getCTP();
	        CTPPr ctppr4 = ctp4.getPPr();
	        if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
	        ctppr4.addNewBidi().setVal(STOnOff.ON);
	        paragraph4.setAlignment(ParagraphAlignment.CENTER);
	        XWPFRun paragraphbody2 = paragraph4.createRun();
	        
	        paragraphbody2.addBreak(); 
	        paragraphbody2.setText( "\'"+ cost + "\'" +   "                   "    +"\'"+sndat + "\'"+ "    :  السندات عدد ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\' "+ to + " \'"+ "    :  الى ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\'"+ cost + "\'" +   "                   "    +"\'"+sndat + "\'"+ "    :  السندات عدد ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "    _______________________________________    ");
	        paragraphbody2.addBreak();
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\' "+ cost + " \'"+   "           "   +"\' "+cost + " \'"+ "  :  المجموع ");
	        paragraphbody2.addBreak();
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "غير لا "+"سورية  ليرة "+" ___________________________ " + "    :  مبلغ فقط  ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setBold(false);
	      
	        paragraphbody2.setColor("101010");
	        paragraphbody2.setFontSize(16);
	        try {
	            document.write(out);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            out.close();
	            document.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("fontstyle.docx written successully");
	        



	    }
*/

	@Override
	public void CreateOpenLoanDoc(Loans Loan) {
		// TODO Auto-generated method stub
		  String Path= System.getProperty("user.dir")+"\\";
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        Date date = new Date();
	        String Filename=Loan.getLoanID()+dateFormat.format(date);
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
	        CTP ctp1 = paragraph.getCTP();
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
	        
	        paragraphtitle1.setText("سلفة فتح قيد");
	        paragraphtitle1.setFontSize(20);
	        paragraphtitle1.setColor("000000");
	        paragraphtitle1.addBreak();
	               

	        
	     
	        //Body
	        XWPFParagraph paragraph3 =  document.createParagraph();
	        //adding arabic lang
	        CTP ctp3 = paragraph.getCTP();
	        CTPPr ctppr3 = ctp3.getPPr();
	        if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
	        ctppr3.addNewBidi().setVal(STOnOff.ON);
	        paragraph3.setAlignment(ParagraphAlignment.RIGHT);
	        XWPFRun paragraphbody = paragraph3.createRun();
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();
	        paragraphbody.setText("\'"+Loan.getBank().BankName + "\'"+ "    :  المصرف  " + dateFormat.format(date) + "     :   التاريخ "+"        "  + Loan.getLoanID() +"     :   رقم السلفة "  );
	        paragraphbody.addBreak();
	        paragraphbody.addBreak();       
	        paragraphbody.setText( "\'"+Loan.getClient() + "\'"+ "    :  لصالح ");
	        paragraphbody.addBreak();
	        paragraphbody.setText( "\' "+Loan.getFirstSide() + " \'"+ "    :  الجهة المدينة ");
	        paragraphbody.addBreak();
	        paragraphbody.setText(Loan.getBank().BankName + "/"+Loan.getBank().BranchName);
	        paragraphbody.addBreak();
	        paragraphbody.setColor("101010");
	        paragraphbody.setFontSize(16);
	        paragraphbody.addBreak();
	        
	        
	        XWPFParagraph paragraph4 = document.createParagraph();
	        CTP ctp4 = paragraph.getCTP();
	        CTPPr ctppr4 = ctp4.getPPr();
	        if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
	        ctppr4.addNewBidi().setVal(STOnOff.ON);
	        paragraph4.setAlignment(ParagraphAlignment.CENTER);
	        XWPFRun paragraphbody2 = paragraph4.createRun();
	        
	        paragraphbody2.addBreak(); 
	        paragraphbody2.setText( "\'"+ Loan.getNetAmmountAsString() + "\'" +   "                   "    +"\'"+Loan.getNumberOfVoucherAsString() + "\'"+ "    :  السندات عدد ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\' "+ Loan.getSecondSide() + " \'"+ "    :  الى ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\'"+ Loan.getNetAmmountAsString() + "\'" +   "                   "    +"\'"+Loan.getNumberOfVoucherAsString() + "\'"+ "    :  السندات عدد ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "    _______________________________________    ");
	        paragraphbody2.addBreak();
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "\' "+ Loan.getTotalAmmountAsString() + " \'"+   "           "   +"\' "+Loan.getTotalAmmountAsString() + " \'"+ "  :  المجموع ");
	        paragraphbody2.addBreak();
	        paragraphbody2.addBreak();
	        paragraphbody2.setText( "غير لا "+"سورية  ليرة "+" ___________________________ " + "    :  مبلغ فقط  ");
	        paragraphbody2.addBreak();
	        paragraphbody2.setBold(false);
	      
	        paragraphbody2.setColor("101010");
	        paragraphbody2.setFontSize(16);
	        try {
	            document.write(out);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            out.close();
	            document.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("fontstyle.docx written successully");
	        

	}

	
}

