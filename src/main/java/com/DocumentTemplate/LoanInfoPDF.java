package com.DocumentTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class LoanInfoPDF implements CreateInfoDocTemplate{

	public LoanInfoPDF() {
		// TODO Auto-generated constructor stub
	}
	
	 public static Font normal = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 26);
	    public static Font normal2 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);
	    public static Font normal3 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);

	
	@Override
	public String CreateInfoLoanDoc(Loans Loan, List<Vouchers> allvouchers) {

		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        normal.setColor(BaseColor.BLACK.darker());
	        normal2.setColor(BaseColor.BLACK.brighter());
	        normal3.setColor(BaseColor.GRAY.darker());
	        Document document = new Document();
	        String Path=System.getProperty("user.dir")+"\\";
	        String Filename="Loan_info_"+Loan.getId()  ;
	        String returnPath = Path+Filename+".pdf" ; 

	        
	        try {
	            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Path+Filename+".pdf"));
	            document.open();
	            PdfPTable table = new PdfPTable(1);
	            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
	            PdfPCell cell = new PdfPCell();
		
	            Paragraph p = new Paragraph("الجمهورية العربية السورية \nمصرف سورية المركزي\n قسم التسليف \n\n                             معلومات سلفة \n\n", normal);
	            Paragraph p2=new Paragraph("رقم  الرهن: " + Loan.getName() +"\n" + "المصرف : " + Loan.getBranche().getBank().getBankName()+" - "+Loan.getBranche().getBranchName() +" \n" +
	            		
	            	    "            " + "عدد السندات "+ "                     "+"المبلغ الاجمالي"+ "                     "+"المبلغ الصافي" +"\n"+
	            	    "       " +"__________________________________________"+"\n",normal2);
	            
	            Paragraph p3=new Paragraph( "",normal2);
	            for( Vouchers v : allvouchers)
	            p3.add("                  " + 1 +"                             "+v.getVoucherAmmount()+"                       "+v.getVoucherAmmount() + "\n");
	            Paragraph p4;
	            if(Loan.getLoanType().getTypeName().equals("مرخص"))
	            { p4=new Paragraph(  "       " +"__________________________________________"+"\n"+
	                    "  اجمالي السندات : " + allvouchers.size() +"\n"+
	                    "  اجمالي المبالغ : " + Loan.getTotalAmmount() +"\n"+
	                    "  اجمالي الصوافي : " + Loan.getTotalAmmount() +"\n"+
	                    "  المبالغ  المرخصة : " + Loan.getTotalAmmount()  +"\n"+
	                    "  المبالغ المعطاة : " + "" +"\n\n",normal2);
	            }
	            else
	            {
	            	 p4=new Paragraph(  "       " +"__________________________________________"+"\n"+
	 	                    "  اجمالي السندات : " + allvouchers.size() +"\n"+
	 	                    "  اجمالي المبالغ : " + Loan.getTotalAmmount() +"\n"+
	 	                    "  اجمالي الصوافي : " + Loan.getTotalAmmount() +"\n"+
	 	                    "  المبالغ  المرخصة : " + "" +"\n"+
	 	                    "  المبالغ المعطاة : " + Loan.getTotalAmmount() +"\n\n",normal2);
	            }
	            
	            PdfPTable table2 = new PdfPTable(1);
	            table2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
	            PdfPCell cell2 = new PdfPCell();
	            Paragraph p11 = new Paragraph(" الاعتماد المفتوح =  " + Loan.getTotalAmmount(), normal3);
	            p11.setAlignment(PdfPCell.ALIGN_LEFT);
	            cell2.addElement(p11);
	            
	            cell2.setPaddingTop(1);
	            cell2.setPaddingBottom(7);
	            cell2.setPaddingRight(5);
	            cell2.setPaddingLeft(5);

	            table2.addCell(cell2);
	            int ammount = Integer.parseInt(Loan.getTotalAmmount());
	            for(Vouchers v : allvouchers) {
	            PdfPCell cell3 = new PdfPCell();
	            
	            
	            ammount = ammount - Integer.parseInt(v.getVoucherAmmount());
	            Paragraph p22 = new Paragraph("صافي مبلغ الاستحقاق = " + v.getVoucherAmmount() + "                                           " + "الاستحقاق : " + v.getVoucherDate()  + "\n"+
	            							  "الصافي المتبقي = " + ammount, normal3);
	            
	            
	            
	            p22.setAlignment(PdfPCell.ALIGN_LEFT);
	            cell3.addElement(p22);
	            
	            cell3.setPaddingTop(1);
	            cell3.setPaddingBottom(7);
	            cell3.setPaddingRight(5);
	            cell3.setPaddingLeft(5);
	            
	            table2.addCell(cell3);
	            
	            }
	            
	            
	            
	            
	            p.setAlignment(PdfPCell.ALIGN_LEFT);
	            cell.addElement(p);
	            cell.addElement(p2);
	            cell.addElement(p3);
	            cell.addElement(p4);
	            cell.setBorder(Rectangle.NO_BORDER);
	            table.addCell(cell);
	            

	            document.add(table);
	            document.add(table2);
	            document.close();
	           // writer.close();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
		return returnPath;
	}
}
