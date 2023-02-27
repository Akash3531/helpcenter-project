package com.helpCenter.requestHandlers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpCenter.requestHandlers.entity.RequestHandler;

public interface RequestHandlerRepository extends JpaRepository<RequestHandler,Integer>{
	

}
