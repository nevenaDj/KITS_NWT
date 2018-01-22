package com.example.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Bill;
import com.example.model.ItemInBill;


public interface ItemInBillRepository extends JpaRepository<ItemInBill, Long>  {

		
	

}
