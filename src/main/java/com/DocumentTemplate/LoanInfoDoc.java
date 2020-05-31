package com.DocumentTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.OverridesAttribute;

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

public class LoanInfoDoc implements CreateInfoDocTemplate {
	public LoanInfoDoc() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String CreateInfoLoanDoc(Loans Loan, List<Vouchers> allvouchers) {
		
        String Path= System.getProperty("user.dir")+"\\";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String Filename="Loan_info_"+Loan.getId()+dateFormat.format(date)  ;
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
        paragraphtitle1.setText("معلومات سلفة ");
        paragraphtitle1.setFontSize(20);
        paragraphtitle1.setColor("000000");
               

        
     
        //Body
        XWPFParagraph paragraph2 =  document.createParagraph();
        //adding arabic lang
        CTP ctp2 = paragraph2.getCTP();
        CTPPr ctppr2 = ctp2.getPPr();
        if (ctppr2 == null) ctppr2 = ctp2.addNewPPr();
        ctppr2.addNewBidi().setVal(STOnOff.ON);
        paragraph2.setSpacingAfter(10);
        XWPFRun paragraphbody = paragraph2.createRun();
        paragraphbody.addBreak();
        paragraphbody.setText( " رقم الرهن : "  );
        paragraphbody.addTab();
        paragraphbody.setText(""+Loan.getName());
        paragraphbody.addBreak();
        paragraphbody.setText("المصرف  : ");
        paragraphbody.addTab();
        paragraphbody.setText(Loan.getBranche().getBank().getBankName() +" - "+Loan.getBranche().getBranchName());
        
       
        
        
        XWPFParagraph paragraph3 = document.createParagraph();
        CTP ctp3 = paragraph3.getCTP();
        CTPPr ctppr3 = ctp3.getPPr();
        if (ctppr3 == null) ctppr3 = ctp3.addNewPPr();
        ctppr3.addNewBidi().setVal(STOnOff.ON);
        paragraph3.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphbody2 = paragraph3.createRun();
       
        paragraphbody2.addBreak(); 
        paragraphbody2.setText( " عدد السندات"+ "                       "    +  " المبلغ الإجمالي " +  "                       "    + "المبلغ الصافي ");
        paragraphbody2.addBreak();
        paragraphbody2.setText( "________________________________________________________");
        paragraphbody2.addBreak();
        for(Vouchers v : allvouchers ) {
        paragraphbody2.setText( v.getVoucherAmmount() + "                           "    +  v.getVoucherAmmount()+"        " +  "                           "    + " 1 " +"       ");
        paragraphbody2.addBreak();
        }
        paragraphbody2.setText( "________________________________________________________");
        
        XWPFParagraph paragraph4 = document.createParagraph();
        CTP ctp4 = paragraph4.getCTP();
        CTPPr ctppr4 = ctp4.getPPr();
        if (ctppr4 == null) ctppr4 = ctp4.addNewPPr();
        ctppr4.addNewBidi().setVal(STOnOff.ON);
        XWPFRun paragraphbody3 = paragraph4.createRun();
        
        paragraphbody3.setText(  "اجمالي السندات  : ");
        paragraphbody3.addTab();
        paragraphbody3.setText(""+allvouchers.size());
        paragraphbody3.addBreak();
        paragraphbody3.setText(  "اجمالي المبالغ  : ");
        paragraphbody3.addTab();
        paragraphbody3.setText(Loan.getTotalAmmount());
        paragraphbody3.addBreak();
        paragraphbody3.setText(  "اجمالي الصوافي : ");
        paragraphbody3.addTab();
        paragraphbody3.setText(Loan.getTotalAmmount());
        paragraphbody3.addBreak();
        paragraphbody3.setText(  "المبالغ المرخصة : ");
        paragraphbody3.addTab();
        paragraphbody3.setText("");
        paragraphbody3.addBreak();
        paragraphbody3.setText(  "المبالغ المعفاة : ");
        paragraphbody3.addTab();
        paragraphbody3.setText(Loan.getTotalAmmount());
        paragraphbody3.addBreak();
        paragraphbody3.setText("__________________________________________________");

        
        
        
     // create table with 3 rows and 4 columns
     			XWPFTable table = document.createTable(allvouchers.size()+1, 0);
     		
     			table.setCellMargins(100, 200, 100, 200);
     			// write to first row, first column
     			XWPFParagraph p11 = table.getRow(0).getCell(0).getParagraphs().get(0);
     			  CTP ctp5= p11.getCTP();
     		        CTPPr ctppr5 = ctp5.getPPr();
     		        if (ctppr5 == null) ctppr5 = ctp5.addNewPPr();
     		        ctppr5.addNewBidi().setVal(STOnOff.ON);    			
     			XWPFRun r1 = p11.createRun();
     			r1.setBold(true);
     			r1.setText("الاعتماد المفتوح = " );
     			r1.addTab();
     			r1.setText(Loan.getTotalAmmount());
     			int i = 1 ;	
     			int cost = Integer.parseInt(Loan.getTotalAmmount());
     		for(Vouchers v : allvouchers)
     		{
     			
     			XWPFParagraph p22 = table.getRow(i).getCell(0).getParagraphs().get(0);
     			  CTP ctp6= p22.getCTP();
   		        CTPPr ctppr6 = ctp6.getPPr();
   		        if (ctppr6 == null) ctppr6 = ctp6.addNewPPr();
   		        ctppr6.addNewBidi().setVal(STOnOff.ON);   			
     			XWPFRun r2 = p22.createRun();
     			r2.setBold(true);
     			r2.setText("صافي مبلغ الاستحقاق : ");
     			r2.addTab();
     			r2.setText(v.getVoucherAmmount());
     			r2.addTab();
     			r2.addTab();
     			r2.addTab();
     			r2.setText("الاستحقاق : ");
     			r2.addTab();
     			r2.setText(v.getVoucherDate());
     			r2.addBreak();
     			r2.setText("الصافي المتبقي : ");
     			r2.addTab();
     			cost=cost-Integer.parseInt(v.getVoucherAmmount());
     			r2.setText(""+cost);
     			i++;
     			}
     			 	   
        
        
     
        paragraphbody2.setBold(false);
        paragraphbody.setBold(false);
        paragraphbody2.setColor("101010");
        paragraphbody2.setFontSize(16);
        paragraphbody.setColor("101010");
        paragraphbody.setFontSize(16);
        paragraphbody3.setBold(false);
        paragraphbody3.setColor("101010");
        paragraphbody3.setFontSize(16);
        
        
        
        try {
			document.write(out);
	        out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}






		return returnPath;
	}
}