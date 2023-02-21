package com.helpCenter.Incident.serviceImpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.imageUtil.ImageUtils;
import com.helpCenter.Incident.reposatiory.ImageReposatiory;
import com.helpCenter.Incident.service.ImageService;

@Component
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageUtils imageUtils;
	
	@Autowired
	ImageReposatiory imageReposatiory;
	
	@Override
	public ImageCreation uploadImage(MultipartFile file) throws IOException {
		  ImageCreation image= new ImageCreation();
		  image.setImage(ImageUtils.compressImage(file.getBytes()));
		  return imageReposatiory.save(image);
		
	}
	
	public byte[] downloadImage(int  fileName) {
		Optional<ImageCreation> imageData = imageReposatiory.findById(fileName);
		return ImageUtils.decompressImage(imageData.get().getImage());
	}
	
	

}
