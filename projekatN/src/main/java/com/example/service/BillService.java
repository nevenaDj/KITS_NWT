package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Bill;
import com.example.repository.BillRepository;

@Service
public class BillService {
	@Autowired
	BillRepository billRepository;

	public Bill findOne(Long id) {
		return billRepository.findOne(id);
	}
	
	public Bill findByGlitch(Long id) {
		return billRepository.findByGlitch(id);
	}
	
	public Bill findByDate(Date start, Date end) {
		return billRepository.findByDate(start, end);
	}


	public Page<Bill> findAll(Pageable page) {
		return billRepository.findAll(page);
	}

	public Bill save(Bill bill) {
		return billRepository.save(bill);
	}

	public void remove(Long id) {
		billRepository.delete(id);
	}

	public Page<Bill> findAllByCompnany(Pageable page, Long id) {
		return billRepository.findAllByCompnany(id, page);
	}

	public Long getCountOfBill(Long id) {
		return billRepository.countByCompany(id);
	}
}
