package org.hibernate.demo.message.post.core.listener;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;

@Singleton
public class MessageListener {

	@Inject
	private BoardRepo boards;

	@Asynchronous
	public void addMessageToBoard(@Observes Message message) {
		Board board = boards.find( message.getUsername() );

		if ( board == null ) {
			boards.add( new Board( message ) );
			return;
		}

		board.pushMessage( message );
		boards.update( board );
	}


}
