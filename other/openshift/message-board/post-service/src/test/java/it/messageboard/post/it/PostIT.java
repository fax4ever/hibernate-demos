/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package it.messageboard.post.it;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.slf4j.Logger;

import org.messageboard.post.core.entity.Post;
import org.messageboard.post.core.repo.PostRepo;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class PostIT {

	@Deployment
	public static WebArchive create() {
		return ShrinkWrap
			.create( WebArchive.class, "post-service.war" )
			.addPackages( true, "org.messageboard.post.core" )
			.addAsResource( "META-INF/persistence.xml" )
			.addAsResource( "hotrodclient.properties" )
			.addAsWebInfResource( new File( "src/main/webapp/WEB-INF/jboss-deployment-structure.xml" ) );
	}

	@Inject
	private PostRepo repo;

	@Inject
	private UserTransaction ut;

	@Inject
	private Logger log;

	@Test
	public void test() throws Exception {

		Post post = new Post( "fax4ever", "Here I'm" );
		post.addTag( "music" );

		ut.begin();
		repo.add( post );
		ut.commit();

		List<Post> posts = repo.findByUsername( "fax4ever" );
		log.info( "Founded posts: {}", posts );

		assertEquals( 1, posts.size() );

	}

}
