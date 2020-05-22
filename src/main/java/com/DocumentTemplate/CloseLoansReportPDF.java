package com.DocumentTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import  java.util.List;

public class CloseLoansReportPDF implements CreateCloseDocTemplate{

	public CloseLoansReportPDF() {
		
	}
	
	 public static Font normal = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 26);
	    public static Font normal2 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);
	    public static Font normal3 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);

	@Override
	public String CreateCloseLoanDoc( Vouchers voucher,List<Vouchers> allvouchers)  
	 {
		Loans Loan = voucher.getLoan();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        normal.setColor(BaseColor.BLACK.darker());
        normal2.setColor(BaseColor.BLACK.brighter());
        normal3.setColor(BaseColor.GRAY.darker());
        Document document = new Document();
        String Path=System.getProperty("user.dir")+"//";
        String Filename="close_Loan_"+Loan.getId()+dateFormat.format(date)  ;
        String returnPath = Path+Filename+".pdf" ; 

        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Path+Filename+".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            PdfPCell cell = new PdfPCell();
            Paragraph p = new Paragraph("الجمهورية العربية السورية \nمصرف سورية المركزي\n قسم التسليف \n\n                        اشعار إغلاق \n\n", normal);
            Paragraph p1 = new Paragraph("\n\n\n");
            Paragraph p2=new Paragraph( "                " +"المصرف : " +  Loan.getBranche().getBank().getBankName() + " - " + Loan.getBranche().getBranchName()  + "                " + "- "+  Loan.getFinanceType() +" -"+ "                " +Loan.getPurpose()+ "\n"+
            		"\n"+
            	    "يرجى من قسم الحسابات الجارية بيان إمكانية إغلاق السلفة التالية : "+"\n\n\n\n",normal2);
            
           // Paragraph p3 = new Paragraph(Bank,normal2);
            
            PdfPTable table2 = new PdfPTable(6);
            table2.setWidthPercentage(90f);
            table2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            PdfPCell cell00 = new PdfPCell();
            Paragraph r00 = new Paragraph("أجل السلفة", normal3);
            r00.setAlignment(PdfPCell.ALIGN_LEFT);
            cell00.addElement(r00);
            
            cell00.setPaddingTop(1);
            cell00.setPaddingBottom(7);
            cell00.setPaddingRight(5);
            cell00.setPaddingLeft(5);

            
            PdfPCell cell01 = new PdfPCell();
            Paragraph r01 = new Paragraph("الاعتماد المفتوح مجددا", normal3);
            r01.setAlignment(PdfPCell.ALIGN_LEFT);
            cell01.addElement(r01);
            
            cell01.setPaddingTop(1);
            cell01.setPaddingBottom(7);
            cell01.setPaddingRight(5);
            cell01.setPaddingLeft(5);
            
            PdfPCell cell02 = new PdfPCell();
            Paragraph r02 = new Paragraph("التنزيلات من الاعتماد المفتوح", normal3);
            r02.setAlignment(PdfPCell.ALIGN_LEFT);
            cell02.addElement(r02);
            
            cell02.setPaddingTop(1);
            cell02.setPaddingBottom(7);
            cell02.setPaddingRight(5);
            cell02.setPaddingLeft(5);
             
            
            PdfPCell cell03 = new PdfPCell();
            Paragraph r03 = new Paragraph("االاعتماد المفتوح سابقا", normal3);
            r03.setAlignment(PdfPCell.ALIGN_LEFT);
            cell03.addElement(r03);
            
            cell03.setPaddingTop(1);
            cell03.setPaddingBottom(7);
            cell03.setPaddingRight(5);
            cell03.setPaddingLeft(5);
            
            
            
            
            PdfPCell cell04 = new PdfPCell();
            Paragraph r04 = new Paragraph("رقم عقد الرهن", normal3);
            r04.setAlignment(PdfPCell.ALIGN_LEFT);
            cell04.addElement(r04);
            
            cell04.setPaddingTop(1);
            cell04.setPaddingBottom(7);
            cell04.setPaddingRight(5);
            cell04.setPaddingLeft(5);
            
            
            PdfPCell cell05 = new PdfPCell();
            Paragraph r05 = new Paragraph("المستفيد" , normal3);
            r05.setAlignment(PdfPCell.ALIGN_LEFT);
            cell05.addElement(r05);
            
            cell05.setPaddingTop(1);
            cell05.setPaddingBottom(7);
            cell05.setPaddingRight(5);
            cell05.setPaddingLeft(5);
            

            PdfPCell cell10 = new PdfPCell();
            Paragraph r10 = new Paragraph(Loan.getLoanDate(), normal3); // اجل السلفة
            r10.setAlignment(PdfPCell.ALIGN_LEFT);
            cell10.addElement(r10);
            
            cell10.setPaddingTop(1);
            cell10.setPaddingBottom(7);
            cell10.setPaddingRight(5);
            cell10.setPaddingLeft(5);

            int x = 0 ;
			
			for(Vouchers v : allvouchers)
			{
				if(!v.getStatus().equalsIgnoreCase("paid"))
				{
					 x += Integer.parseInt(v.getVoucherAmmount());
				}
			}
            PdfPCell cell11 = new PdfPCell();
            Paragraph r11 = new Paragraph(""+x, normal3); // الاعتماد المفتوح مجددا
            r11.setAlignment(PdfPCell.ALIGN_LEFT);
            cell11.addElement(r11);
            
            cell11.setPaddingTop(1);
            cell11.setPaddingBottom(7);
            cell11.setPaddingRight(5);
            cell11.setPaddingLeft(5);
           
            PdfPCell cell12 = new PdfPCell();
            Paragraph r12 = new Paragraph(voucher.getVoucherAmmount(), normal3); // التنزيلات من اعتماد مفتوح
            r12.setAlignment(PdfPCell.ALIGN_LEFT);
            cell12.addElement(r12);
            
            cell12.setPaddingTop(1);
            cell12.setPaddingBottom(7);
            cell12.setPaddingRight(5);
            cell12.setPaddingLeft(5);
             
            
            PdfPCell cell13 = new PdfPCell();
            Paragraph r13 = new Paragraph(Loan.getTotalAmmount(), normal3);// الاعتماد المفتوح سابقا
            r13.setAlignment(PdfPCell.ALIGN_LEFT);
            cell13.addElement(r13);
            
            cell13.setPaddingTop(1);
            cell13.setPaddingBottom(7);
            cell13.setPaddingRight(5);
            cell13.setPaddingLeft(5);
            
            
            
            
            PdfPCell cell14 = new PdfPCell();
            Paragraph r14 = new Paragraph(""+Loan.getName(), normal3); // رقم الرهن
            r14.setAlignment(PdfPCell.ALIGN_LEFT);
            cell14.addElement(r14);
            
            cell14.setPaddingTop(1);
            cell14.setPaddingBottom(7);
            cell14.setPaddingRight(5);
            cell14.setPaddingLeft(5);
            
            
            PdfPCell cell15 = new PdfPCell();
            Paragraph r15 = new Paragraph(Loan.getClient().getClientName(), normal3); // المستفيد
            r15.setAlignment(PdfPCell.ALIGN_LEFT);
            cell15.addElement(r15);
            
            cell15.setPaddingTop(1);
            cell15.setPaddingBottom(7);
            cell15.setPaddingRight(5);
            cell15.setPaddingLeft(5);
            
            
            
            
            
            
            
            table2.addCell(cell05);
            table2.addCell(cell04);
            table2.addCell(cell03);
            table2.addCell(cell02);
            table2.addCell(cell01);
            table2.addCell(cell00);
            table2.addCell(cell15);
            table2.addCell(cell14);
            table2.addCell(cell13);
            table2.addCell(cell12);
            table2.addCell(cell11);
            table2.addCell(cell10);
         
             
            

            p.setAlignment(PdfPCell.ALIGN_LEFT);
            cell.addElement(p);
            cell.addElement(p2);
            //cell.addElement(p3);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);
            document.add(table2);
            //document.add(p);
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
