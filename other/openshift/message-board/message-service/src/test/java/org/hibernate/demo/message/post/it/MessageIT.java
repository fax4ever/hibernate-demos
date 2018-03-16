/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.it;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class MessageIT {

	public static final String USERNAME = "fax4ever";

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private MessageService testSubject;

	@Inject
	private Logger log;

	Message post1;
	Message post2;
	Message post3;

	@Before
	public void before() {

		post1 = new Message( USERNAME, "Here I am!" );
		post2 = new Message( USERNAME, "Here I am! II" );
		post3 = new Message( USERNAME, "Here I am! III" );

		post1.addTag( "music" );
		post2.addTag( "stuff" );
		post3.addTag( "play" );

		testSubject.insertPost( post1 );
		testSubject.insertPost( post2 );
		testSubject.insertPost( post3 );

	}

	@Test
	public void test() throws Exception {

		List<Message> messages = null;

		for (int i=0; i<10; i++) {
			messages = testSubject.findMessagesByUser( USERNAME );
			log.info( "{} messages is now present on board: {}", messages.size(), messages );

			if (messages.size() == 3) {
				break;
			}

			if (i >= 10) {
				fail();
			}

			Thread.sleep( 100 );
		}

		// order is not important!
		assertTrue( messages.contains( post1 ) );
		assertTrue( messages.contains( post2 ) );
		assertTrue( messages.contains( post3 ) );

	}

}
