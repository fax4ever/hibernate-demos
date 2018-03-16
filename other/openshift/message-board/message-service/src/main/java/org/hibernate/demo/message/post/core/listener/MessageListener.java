package org.hibernate.demo.message.post.core.listener;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Board;
import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.repo.BoardRepo;

import org.slf4j.Logger;

@Singleton
public class MessageListener {

	@Inject
	private BoardRepo boards;

	@Inject
	private Logger log;

	@Asynchronous
	@Lock(LockType.READ)
	public void addMessageToBoard(@Observes Message message) {

		// should be implemented server side
		// it is safe only with single client
		synchronized (message.getUsername().intern()) {
			Board board = boards.find( message.getUsername() );

			if ( board == null ) {
				boards.add( new Board( message ) );
				return;
			}

			board.pushMessage( message );
			boards.update( board );

			log.info( "add message {} to the board {}", message.getBody(), message.getUsername()  );
		}

	}


}
