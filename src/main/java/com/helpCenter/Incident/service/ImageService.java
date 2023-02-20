package com.helpCenter.Incident.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.entity.ImageCreation;

@Service
public interface ImageService {

	ImageCreation uploadImage(MultipartFile file)throws IOException;

}
