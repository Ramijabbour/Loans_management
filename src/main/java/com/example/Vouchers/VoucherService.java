package com.example.Vouchers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clients.Clients;

@Service
public class VoucherService {

	@Autowired 
	VouchersRepository voucherRepository ;
	
	public void addVoucher(Vouchers v)
	{
		voucherRepository.save(v);
	}
	
	public List<Vouchers> GetAllVouchers ()
	{
		return voucherRepository.findAll();
	}
	
	public void deleteVoucher(int id) {
		voucherRepository.deleteById(id);
	}
	
	public Vouchers GetVoucherById(int id)
	{
		List<Vouchers> allVoucher=voucherRepository.findAll();
		if(allVoucher.isEmpty())
		{
			System.out.println("empty Voucher List ");
			return null ;
		}
		for (Vouchers voucher : allVoucher)
		{
			if(voucher.getVoucherID()==id)
				return voucher;
		}
		System.out.println("requested voucher not found ");
		return null;
		
	}

	public void updateVoucher(Vouchers voucher) {
		try {
			if(voucherRepository.findById(voucher.getVoucherID()) != null) {
					voucherRepository.save(voucher); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at voucher Service / Update voucher -- call for null voucher ");
		}
	}
	
	/*public int getLoanVocher(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		int  NumberofloanVoucher =0;
		for(Vouchers voucher : allVoucher )
		{	
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getLoanID() == id)
					NumberofloanVoucher++;	
			}
		}
		return NumberofloanVoucher;
	}
	*/
	
	public List<Vouchers> getVoucherForThisLoan(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		ArrayList <Vouchers> LoanVoucher = new ArrayList<Vouchers>(); 
		for(Vouchers voucher : allVoucher )
		{
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getLoanID()==id)
					LoanVoucher.add(voucher);
			}
		}
		return LoanVoucher;
	}
	
	
	public boolean AllVoucherPaid(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		ArrayList <Vouchers> LoanVoucher = new ArrayList<Vouchers>(); 
		for(Vouchers voucher : allVoucher )
		{
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getLoanID()==id)
					if(!voucher.getStatus().equalsIgnoreCase("paid"))
					  return false ;
			}
		}
		return true;
	}
	
}
