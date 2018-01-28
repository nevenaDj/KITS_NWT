package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.GlitchTypeDTO;
import com.example.dto.ItemInPricelistDTO;
import com.example.dto.PricelistDTO;
import com.example.dto.UserDTO;
import com.example.model.GlitchType;
import com.example.model.ItemInPrincelist;
import com.example.model.Pricelist;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.GlitchService;
import com.example.service.ItemInPricelistService;
import com.example.service.PricelistService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/company/{id}/pricelist")
@RestController
@Api(value = "priceList")
public class PricelistController {
	@Autowired
	UserService userService;
	@Autowired
	PricelistService pricelistService;
	@Autowired
	ItemInPricelistService itemService;
	@Autowired
	GlitchService glitchService;
	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Create the priceList.", notes = "Returns the pricelist being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = PricelistDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<PricelistDTO> getPricelists(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long id,
			@ApiParam(value = "The PricelistDTO object", required = true) @RequestBody PricelistDTO itemDTO) {
		
		Pricelist item = PricelistDTO.getPricelist(itemDTO);

		
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// ha mar van 1 egy companyhoz
		item = pricelistService.save(item);
		itemDTO.setId(item.getId());

		return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get a priceList.", notes = "Returns the price list being saved.", httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = PricelistDTO.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PricelistDTO> getPricelists(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long id, Pageable page) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Pricelist item = pricelistService.findOneByCompany(id);

		if (item == null) {
			PricelistDTO pricelistDTO = new PricelistDTO();
			pricelistDTO.setCompany(new UserDTO(user));
			pricelistDTO.setDateUpdate(new Date());			
			return getPricelists(id, pricelistDTO);
		} else {
			PricelistDTO itemDTO = new PricelistDTO(item);
			return new ResponseEntity<>(itemDTO, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Update a priceList.", notes = "Returns the pricelist being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = PricelistDTO.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@RequestMapping(value = "/types/{type_id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Void> updatePricelists(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long id,
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long type_id) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pricelist pricelist = pricelistService.findOneByCompany(id);
		if (pricelist!=null){
			
			pricelist.setDateUpdate(new Date());
			
			GlitchType type= glitchService.findOneGlitchType(type_id);
			pricelist.setType(type);
			pricelistService.save(pricelist);
			return new ResponseEntity<>( HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		
	}

	@RequestMapping(value = "/items", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create new item in pricelist.", notes = "Returns the item being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = ItemInPricelistDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<ItemInPricelistDTO> addItem(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long id,
			@ApiParam(value = "The ItemInPricelistDTO object", required = true) @RequestBody ItemInPricelistDTO itemDTO) {
		ItemInPrincelist item = ItemInPricelistDTO.getItemInPricelist(itemDTO);
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Pricelist pricelist = pricelistService.findOneByCompany(id);
		item.setPricelist(pricelist);
		item = itemService.save(item);
		itemDTO.setId(item.getId());

		return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get all item from the pricelist.", notes = "Returns the pricelist.", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = PricelistDTO.class),
			@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 400, message = "Bad request") })
	public ResponseEntity<PricelistDTO> getItemsInPricelist(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable Long id, Pageable page,
			HttpServletRequest request) {

		User company = userService.findOne(id);

		if (company == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Pricelist pricelist = pricelistService.findOneByCompany(company.getId());
		
		
		if (pricelist == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			pricelist.setDateUpdate(new Date());
			pricelistService.save(pricelist);
			return new ResponseEntity<>(new PricelistDTO(pricelist), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/items/{items_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete item from pricelist.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteItem(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the item in the pricelist.", required = true) @PathVariable("items_id") Long itemId) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ItemInPrincelist item = itemService.findOne(itemId);
		if (item == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			itemService.remove(itemId);
			Pricelist pricelist = pricelistService.findOneByCompany(id);
			pricelist.setDateUpdate(new Date());
			pricelistService.save(pricelist);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/items/{items_id}", method = RequestMethod.PUT, consumes = "application/json")
	@ApiOperation(value = "Update item in the pricelist", httpMethod = "PUT", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> updateItem(
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the item in the pricelist.", required = true) @PathVariable("items_id") Long itemId,
			@ApiParam(value = "The ItemInPricelistDTO object", required = true) @RequestBody ItemInPricelistDTO itemDTO) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ItemInPrincelist item = itemService.findOne(itemId);
		item.setPrice(itemDTO.getPrice());
		item.setNameOfType(itemDTO.getNameOfType());
		itemService.save(item);
		
		
		Pricelist pricelist = pricelistService.findOneByCompany(id);
		pricelist.setDateUpdate(new Date());
		pricelistService.save(pricelist);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
