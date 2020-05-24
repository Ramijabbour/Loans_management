package com.DocumentTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.Loans.Loans;
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

public class OpenLoansReportPDF implements CreateOpenDocTemplate{

	public OpenLoansReportPDF() {
		
	}
	
	 public static Font normal = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 26);
	    public static Font normal2 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);
	    public static Font normal3 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);

	
	@Override
	public String CreateOpenLoanDoc(Loans Loan) {
		
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        normal.setColor(BaseColor.BLACK.darker());
        normal2.setColor(BaseColor.BLACK.brighter());
        normal3.setColor(BaseColor.GRAY.darker());
        Document document = new Document();
        String Path=System.getProperty("user.dir")+"\\";
        String Filename="open_Loan_"+Loan.getId()  ;
        String returnPath = Path+Filename+".pdf" ; 

        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Path+Filename+".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            PdfPCell cell = new PdfPCell();
            Paragraph p = new Paragraph("الجمهورية العربية السورية \nمصرف سورية المركزي\n قسم التسليف \n\n                         قيد فتح سلفة \n\n", normal);
            Paragraph p1 = new Paragraph("\n\n\n");
            Paragraph p2=new Paragraph("السلفة رقم: " + Loan.getLoanNumber() +"   " + "التاريخ : " + Loan.getLoanDate()+"   " +" المصرف  : "+Loan.getBranche().getBank().getBankName()+" - " + Loan.getBranche().getBranchName()+"\n"+
            		" لصالح  : "+Loan.getClient().getClientName()+"\n\n"+
            		" الجهة المدينة  : من حـ/القروض و التسليفات للمصارف"+"\n"+
            		Loan.getBranche().getBank().getBankName()+" - " + Loan.getBranche().getBranchName()+"\n\n"+
            	    "                      " + "عدد السندات : \'"+Loan.getNumberOfVoucher()+"\'"+ "              "+ "\'"+Loan.getTotalAmmount()+"\'"+"\n"+
                    "                           "+"الى : حـ/العمليات المصرفية-الحسابات الجارية"+"\n"+
            	    "                      " + "عدد السندات : \'"+Loan.getNumberOfVoucher()+"\'"+ "              "+ "\'"+Loan.getTotalAmmount()+"\'"+"\n"+
                    "                      "+"____________________________"+"\n"+
                    "                      " + " المجموع : " + Loan.getTotalAmmount() + "                      " + "\'"+Loan.getTotalAmmount()+"\'\n",normal2);
           // Paragraph p3 = new Paragraph(Bank,normal2);

            p.setAlignment(PdfPCell.ALIGN_LEFT);
            cell.addElement(p);
            cell.addElement(p2);
            //cell.addElement(p3);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);
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
