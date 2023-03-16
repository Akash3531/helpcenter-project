package com.helpCenter.comment.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.exceptionHandler.IncidentNotFoundException;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.comment.dto.CommentByIncidentIdDto;
import com.helpCenter.comment.dto.RequestCommentDto;
import com.helpCenter.comment.dto.ResponseCommentDto;
import com.helpCenter.comment.entity.Comment;
import com.helpCenter.comment.reposatiory.CommentReposatiory;
import com.helpCenter.comment.service.CommentsService;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.repository.UserRepository;

@Service
public class CommentServiceimpl implements CommentsService {

	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	CommentReposatiory commentReposatiory;
	@Autowired
	Comment commentClass;
	@Autowired
	ResponseCommentDto responseDto;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentByIncidentIdDto commentByIncidentIdDto;

// CREATE COMMENT
	@Override
	public void createComment(int id, RequestCommentDto commentDto, List<MultipartFile> file) throws IOException {

		// Dto Conversion
		Comment comment = commentClass.dtoToClass(commentDto);
		// Get User from Authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		User user = userRepository.findByuserName(name);
		// Get Incident
		Incident incident = incidentReposatiory.findById(id);
		if (incident == null) {
			throw new IncidentNotFoundException(id);
		} else {
			// Adding Images
			List<ImageCreation> imageslist = new ArrayList<>();
			if (file != null) {
				for (MultipartFile multipart : file) {
					ImageCreation image = new ImageCreation();
					image.setImage(multipart.getBytes());
					image.setComment(comment);
					imageslist.add(image);
				}
				comment.setImages(imageslist);
			}
			comment.setUser(user);
			comment.setIncident(incident);
			commentReposatiory.save(comment);
		}

	}

// GET ALL COMMENTS
	@Override
	public List<ResponseCommentDto> getAllComments() {
		List<Comment> comments = commentReposatiory.findAll();
		for (Comment comment2 : comments) {
			System.out.println(comment2);
		}
		List<ResponseCommentDto> commentsList = comments.stream()
				.map((comment) -> responseDto.commentToResponsedto(comment)).collect(Collectors.toList());
		return commentsList;
	}

// GET COMMENTS BY INCIDENT ID
	@Override
	public List<CommentByIncidentIdDto> getCommentsByIncident(int id) {
		List<Comment> comments = commentReposatiory.findCommentByIncidentId(id);	
		if (comments.size()==0) {
			throw new IncidentNotFoundException(id);
		} else {
			List<CommentByIncidentIdDto> commentsList = comments.stream()
					.map((comment) ->commentByIncidentIdDto.dtoConversion(comment)).collect(Collectors.toList());
			return commentsList;
		}
	}

}
