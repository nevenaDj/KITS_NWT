package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ItemInPricelistDTO;
import com.example.dto.NotificationDTO;
import com.example.dto.PricelistDTO;
import com.example.model.Item_In_Princelist;
import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.Pricelist;
import com.example.model.User;
import com.example.service.ItemInPricelistService;
import com.example.service.MeetingService;
import com.example.service.NotificationService;
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
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<PricelistDTO> addPricelist(@PathVariable Long id, @RequestBody PricelistDTO itemDTO) {
		Pricelist item = PricelistDTO.getPricelist(itemDTO);

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<PricelistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		// ha mar van 1 egy companyhoz
		item = pricelistService.save(item);
		itemDTO.setId(item.getId());

		return new ResponseEntity<PricelistDTO>(itemDTO, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<PricelistDTO> getPricelists(@PathVariable Long id,Pageable page) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<PricelistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Pricelist item = pricelistService.findOne(id);
		
		if (item==null){
			return new ResponseEntity<PricelistDTO>(HttpStatus.NOT_FOUND);
		} else {
			PricelistDTO itemDTO = new PricelistDTO(item);
			return new ResponseEntity<PricelistDTO>(itemDTO, HttpStatus.OK);
		}
	}
	 
	@RequestMapping(value = "/items", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<ItemInPricelistDTO> addItem(@PathVariable Long id, @RequestBody ItemInPricelistDTO itemDTO) {
		Item_In_Princelist item = ItemInPricelistDTO.getItemInPricelist(itemDTO);

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<ItemInPricelistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		// ha mar van 1 egy companyhoz
		item = itemService.save(item);
		itemDTO.setId(item.getId());

		return new ResponseEntity<ItemInPricelistDTO>(itemDTO, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/items", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<ItemInPricelistDTO> getItemsInPricelist(@PathVariable Long id,Pageable page) {

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<ItemInPricelistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Item_In_Princelist item = itemService.findOne(id);
		
		if (item==null){
			return new ResponseEntity<ItemInPricelistDTO>(HttpStatus.NOT_FOUND);
		} else {
			ItemInPricelistDTO itemDTO = new ItemInPricelistDTO(item);
			return new ResponseEntity<ItemInPricelistDTO>(itemDTO, HttpStatus.OK);
		}
	} 
	
	@RequestMapping(value = "/items/{items_id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id, @PathVariable("items_id")  Long items_id ) { // da li je dobro tako????

		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		Item_In_Princelist item = itemService.findOne(items_id);
		if (item == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			itemService.remove(items_id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

}
