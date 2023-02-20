package com.helpCenter.Incident.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.service.ImageService;
import com.helpCenter.Incident.serviceImpl.ImageServiceImpl;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@Autowired
	ImageServiceImpl imageServiceImpl;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/")
	public void uploadImage(@RequestParam("productImage") List<MultipartFile> file) throws IOException {

		for (MultipartFile multipartFile : file) {
			imageService.uploadImage(multipartFile);
		}

	}
	

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable int id) {
		byte[] image = imageServiceImpl.downloadImage(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}
}
