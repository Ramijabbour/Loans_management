package com.DocumentTemplate;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import com.example.settelmets.Models.SettledChaque;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DOCXDOC implements CreateDocTemplate {
	
	public DOCXDOC(){
	}
	
    @Override
    public String CreateRTGSDoc(SettledChaque settledChaque) {
    /*    String Path= System.getProperty("user.dir")+"\\";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String Filename=settledChaque.getFirstBankSW()+dateFormat.format(date);
        //Blank Document
        XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = null;
        String returnPath = Path+Filename+".docx"; 
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
        paragraphtitle.setText("نظام التسوية اللحظية");
        paragraphtitle.addBreak();
        //title2
        //create another paragraph
        XWPFParagraph paragraph2 = document.createParagraph();
        //adding arabic lang
        CTP ctp2 = paragraph2.getCTP();
        CTPPr ctppr2 = ctp.getPPr();
        if (ctppr2 == null) ctppr2 = ctp.addNewPPr();
        ctppr2.addNewBidi().setVal(STOnOff.ON);
        paragraph2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphtitle2 = paragraph2.createRun();
        paragraphtitle2.setText("اشعار دفع مستحقات مادية");
        paragraphtitle2.setColor("000000");
        paragraphtitle2.setFontSize(18);
        //Body
        XWPFParagraph paragraph3 = document.createParagraph();
        //adding arabic lang
        CTP ctp3 = paragraph3.getCTP();
        CTPPr ctppr3 = ctp.getPPr();
        if (ctppr3 == null) ctppr3 = ctp.addNewPPr();
        ctppr3.addNewBidi().setVal(STOnOff.ON);
        paragraph3.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun paragraphbody = paragraph3.createRun();
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.setText(":" + "الى البنك");
        paragraphbody.setText(settledChaque.getFirstBank());
        paragraphbody.addBreak();
        paragraphbody.setText(String.valueOf(settledChaque.getFirstBankSW()));
        paragraphbody.setText(":" + "رمز الفرع");
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.setText("استنادا لنتيجة نظام التسوية اللحظية الخاص بمصرف سورية المركزي توجب عليكم دفع المستحقات المالية المفروضة عليكم و قدرها");
        paragraphbody.setText(")");
        paragraphbody.setText(settledChaque.getAmount()+ "(ل.س");
        paragraphbody.setText("");
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.setText(":علما انه سيتم تسديد هذ المستحقات الى");
        paragraphbody.setText("");
        paragraphbody.addBreak();
        paragraphbody.setText("البنك:");
        paragraphbody.setText(settledChaque.getSecondBank());
        paragraphbody.addBreak();
        paragraphbody.setText(String.valueOf(settledChaque.getSecondBankSW()));
        paragraphbody.setText(":" + "رمز الفرع");
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.addBreak();
        paragraphbody.setText(":دمشق في");
        paragraphbody.addBreak();
        paragraphbody.setText(dateFormat.format(date));
        paragraphbody.setBold(false);
        paragraphbody.setColor("101010");
        paragraphbody.setFontSize(15);
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
        
        return returnPath ; 
     */
    	//remove return stmt after fix 
    		return null ; 
    }

}

