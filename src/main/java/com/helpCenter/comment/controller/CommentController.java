package com.helpCenter.comment.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.comment.dto.CommentByIncidentIdDto;
import com.helpCenter.comment.dto.RequestCommentDto;
import com.helpCenter.comment.dto.ResponseCommentDto;
import com.helpCenter.comment.service.CommentsService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentsService commentsService;

// CREATE COMMENT OR POST COMMENT
	@PostMapping(path = "/{incidentId}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createComment(@PathVariable int incidentId,
			@RequestPart(value = "comment") RequestCommentDto commentdto,
			@RequestParam(value = "image", required = false) List<MultipartFile> file) throws IOException {

		commentsService.createComment(incidentId, commentdto, file);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
// GET ALL COMMENTS 
	@GetMapping("/")
	public ResponseEntity<List<ResponseCommentDto>> getAllComments() {
		List<ResponseCommentDto> comments = commentsService.getAllComments();
		return new ResponseEntity<List<ResponseCommentDto>>(comments, HttpStatus.OK);
	}

// GET COMMENTS BY INCIDENT
	@GetMapping(path = "/{incidentId}")
	public ResponseEntity<List<CommentByIncidentIdDto>> getCommentByIncident(@PathVariable int incidentId) {
		List<CommentByIncidentIdDto> comments = commentsService.getCommentsByIncident(incidentId);
		return new ResponseEntity<List<CommentByIncidentIdDto>>(comments, HttpStatus.OK);
	}
}
