package com.example.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BillDTO;
import com.example.model.Apartment;
import com.example.model.Bill;
import com.example.model.Glitch;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.BillService;
import com.example.service.GlitchService;
import com.example.service.UserService;

@RestController
public class BillController {

	@Autowired
	ApartmentService apService;
	@Autowired
	GlitchService glitchService;
	@Autowired
	BillService billService;
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.POST, consumes = "application/json") /// value
																																			/// ??????
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<BillDTO> addBill(@PathVariable("ap_id") Long apartmentId,
			@PathVariable("glitch_id") Long glitchId, @RequestBody BillDTO billDTO, HttpServletRequest request) {
		Bill bill = BillDTO.getBill(billDTO);

		Apartment apartment = apService.findOne(apartmentId);
		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);
		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User company = userService.findByUsername(username);
		bill.setCompany(company);

		bill = billService.save(bill);
		billDTO.setId(bill.getId());

		glitch.setBill(bill);
		glitchService.save(glitch);

		return new ResponseEntity<>(billDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteBill(@PathVariable("ap_id") Long apartmentId,
			@PathVariable("glitch_id") Long glitchId) {
		Apartment apartment = apService.findOne(apartmentId);
		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);
		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Bill bill = billService.findByGlitch(glitchId);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			billService.remove(bill.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteBill(@PathVariable("id") Long id) {

		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			billService.remove(bill.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<BillDTO> findBill(@PathVariable("id") Long id) {

		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<BillDTO> findBillByGlitch(@PathVariable("ap_id") Long apartmentId,
			@PathVariable("glitch_id") Long glitchId) {
		Apartment apartment = apService.findOne(apartmentId);
		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);
		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Bill bill = billService.findByGlitch(glitchId);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills", method = RequestMethod.GET, produces = "application/json", params = { "start",
			"end" })
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> findBillByDate(@RequestParam("start") Date start, @RequestParam("end") Date end) {

		Bill bill = billService.findByDate(start, end);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> setBillApprove(@PathVariable("ap_id") Long apartmentId,
			@PathVariable("glitch_id") Long glitchId) {

		Apartment apartment = apService.findOne(apartmentId);
		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);
		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Bill bill = billService.findByGlitch(glitchId);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			bill.setApproved(true);
			billService.save(bill); // update good????
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/{id}", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> setBillApprove(@PathVariable("id") Long id) {

		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			bill.setApproved(true);
			billService.save(bill); // update good????
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}
}
