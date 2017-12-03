package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BillDTO;
import com.example.dto.DateDTO;
import com.example.dto.NotificationDTO;
import com.example.model.Apartment;
import com.example.model.Bill;
import com.example.model.Building;
import com.example.model.Glitch;
import com.example.model.Notification;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.BillService;
import com.example.service.BuildingService;
import com.example.service.GlitchService;
import com.example.service.NotificationService;
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
	
	@RequestMapping(value ="/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.POST, consumes = "application/json") ///value ??????
	//aut -> company
	public ResponseEntity<BillDTO> addBill(@PathVariable("ap_id") Long apartments_id, @PathVariable("glitch_id") Long glitch_id, @RequestBody BillDTO billDTO, 
			HttpServletRequest request) {
		Bill bill = BillDTO.getBill(billDTO);
		
		Apartment apartment = apService.findOne(apartments_id);
		if (apartment == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Glitch glitch = glitchService.findOne(glitch_id);
		if (glitch == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User company = userService.findByUsername(username);	
		bill.setCompany(company);
		
		bill.setGlitch(glitch);
		bill = billService.save(bill);
		billDTO.setId(bill.getId());

		return new ResponseEntity<BillDTO>(billDTO, HttpStatus.CREATED);
	}
		
	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBill(@PathVariable("ap_id") Long apartments_id, @PathVariable("glitch_id") Long glitch_id) { 
		Apartment apartment = apService.findOne(apartments_id);
		if (apartment == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		Glitch glitch = glitchService.findOne(glitch_id);
		if (glitch == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		
		Bill bill = billService.findByGlitch(glitch_id);
		if (bill == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			billService.remove(bill.getId());
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBill(@PathVariable("id") Long id) { 
		
		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			billService.remove(bill.getId());
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, produces="application/json" )
	public ResponseEntity<BillDTO> getBill(@PathVariable("id") Long id) { 
		
		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<BillDTO> getBillByGlitch(@PathVariable("ap_id") Long apartments_id, @PathVariable("glitch_id") Long glitch_id) { 
		Apartment apartment = apService.findOne(apartments_id);
		if (apartment == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Glitch glitch = glitchService.findOne(glitch_id);
		if (glitch == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		
		Bill bill = billService.findByGlitch(glitch_id);
		if (bill == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bills", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<BillDTO> getBillByDate(@RequestBody DateDTO dateDTO) { 
			
		Bill bill = billService.findByDate(dateDTO);
		if (bill == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<BillDTO> setBillApprove(@PathVariable("ap_id") Long apartments_id, @PathVariable("glitch_id") Long glitch_id) { 
			
		Apartment apartment = apService.findOne(apartments_id);
		if (apartment == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Glitch glitch = glitchService.findOne(glitch_id);
		if (glitch == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		
		
		Bill bill = billService.findByGlitch(glitch_id);
		if (bill == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.NOT_FOUND);
		} else {
			bill.setApproved(true);
			billService.save(bill); //update good????
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<BillDTO> setBillApprove(@PathVariable("id") Long id) { 
			
		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<BillDTO>(HttpStatus.NOT_FOUND);
		} else {
			bill.setApproved(true);
			billService.save(bill); //update good????
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
		}
	}
}
