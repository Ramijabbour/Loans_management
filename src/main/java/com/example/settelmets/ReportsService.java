package com.example.settelmets;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.DocumentTemplate.DOCXDOC;
import com.DocumentTemplate.PDFDOC;
import com.DocumentTemplate.XMLDOC;
import com.example.MQ.SettledChaque;

@Service
public class ReportsService {

	
	public void exportDocx(SettledChaque settledChaque) {
		DOCXDOC docsGenerator = new DOCXDOC() ; 
		docsGenerator.CreateRTGSDoc(settledChaque);
	}
	
	public ModelAndView exportXml(SettledChaque settledChaque) {
		XMLDOC xmlGenerator = new XMLDOC(); 
		return xmlGenerator.ViewRTGSDOC(settledChaque);
		
	}
	
	public void excportPDF(SettledChaque settledChaque) {
		PDFDOC pdfGenerator = new PDFDOC(); 
		pdfGenerator.CreateRTGSDoc(settledChaque);
	}
	
}
