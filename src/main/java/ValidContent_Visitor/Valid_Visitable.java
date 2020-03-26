package ValidContent_Visitor;


import com.example.Banks.Banks;
import org.apache.commons.lang3.StringUtils;

public class Valid_Visitable implements Visitor {
//"[^A-Za-z]"


    @Override
    public boolean visit(Banks Bank) {

        if(StringUtils.isNumeric(Bank.BankCode)==false ||Bank.BankCode.contains("-"))
            return false ;
        if(Bank.BankName.length()>15 || Bank.BankName.matches("[@_!#$%^&*()<>?/\\|}{~:]" ))
        {
            return false;
        }

        if (StringUtils.isNumeric(Bank.FinancialAllocations)==false||Float.valueOf(Bank.FinancialAllocations)<0)
            return false;


        return  true;

    }}