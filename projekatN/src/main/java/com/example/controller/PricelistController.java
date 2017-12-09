package com.example.controller;


import java.util.ArrayList;
import java.util.List;

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

import com.example.dto.GlitchDTO;
import com.example.dto.ItemInPricelistDTO;
import com.example.dto.PricelistDTO;
import com.example.dto.UserDTO;
import com.example.model.Glitch;
import com.example.model.GlitchType;
import com.example.model.ItemInPrincelist;
import com.example.model.Pricelist;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.GlitchService;
import com.example.service.ItemInPricelistService;
import com.example.service.PricelistService;
import com.example.service.UserService;


@RequestMapping(value = "/company/{id}/pricelist")
@RestController
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
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<PricelistDTO> addPricelist(@PathVariable Long id, @RequestBody PricelistDTO itemDTO) {
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
	
	
	@RequestMapping(method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<PricelistDTO> getPricelists(@PathVariable Long id,Pageable page) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pricelist item = pricelistService.findOne(id);
		
		if (item==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			PricelistDTO itemDTO = new PricelistDTO(item);
			return new ResponseEntity<>(itemDTO, HttpStatus.OK);
		}
	}
	 
	@RequestMapping(value = "/items", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<ItemInPricelistDTO> addItem(@PathVariable Long id, @RequestBody ItemInPricelistDTO itemDTO) {
		ItemInPrincelist item = ItemInPricelistDTO.getItemInPricelist(itemDTO);

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		// ha mar van 1 egy companyhoz
		item = itemService.save(item);
		itemDTO.setId(item.getId());

		return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/items", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<PricelistDTO> getItemsInPricelist(Pageable page, HttpServletRequest request) {
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User company = userService.findByUsername(username);	
		
		if (company == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pricelist pricelist = pricelistService.findOneByCompany(company.getId());
		
		if (pricelist==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new PricelistDTO(pricelist), HttpStatus.OK);
		}
	} 
	
	@RequestMapping(value = "/items/{items_id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id, @PathVariable("items_id")  Long itemId ) { // da li je dobro tako????

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ItemInPrincelist item = itemService.findOne(itemId);
		if (item == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			itemService.remove(itemId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/items/{items_id}", method = RequestMethod.PUT, consumes="application/json")
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> updateItem(@PathVariable("id") Long id, @PathVariable("items_id")  Long itemId, @RequestBody ItemInPricelistDTO itemDTO ) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ItemInPrincelist item = itemService.findOne(itemId);
		item= ItemInPricelistDTO.getItemInPricelist(itemDTO);
		itemService.save(item);
		///????
		if (item == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			itemService.remove(itemId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/glitches/{id}/company", method = RequestMethod.GET, produces= "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<List<PricelistDTO>> findPricelistsByGlitchType(@PathVariable Long id) {

		Glitch glitch = glitchService.findOne(id);
		if (glitch== null) {
			return new ResponseEntity<List<PricelistDTO>>(HttpStatus.BAD_REQUEST);
		}
		
		List<Pricelist> pricelist = pricelistService.findbyGlitchType(glitch.getType().getId());
		List<PricelistDTO> pricelistDTO = new ArrayList<PricelistDTO>();
		for (Pricelist p : pricelist){
			PricelistDTO pDTO= new PricelistDTO(p);
			pDTO.setCompany(new UserDTO(p.getCompany()));
			pricelistDTO.add(pDTO);
		}
		
		return new ResponseEntity<List<PricelistDTO>>(pricelistDTO, HttpStatus.OK);		
	}


	@RequestMapping(value = "/glitches/{id}/company", method = RequestMethod.PUT, consumes= "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<GlitchDTO> chooseCompany(@PathVariable Long id, @RequestBody UserDTO companyDTO) {

		Glitch glitch = glitchService.findOne(id);
		if (glitch== null) {
			return new ResponseEntity<GlitchDTO>(HttpStatus.BAD_REQUEST);
		}
		
		User company= userService.findOne(companyDTO.getId());
		glitch.setCompany(company);
		glitch= glitchService.save(glitch);
		
		return new ResponseEntity<GlitchDTO>(new GlitchDTO(glitch), HttpStatus.OK);		
	}

}
