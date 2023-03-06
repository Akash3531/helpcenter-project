package com.helpCenter.comment.reposatiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helpCenter.comment.entity.Comment;

public interface CommentReposatiory extends JpaRepository<Comment, Integer> {

	@Query(value = "Select * from comment where incident_id=?", nativeQuery = true)
	public List<Comment> findByIncident(int id);
}
