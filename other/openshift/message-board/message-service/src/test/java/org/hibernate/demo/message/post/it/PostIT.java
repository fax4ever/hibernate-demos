/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.it;

import static org.hibernate.demo.message.test.util.TestUtil.inTransaction;
import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.slf4j.Logger;

import org.hibernate.demo.message.post.core.entity.Post;
import org.hibernate.demo.message.post.core.repo.PostRepo;
import org.hibernate.demo.message.post.util.DeploymentUtil;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class PostIT {

	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private PostRepo repo;

	@Inject
	private UserTransaction ut;

	@Inject
	private Logger log;

	@Test
	public void test() {

		Post post = new Post( "fax4ever", "Here I am!" );
		post.addTag( "music" );

		inTransaction( ut, ut -> {
			repo.add( post );
		} );

		inTransaction( ut, ut -> {
			List<Post> posts = repo.findByUser( "fax4ever" );
			log.info( "Founded posts: {}", posts );

			assertEquals( 1, posts.size() );
		} );

		inTransaction( ut, ut -> {
			List<Post> posts = repo.findByUser( "fax4ever" );
			log.info( "Founded posts: {}", posts );

			assertEquals( 1, posts.size() );
		} );

		inTransaction( ut, ut -> {
			List<Post> posts = repo.findByUser( "fax4ever" );
			log.info( "Founded posts: {}", posts );

			assertEquals( 1, posts.size() );
		} );

	}

}
