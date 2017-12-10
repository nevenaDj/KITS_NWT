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
import com.example.dto.GlitchDTO;
import com.example.model.Apartment;
import com.example.model.Bill;
import com.example.model.Glitch;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.BillService;
import com.example.service.GlitchService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "bill")
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

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.POST, consumes = "application/json", produces = "application/json") /// value
	@ApiOperation(value = "Create a bill for the glitch.", notes = "Returns the bill being saved.",
		httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = BillDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })																														/// ??????
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<BillDTO> addBill(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("ap_id") Long apartmentId,
			@ApiParam(value = "The ID of the bill.", required = true) @PathVariable("glitch_id") Long glitchId,
			@ApiParam(value = "The BillDTO Object.", required = true) @RequestBody BillDTO billDTO, 
			HttpServletRequest request) {
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
	@ApiOperation(value = "Delete the bill.", notes = "Returns the bill being saved.",
		httpMethod = "DELETE", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteBill(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("ap_id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId) {
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
	@ApiOperation(value = "Delete the bill.", notes = "Returns the bill being saved.",
		httpMethod = "DELETE", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteBill(@ApiParam(value = "The ID of the bill.", required = true) @PathVariable("id") Long id) {

		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			billService.remove(bill.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get the bill by id.", notes = "Returns the bill being saved.",
		httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response=BillDTO.class),
		@ApiResponse(code = 404, message = "Not found")})
	public ResponseEntity<BillDTO> findBill(
			@ApiParam(value = "The ID of the bill.", required = true) @PathVariable("id") Long id) {

		Bill bill = billService.findOne(id);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	
	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get the bill by id.", notes = "Returns the bill being saved.",
		httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response=BillDTO.class),
		@ApiResponse(code = 404, message = "Not found")})
	public ResponseEntity<BillDTO> findBillByGlitch(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("ap_id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId) {
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
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills", method = RequestMethod.GET, produces = "application/json", params = { "start",
			"end" })
	@ApiOperation(value = "Get the bill by id.", notes = "Returns the bill being saved.",
		httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response=BillDTO.class),
		@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> findBillByDate(
			@ApiParam(name="start", value = "Period which starts that day", required = true) @RequestParam("start") Date start,
			@ApiParam(name="end", value = "Period which ends that day", required = true)@RequestParam("end") Date end) {

		Bill bill = billService.findByDate(start, end);
		if (bill == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/apartments/{ap_id}/glitches/{glitch_id}/bill", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Approve the bill.", notes = "Returns the bill being saved.",
		httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response=BillDTO.class),
		@ApiResponse(code = 400, message = "Bad request")})
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> setBillApprove(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("ap_id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId) {

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
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			bill.setApproved(true);
			billService.save(bill); // update good????
			BillDTO billDTO = new BillDTO(bill);
			return new ResponseEntity<>(billDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Approve the bill.", notes = "Returns the bill being saved.",
		httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response=BillDTO.class),
		@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<BillDTO> setBillApprove(
			@ApiParam(value = "The ID of the bill.", required = true) @PathVariable("id") Long id) {

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
