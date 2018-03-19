package org.hibernate.demo.message.post.core.statup;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.service.MessageService;

@Singleton
@Startup
public class FillCache {

	public static final String USERNAME = "fabio";
	public static final String USERNAME_2 = "andrea";

	public Message[] messages = new Message[6];

	@Inject
	private MessageService service;

	@PostConstruct
	public void onInit() {

		String uuid = UUID.randomUUID() + " :: ";

		messages[0] = new Message( USERNAME, uuid + "Here I am!" );
		messages[1] = new Message( USERNAME, uuid + "Here I am! II" );
		messages[2] = new Message( USERNAME, uuid + "Here I am! III" );

		messages[0].addTag( "music" );
		messages[1].addTag( "stuff" );
		messages[2].addTag( "play" );

		messages[3] = new Message( USERNAME_2, uuid + "Hello!" );
		messages[4] = new Message( USERNAME_2, uuid + "Hello! II" );
		messages[5] = new Message( USERNAME_2, uuid + "Hello! III" );

		messages[3].addTag( "music" );
		messages[4].addTag( "stuff" );
		messages[5].addTag( "play" );

		for (int i=0; i<messages.length; i++) {
			service.insertPost( messages[i] );
		}
	}

	public Message getPost( int index ) {
		return messages[index];
	}

}
