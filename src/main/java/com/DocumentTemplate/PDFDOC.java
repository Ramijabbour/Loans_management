package com.DocumentTemplate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.settelmets.Models.SettledChaque;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PDFDOC implements CreateDocTemplate  {

    public static Font normal = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 26);
    public static Font normal2 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);
    public static Font normal3 = FontFactory.getFont(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\new\\fonts\\sourcesanspro\\arabtype.ttf", BaseFont.IDENTITY_H, 18);

    @Override
    public String CreateRTGSDoc(SettledChaque settledChaque) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String Path=System.getProperty("user.dir")+"//";
        String Filename=settledChaque.getFirstBankSW()+dateFormat.format(date);
        normal.setColor(BaseColor.BLACK.darker());
        normal2.setColor(BaseColor.BLACK.brighter());
        normal3.setColor(BaseColor.GRAY.darker());
        Document document = new Document();
        String returnPath = Path+Filename+".pdf" ; 
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Path+Filename+".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            PdfPCell cell = new PdfPCell();
            Paragraph p = new Paragraph("الجمهورية العربية السورية \nمصرف سورية المركزي\nنظام التسوية اللحظية\n\n                       اشعار دفع مستحقات مادية\n\n", normal);
            Paragraph p1 = new Paragraph("\n\n\n");
            Paragraph p2=new Paragraph("الى البنك: " + settledChaque.getFirstBank()+"\n"+"رمز الفرع: "+settledChaque.getFirstBankSW()+"\n"+"\n"+"" +
                    "استنادا لنتيجة نظام التسوية اللحظية الخاص بمصرف سورية المركزي توجب عليكم دفع المستحقات المالية المفروضة عليكم و قدرها("+settledChaque.getAmount()+")"+"ل.س"+
                    "\n\n"+"علما انه سيتم تسديد هذه المستحقات الى:"+"\n"+"البنك: "+settledChaque.getSecondBank()+"\n"+"رمز الفرع: "+settledChaque.getSecondBankSW()+"\n\n\n\n\nدمشق في:"+
                    "\n"+dateFormat.format(date),normal2);
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
        return returnPath ; 
    }
    }




