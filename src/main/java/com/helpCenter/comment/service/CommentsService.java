package com.helpCenter.comment.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.comment.dto.CommentByIncidentIdDto;
import com.helpCenter.comment.dto.RequestCommentDto;
import com.helpCenter.comment.dto.ResponseCommentDto;
import com.helpCenter.comment.entity.Comment;

@Service
public interface CommentsService {

	Comment createComment(int id, RequestCommentDto comments, List<MultipartFile> file) throws IOException;

	List<ResponseCommentDto> getAllComments();

	List<CommentByIncidentIdDto> getCommentsByIncident(int id);
}
