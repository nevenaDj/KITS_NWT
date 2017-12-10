package com.example.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.dto.GlitchTypeDTO;
import com.example.model.GlitchType;
import com.example.service.GlitchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "glitcheTypes")
public class GlitchTypeController {
	@Autowired
	GlitchService glitchService;

	@RequestMapping(value = "/glitchTypes", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a new glitch type.", notes = "Returns the glitch type being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = GlitchTypeDTO.class),
			@ApiResponse(code = 500, message = "Failure") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** add a glitch type***/
	public ResponseEntity<GlitchTypeDTO> addGlitchType(@RequestBody GlitchTypeDTO glitchTypeDTO) {
		GlitchType glitchType = GlitchTypeDTO.getGlitchType(glitchTypeDTO);

		glitchType = glitchService.saveGlitchType(glitchType);

		return new ResponseEntity<>(new GlitchTypeDTO(glitchType), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/glitchTypes/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a glitch type.", httpMethod = "DELETE")
	@ApiResponse(code = 404, message = "Not found")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** delete a glitch type ***/
	public ResponseEntity<Void> deleteGlitchType(@PathVariable Long id) {
		GlitchType glitchType = glitchService.findOneGlitchType(id);
		if (glitchType == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		glitchService.removeGlitchType(glitchType.getId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/glitchTypes/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get a glitch type.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchTypeDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** get a glitch type ***/
	public ResponseEntity<GlitchTypeDTO> getGlitchType(@PathVariable Long id) {
		GlitchType glitchType = glitchService.findOneGlitchType(id);

		if (glitchType == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new GlitchTypeDTO(glitchType), HttpStatus.OK);
	}

	@RequestMapping(value = "/glitchTypes", method = RequestMethod.GET, params = {
			"name" })
	@ApiOperation(value = "Find a glitch type by name.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchTypeDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** find glitch type by name ***/
	public ResponseEntity<GlitchTypeDTO> findGlitchTypeByName(@RequestParam String name) {
		GlitchType glitchType = glitchService.findOneGlitchTypeByName(name);
		if (glitchType == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new GlitchTypeDTO(glitchType), HttpStatus.OK);
	}

	@RequestMapping(value = "/glitchTypes", method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of glitch types.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	/*** find all glitch types ***/
	public ResponseEntity<List<GlitchTypeDTO>> findAllGlitchType() {
		List<GlitchType> glitchTypes = glitchService.findAllGlitchType();
		ArrayList<GlitchTypeDTO> glitchTypesDTO = new ArrayList<>();
		for (GlitchType glitchT : glitchTypes) {
			glitchTypesDTO.add(new GlitchTypeDTO(glitchT));
		}
		return new ResponseEntity<>(glitchTypesDTO, HttpStatus.OK);
	}

}
