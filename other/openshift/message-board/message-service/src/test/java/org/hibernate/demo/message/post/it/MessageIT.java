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

	public static final String USERNAME = "fercoli";
	public static final String USERNAME_2 = "aboriero";

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
	Message post4;
	Message post5;
	Message post6;

	@Before
	public void before() {

		post1 = new Message( USERNAME, "Here I am!" );
		post2 = new Message( USERNAME, "Here I am! II" );
		post3 = new Message( USERNAME, "Here I am! III" );

		post1.addTag( "music" );
		post2.addTag( "stuff" );
		post3.addTag( "play" );

		post4 = new Message( USERNAME_2, "Hello!" );
		post5 = new Message( USERNAME_2, "Hello! II" );
		post6 = new Message( USERNAME_2, "Hello! III" );

		post4.addTag( "music" );
		post5.addTag( "stuff" );
		post6.addTag( "play" );

		testSubject.insertPost( post1 );
		testSubject.insertPost( post2 );
		testSubject.insertPost( post3 );
		testSubject.insertPost( post4 );
		testSubject.insertPost( post5 );
		testSubject.insertPost( post6 );

	}

	@Test
	public void test() throws Exception {

		List<Message> fabioMessages = null;
		List<Message> andreaMessages = null;

		for (int i=0; i<20; i++) {
			fabioMessages = testSubject.findMessagesByUser( USERNAME );
			andreaMessages = testSubject.findMessagesByUser( USERNAME_2 );
			log.info( "{} fabioMessages is now present on board: {}", fabioMessages.size(), fabioMessages );
			log.info( "{} andreaMessages is now present on board: {}", andreaMessages.size(), andreaMessages );

			if (fabioMessages.size() == 3 && andreaMessages.size() == 3) {
				break;
			}

			if (i == 19) {
				fail();
			}

			Thread.sleep( 300 );
		}

		// order is not important!
		assertTrue( fabioMessages.contains( post1 ) );
		assertTrue( fabioMessages.contains( post2 ) );
		assertTrue( fabioMessages.contains( post3 ) );

		// order is not important!
		assertTrue( andreaMessages.contains( post4 ) );
		assertTrue( andreaMessages.contains( post5 ) );
		assertTrue( andreaMessages.contains( post6 ) );

	}

}
