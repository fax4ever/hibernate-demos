package org.hibernate.demo.message.post.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.post.core.entity.Post;
import org.hibernate.demo.message.post.core.repo.PostRepo;

@Path( "/messages" )
@Stateless
public class MessageService {

	@Inject
	private PostRepo repo;

	@GET
	public String getCiao() {
		return "ciao!";
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<Post> findPostByUser( @QueryParam( "username" ) String username ) {

		//TODO: must to implement
		return new ArrayList<>(  );

	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public void insertPost( Post post ) {

		repo.add( post );

	}


}
