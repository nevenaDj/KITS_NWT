package com.example.controller;

import org.seleniumhq.jetty9.servlet.FilterHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.GlitchDTO;
import com.gargoylesoftware.htmlunit.javascript.host.html.Image;
import com.gargoylesoftware.htmlunit.javascript.host.xml.FormData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FileUploadController {

	  private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	    //Save the uploaded file to this folder
	    private static String UPLOADED_FOLDER = "/Users/sabinomi/Documents";

	    // 3.1.1 Single file upload
	/*    @PostMapping("/api/upload")
	    @ResponseBody
	    public ResponseEntity<?> uploadFile() {

	    	System.out.println("Single file upload!");
	        System.out.println("model "+uploadfile);
	        if (uploadfile.isEmpty()) {
	            return new ResponseEntity("please select a file!", HttpStatus.OK);
	        }

	        try {

	            saveUploadedFiles(Arrays.asList(uploadfile));

	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity("Successfully uploaded - " +
	                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

	    }
*/
	    // 3.1.2 Multiple file upload
	    @RequestMapping(value = "/api/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
	    public ResponseEntity<?> uploadFileMulti( @RequestParam("image") Object image) {

	    	//@RequestParam("image") java.awt.Image image
	        logger.debug("Multiple file upload!");
	        System.out.println("model"+image);
	        // Get file name
	        /*       String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
	                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

	        if (StringUtils.isEmpty(uploadedFileName)) {
	        		System.out.println("please select a file!");
	            return new ResponseEntity("please select a file!", HttpStatus.OK);
	        }

	        try {

	            saveUploadedFiles(Arrays.asList(uploadfiles));

	        } catch (IOException e) {
	        		System.out.println("SBAD_REQUEST");
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        System.out.println("uccessfully uploaded!");
	        return new ResponseEntity("Successfully uploaded - "
	                + uploadedFileName, HttpStatus.OK);
*/
	        return new ResponseEntity("please select a file!", HttpStatus.OK);
	    }
/*
	    // 3.1.3 maps html form to a Model
	    @PostMapping("/api/upload/multi/model")
	    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute FilterHolder model) {

	        System.out.println("Multiple file upload! With UploadModel");

	       // try {
	        		System.out.println("model"+model.getSource());
	            //saveUploadedFiles(Arrays.asList(model));

	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);

	    }
*/
	    //save file
	    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

	        for (MultipartFile file : files) {

	            if (file.isEmpty()) {
	                continue; //next pls
	            }

	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	        }

	    }

}