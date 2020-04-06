package com.example.settelmets;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.DocumentTemplate.DOCXDOC;
import com.DocumentTemplate.PDFDOC;
import com.DocumentTemplate.XMLDOC;
import com.example.MQ.SettledChaque;

@Service
public class ReportsService {

	
	public String exportDocx(SettledChaque settledChaque) {
		DOCXDOC docsGenerator = new DOCXDOC() ; 
		String downloadPath = docsGenerator.CreateRTGSDoc(settledChaque);
		return downloadPath ; 
	}
	
	public ModelAndView exportXml(SettledChaque settledChaque) {
		XMLDOC xmlGenerator = new XMLDOC(); 
		return xmlGenerator.ViewRTGSDOC(settledChaque);
		
	}
	
	public String excportPDF(SettledChaque settledChaque) {
		PDFDOC pdfGenerator = new PDFDOC(); 
		String downloadPath =  pdfGenerator.CreateRTGSDoc(settledChaque);
		return downloadPath ; 
	}
	
}
