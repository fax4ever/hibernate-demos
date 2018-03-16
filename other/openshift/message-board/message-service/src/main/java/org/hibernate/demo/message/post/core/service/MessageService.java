package org.hibernate.demo.message.post.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;
import org.hibernate.demo.message.post.core.repo.MessageRepo;

@Path( "/messages" )
@Stateless
public class MessageService {

	@Inject
	private MessageRepo messages;

	@Inject
	private BoardRepo boards;

	@Inject
	private Event<Message> event;

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<Message> findMessagesByUser( @QueryParam( "username" ) String username ) {

		Board board = boards.find( username );
		if (board == null) {
			return new ArrayList<>();
		}

		return board.getMessages();

	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public void insertPost( Message message ) {

		messages.add( message );
		event.fire( message );

	}


}
