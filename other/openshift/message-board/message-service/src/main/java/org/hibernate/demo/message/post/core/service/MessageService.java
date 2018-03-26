package org.hibernate.demo.message.post.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.entity.Tag;
import org.hibernate.demo.message.post.core.repo.BoardRepo;
import org.hibernate.demo.message.post.core.repo.MessageRepo;
import org.hibernate.demo.message.post.core.repo.TagRepo;
import org.hibernate.demo.message.post.core.service.exception.ResourceNotFoundException;

@Path( "/messages" )
@Stateless
public class MessageService {

	@Inject
	private MessageRepo messages;

	@Inject
	private BoardRepo boards;

	@Inject
	private TagRepo tags;

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

		Set<Tag> tagSet = message.getTags().stream().map( tag -> {
			Tag loaded = tags.find( tag.toString() );
			return ( loaded != null ) ? loaded : tag;
		} ).collect( Collectors.toSet() );

		message.setTags( tagSet );

		messages.add( message );
		event.fire( message );

	}

	@Path( "{id}" )
	@DELETE
	public void deleteMessage( @PathParam( "id" ) Long id ) throws ResourceNotFoundException {

		Message message = messages.findById( id );
		if (message == null) {
			throw new ResourceNotFoundException( "message", id );
		}

		Board board = boards.find( message.getUsername() );

		board.deleteMessage( message );
		boards.update( board );
		messages.remove( message );

	}


}
