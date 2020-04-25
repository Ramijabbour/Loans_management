package com.example.Loans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.validation.OverridesAttribute;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

public class LoanInfoDoc implements CreateInfoDocTemplate {
	public LoanInfoDoc() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void CreateInfoLoanDoc(Loans Loan) {
		// TODO Auto-generated method stub
		
	
	
		 //Blank Document
       XWPFDocument document = new XWPFDocument();


       //Write the Document in file system
       FileOutputStream out;
		try {
			out = new FileOutputStream(new File("D:\\Info.docx"));
		

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
       CTPPr ctppr1 = ctp.getPPr();
       if (ctppr1 == null) ctppr1 = ctp1.addNewPPr();
       ctppr1.addNewBidi().setVal(STOnOff.ON);
       paragraph1.setAlignment(ParagraphAlignment.CENTER);
       
       //Tiltle
       XWPFRun paragraphtitle1 = paragraph1.createRun();
       paragraphtitle1.setBold(true);

       // paragraphOneRunOne.setItalic(true);
       paragraphtitle1.addBreak();
       paragraphtitle1.addBreak();
       
       paragraphtitle1.setText("سلفة معلومات ");
       paragraphtitle1.setFontSize(20);
       paragraphtitle1.setColor("000000");
       paragraphtitle1.addBreak();
              

       
    
       //Body
       XWPFParagraph paragraph2 =  document.createParagraph();
       //adding arabic lang
       CTP ctp2 = paragraph1.getCTP();
       CTPPr ctppr2 = ctp.getPPr();
       if (ctppr2 == null) ctppr2 = ctp2.addNewPPr();
       ctppr2.addNewBidi().setVal(STOnOff.ON);
       paragraph2.setAlignment(ParagraphAlignment.RIGHT);
       paragraph2.setSpacingAfter(10);
       XWPFRun paragraphbody = paragraph2.createRun();
       paragraphbody.addBreak();
       paragraphbody.addBreak();
       paragraphbody.setText(Loan.getLoanID() +"            :   رقم الرهن "  );
       paragraphbody.addBreak();
       paragraphbody.setText(Loan.getBank().getBankName()  + " :  المصرف ");
       paragraphbody.addBreak();
      
       
       
       XWPFParagraph paragraph3 = document.createParagraph();
       CTP ctp3 = paragraph2.getCTP();
       CTPPr ctppr3 = ctp.getPPr();
       if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
       ctppr3.addNewBidi().setVal(STOnOff.ON);
       paragraph3.setAlignment(ParagraphAlignment.CENTER);
       XWPFRun paragraphbody2 = paragraph3.createRun();
      
       paragraphbody2.addBreak(); 
       paragraphbody2.setText( "الصافي المبلغ"+ "                       "    +  "الإجمالي المبلغ" +  "                       "    + "السندات عدد");
       paragraphbody2.addBreak();
       paragraphbody2.setText( "________________________________________________________");
       paragraphbody2.addBreak();
       for(int i = 0 ;i<Integer.parseInt(Loan.getNumberOfVoucherAsString()); i ++ ) {
       paragraphbody2.setText( Loan.getTotalAmmountAsString() + "                       "    +  Loan.getTotalAmmountAsString()+"        " +  "                       "    + " 1 " +"       ");
       paragraphbody2.addBreak();
       }
       paragraphbody2.setText( "________________________________________________________");
       
       XWPFParagraph paragraph4 = document.createParagraph();
       CTP ctp4 = paragraph3.getCTP();
       CTPPr ctppr4 = ctp.getPPr();
       if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
       ctppr4.addNewBidi().setVal(STOnOff.ON);
       paragraph4.setAlignment(ParagraphAlignment.RIGHT);
       XWPFRun paragraphbody3 = paragraph4.createRun();
       
       paragraphbody3.setText( Loan.getNumberOfVoucherAsString() + "    :  السندات اجمالي ");
       paragraphbody3.addBreak();
       paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  المبالغ اجمالي ");
       paragraphbody3.addBreak();
       paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  الصوافي اجمالي ");
       paragraphbody3.addBreak();
       paragraphbody3.setText( "" + "    :  المرخصة المبالغ ");
       paragraphbody3.addBreak();
       paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  المعطاة المبالغ ");
       paragraphbody3.addBreak();
       paragraphbody3.setText("__________________________________________________");
       paragraphbody3.addBreak();
       
       
       
     /*
       
       XWPFTable tab11 = document.createTable(); 
       XWPFTableRow row = tab11.getRow(0); // First row  
    
       // Columns         
       row.getCell(0).setText("Sl. No.");  
       row.addNewTableCell().setText("Name");  
       row.addNewTableCell().setText("Email");  
       row = tab11.createRow(); // Second Row  
       row.getCell(0).setText("1.");  
       row.getCell(1).setText("Irfan");  
       row.getCell(2).setText("irfan@gmail.com");  
       row = tab11.createRow(); // Third Row  
       row.getCell(0).setText("2.");  
       row.getCell(1).setText("Mohan");  
       row.getCell(2).setText("mohan@gmail.com");  
        */
       paragraphbody2.setBold(false);
       paragraphbody.setBold(false);
       paragraphbody2.setColor("101010");
       paragraphbody2.setFontSize(16);
       paragraphbody.setColor("101010");
       paragraphbody.setFontSize(16);
       paragraphbody3.setBold(false);
       paragraphbody3.setColor("101010");
       paragraphbody3.setFontSize(16);
       
       
       
       
			document.write(out);
	        out.close();
	        document.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

	
	
	/*
	@Override
    public void CreateInfoLoanDoc(Loans Loan) {
		
		
		 //Blank Document
        XWPFDocument document = new XWPFDocument();


        //Write the Document in file system
        FileOutputStream out;
		try {
			out = new FileOutputStream(new File("D:\\Info.docx"));
		

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
        CTPPr ctppr1 = ctp.getPPr();
        if (ctppr1 == null) ctppr1 = ctp1.addNewPPr();
        ctppr1.addNewBidi().setVal(STOnOff.ON);
        paragraph1.setAlignment(ParagraphAlignment.CENTER);
        
        //Tiltle
        XWPFRun paragraphtitle1 = paragraph1.createRun();
        paragraphtitle1.setBold(true);

        // paragraphOneRunOne.setItalic(true);
        paragraphtitle1.addBreak();
        paragraphtitle1.addBreak();
        
        paragraphtitle1.setText("سلفة معلومات ");
        paragraphtitle1.setFontSize(20);
        paragraphtitle1.setColor("000000");
        paragraphtitle1.addBreak();
               

        
     
        //Body
        XWPFParagraph paragraph2 =  document.createParagraph();
        //adding arabic lang
        CTP ctp2 = paragraph1.getCTP();
        CTPPr ctppr2 = ctp.getPPr();
        if (ctppr2 == null) ctppr2 = ctp2.addNewPPr();
        ctppr2.addNewBidi().setVal(STOnOff.ON);
        paragraph2.setAlignment(ParagraphAlignment.RIGHT);
        paragraph2.setSpacingAfter(10);
        XWPFRun paragraphbody = paragraph2.createRun();
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.setText(Loan.getLoanID() +"            :   رقم الرهن "  );
        paragraphbody.addBreak();
        paragraphbody.setText(Loan.getBank().getBankName()  + " :  المصرف ");
        paragraphbody.addBreak();
       
        
        
        XWPFParagraph paragraph3 = document.createParagraph();
        CTP ctp3 = paragraph2.getCTP();
        CTPPr ctppr3 = ctp.getPPr();
        if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
        ctppr3.addNewBidi().setVal(STOnOff.ON);
        paragraph3.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphbody2 = paragraph3.createRun();
       
        paragraphbody2.addBreak(); 
        paragraphbody2.setText( "الصافي المبلغ"+ "                       "    +  "الإجمالي المبلغ" +  "                       "    + "السندات عدد");
        paragraphbody2.addBreak();
        paragraphbody2.setText( "________________________________________________________");
        paragraphbody2.addBreak();
        for(int i = 0 ;i<Integer.parseInt(Loan.getNumberOfVoucherAsString()); i ++ ) {
        paragraphbody2.setText( Loan.getTotalAmmountAsString() + "                       "    +  Loan.getTotalAmmountAsString()+"        " +  "                       "    + " 1 " +"       ");
        paragraphbody2.addBreak();
        }
        paragraphbody2.setText( "________________________________________________________");
        
        XWPFParagraph paragraph4 = document.createParagraph();
        CTP ctp4 = paragraph3.getCTP();
        CTPPr ctppr4 = ctp.getPPr();
        if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
        ctppr4.addNewBidi().setVal(STOnOff.ON);
        paragraph4.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun paragraphbody3 = paragraph4.createRun();
        
        paragraphbody3.setText( Loan.getNumberOfVoucherAsString() + "    :  السندات اجمالي ");
        paragraphbody3.addBreak();
        paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  المبالغ اجمالي ");
        paragraphbody3.addBreak();
        paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  الصوافي اجمالي ");
        paragraphbody3.addBreak();
        paragraphbody3.setText( "" + "    :  المرخصة المبالغ ");
        paragraphbody3.addBreak();
        paragraphbody3.setText( Loan.getTotalAmmountAsString() + "    :  المعطاة المبالغ ");
        paragraphbody3.addBreak();
        paragraphbody3.setText("__________________________________________________");
        paragraphbody3.addBreak();
        
        
        
      /*
        
        XWPFTable tab11 = document.createTable(); 
        XWPFTableRow row = tab11.getRow(0); // First row  
     
        // Columns         
        row.getCell(0).setText("Sl. No.");  
        row.addNewTableCell().setText("Name");  
        row.addNewTableCell().setText("Email");  
        row = tab11.createRow(); // Second Row  
        row.getCell(0).setText("1.");  
        row.getCell(1).setText("Irfan");  
        row.getCell(2).setText("irfan@gmail.com");  
        row = tab11.createRow(); // Third Row  
        row.getCell(0).setText("2.");  
        row.getCell(1).setText("Mohan");  
        row.getCell(2).setText("mohan@gmail.com");  
         
        paragraphbody2.setBold(false);
        paragraphbody.setBold(false);
        paragraphbody2.setColor("101010");
        paragraphbody2.setFontSize(16);
        paragraphbody.setColor("101010");
        paragraphbody.setFontSize(16);
        paragraphbody3.setBold(false);
        paragraphbody3.setColor("101010");
        paragraphbody3.setFontSize(16);
        
        
        
        
			document.write(out);
	        out.close();
	        document.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    */
}
