/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.test;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.demo.message.post.core.entity.Post;
import org.hibernate.demo.message.post.core.entity.Tag;
import org.hibernate.demo.message.post.core.repo.PostRepo;
import org.hibernate.demo.message.test.BaseEntityManagerFunctionalTestCase;

import org.junit.Test;

/**
 * @author Fabio Massimo Ercoli
 */
public class PostRepoTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { Post.class, Tag.class };
	}

	@Test
	public void createUserTest() {

		Post post = new Post( "fax4ever", "Here I am!" );
		post.addTag( "music" );

		doInJPA( this::entityManagerFactory, entityManager -> {
			PostRepo repo = new PostRepo( entityManager );

			repo.add( post );

			assertNotNull( post.getId() );

			List<Post> posts = repo.findByUser( "fax4ever" );

			assertEquals( 1, posts.size() );

		} );
	}

}
