package com.example.Loans;

        import org.springframework.data.domain.Pageable;
        import org.springframework.data.domain.Slice;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.PagingAndSortingRepository;
        import org.springframework.stereotype.Repository;



@Repository
public interface LoansRepository extends JpaRepository<Loans,Integer>,PagingAndSortingRepository<Loans,Integer>{


    //@Query("select loans from Loans loans where loans.loanNumber = :#{#loanNumber}")
    public Slice<Loans> findByLoanNumber(String loanNumber, Pageable pageable);
   
    @Query("select count(*) from Loans")
    public int getLoansCount(); 

}
