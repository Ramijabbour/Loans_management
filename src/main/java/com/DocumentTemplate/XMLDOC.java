package com.DocumentTemplate;

import com.example.MQ.SettledChaque;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XMLDOC implements ViewDocTemplate {
    @Override
    public ModelAndView ViewRTGSDOC(SettledChaque settledChaque) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String viewName = "greeting";

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("Bank",settledChaque.FirstBank);
        model.put("Code",settledChaque.FirstBankSW);
        model.put("Bank2",settledChaque.SecondBank);
        model.put("Code2",settledChaque.SecondBankSW);
        model.put("Amount",settledChaque.Amount);
        model.put("Date",dateFormat.format(date));



        return new ModelAndView(viewName , model);
    }
}
