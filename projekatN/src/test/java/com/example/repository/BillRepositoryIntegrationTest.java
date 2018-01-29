package com.example.repository;

import static com.example.constants.BillConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Bill;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class BillRepositoryIntegrationTest {

	@Autowired
	BillRepository billRepository;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void testFindByDate() throws ParseException {
		Date d = formatter.parse("2017-12-03");
		Date d1 = formatter.parse("2017-12-01");
		Date d2 = formatter.parse("2017-12-10");
		Bill bill = billRepository.findByDate(d1, d2);

		assertNotNull(bill);
		assertEquals(COMPANY_ID, bill.getCompany().getId());
		assertEquals(PRICE, bill.getPrice(), DELTA);
		assertEquals(d, bill.getDate());
		assertEquals(APPROVED, bill.isApproved());

	}
}
