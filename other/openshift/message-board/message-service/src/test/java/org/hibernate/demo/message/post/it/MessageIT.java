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

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.hibernate.demo.message.post.core.entity.Message;
import org.hibernate.demo.message.post.core.service.MessageService;
import org.hibernate.demo.message.post.core.statup.FillCache;
import org.hibernate.demo.message.post.util.DeploymentUtil;

import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class MessageIT {

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private MessageService testSubject;

	@Inject
	private FillCache createdCache;

	@Inject
	private Logger log;

	@Test
	public void test() throws Exception {

		List<Message> fabioMessages = null;
		List<Message> andreaMessages = null;

		for (int i=0; i<20; i++) {
			fabioMessages = testSubject.findMessagesByUser( FillCache.USERNAME );
			andreaMessages = testSubject.findMessagesByUser( FillCache.USERNAME_2 );
			log.info( "{} fabioMessages is now present on board: {}", fabioMessages.size(), fabioMessages );
			log.info( "{} andreaMessages is now present on board: {}", andreaMessages.size(), andreaMessages );

			if (fabioMessages.size() == 3 && andreaMessages.size() == 3) {
				break;
			}

			if (i == 19) {
				fail( "attempts exhausted to retrieve board messages" );
			}

			Thread.sleep( 300 );
		}

		// order is not important!
		for ( int i=0; i<3; i++ ) {
			assertTrue( "Fabio's board does not contain post: " + createdCache.getPost( i ), fabioMessages.contains( createdCache.getPost( i ) ) );
		}

		// order is not important!
		for ( int i=3; i<6; i++ ) {
			assertTrue( "Andrea's board does not contain post: " + createdCache.getPost( i ), andreaMessages.contains( createdCache.getPost( i ) ) );
		}

	}

}
