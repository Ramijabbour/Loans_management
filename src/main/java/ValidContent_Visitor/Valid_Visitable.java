package ValidContent_Visitor;


import com.example.Allocations.Allocations;
import com.example.Banks.Banks;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.Loans.Loans;
import org.apache.commons.lang3.StringUtils;
//import com.example.TT.tt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Valid_Visitable implements Visitor {
//"[^A-Za-z]"


    @Override
    public boolean visit(Banks Bank) {
       // if (Bank.BankCode.charAt(0) != '#' && StringUtils.isNumeric(Bank.BankCode) == false || Bank.BankCode.contains("-") || Bank.BankCode.length() > 7)
         //   return false;
        if (Bank.BankName.length() > 20 || !Bank.BankName.matches("[@_!#$%^&*()<>?/\\|}{~:]"))
            return false;
        if (StringUtils.isNumeric(Bank.FinancialAllocations) == false || Float.valueOf(Bank.FinancialAllocations) < 0.0)
            return false;
        //if (Bank.BranchName.length() > 20)
          //  return false;
        return true;
    }

    public boolean visit(Allocations allocations) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (StringUtils.isNumeric(allocations.AllocationAmmount) == false || Float.valueOf(allocations.AllocationAmmount) < 0)
            return false;
        try {
            Date date = sdf.parse(allocations.AllocationDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean visit(FinanceType financeType) {
        if (!financeType.TypeName.matches("[@_!#$%^&*()<>?/\\|}{~:]"))
            return false;
        if (financeType.FundintRate> 100.0 && financeType.FundintRate < 0.0)
            return false;
        if (financeType.FundintRate <= 100.0 && financeType.FundintRate >= 0.0)
            financeType.FundintRate = (financeType.FundintRate / 100.0);
        if (Float.valueOf(financeType.lenght) < 0)
            return false;
        if (financeType.funded_propse.length() < 130)
            return false;
        return true;
    }

    public boolean visit(Clients clients) {
        if (StringUtils.isAlphaSpace(clients.ClientName) == false)
            return false;
        clients.identity_number = clients.identity_number.replaceAll(" ", "");
        if (StringUtils.isNumeric(clients.identity_number)) {
            if (clients.identity_number.length() != 11)
                return false;
        }
        if (StringUtils.isNumeric(clients.identity_number) == false)
            return false;
        return true;
    }

//    public boolean visit(tt t) {
//        //t.Total = t.Total.replaceAll(" ", "");
//        if (StringUtils.isAlphaSpace(t.Total)) {
//            System.out.println("ggg");
//
//            if (t.Total.length() == 11)
//                System.out.println("ggg");
//        }
//        return true;
//    }

    public boolean visit(Loans loans)
    {
        if(Float.valueOf(loans.TotalAmmount)<0)
            return false ;

        if(StringUtils.isAlphaSpace(loans.TotalAmmountAsString) == false)
            return  false;
//        if (loans.LoanDate)



        return true ;
    }
}