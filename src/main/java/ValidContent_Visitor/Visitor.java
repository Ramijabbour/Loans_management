package ValidContent_Visitor;

import com.example.Allocations.Allocations;
import com.example.Banks.Banks;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.TT.tt;

public interface Visitor {

    public boolean visit(Banks Bank);
    public boolean visit( tt t );
    public boolean visit( Allocations allocations );
    public boolean visit( FinanceType financeType );
    public boolean visit( Clients clients );




}
