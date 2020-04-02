package com.example.Vouchers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public int getLoanVocher(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		int  NumberofloanVoucher =0;
		for(Vouchers voucher : allVoucher )
		{	
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getLoanID() == id)
				{
					System.out.println("loan id in voucher "+ voucher.getLoan().getLoanID()+" and loan id " +id);
					NumberofloanVoucher++;
					System.out.println("done");
				}
			}
		}
		return NumberofloanVoucher;
	}
	
}
