/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.repo;

import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.demo.message.post.core.entity.Post;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import org.slf4j.Logger;

import org.apache.lucene.search.Query;

/**
 * @author Fabio Massimo Ercoli
 */
@ApplicationScoped
public class PostRepo {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public PostRepo() {
	}

	public PostRepo(EntityManager em, Logger log) {
		this.em = em;
		this.log = log;
	}

	public void add(@Valid Post post) {
		em.persist(post);
	}

	public List<Post> findByUsername(String username) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager( em );
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity( Post.class ).get();

		Query query = queryBuilder
			.keyword()
				.onField( "username" ).matching( username )
			.createQuery();

		return fullTextEntityManager.createFullTextQuery(query, Post.class).getResultList();
	}

	public List<Post> findByUser(String username) {

		log.info( "ciao" );

		int identityRepo = System.identityHashCode( this );
		int identityEM = System.identityHashCode( em );

		Session hibernateSession = (Session)em.getDelegate();
		int hibSession = System.identityHashCode( hibernateSession );

		log.info( "identityRepo {} - identityEM {} - hibSession {}", identityRepo, identityEM, hibSession );

		javax.persistence.Query query = em.createQuery( "from Post u where u.username = :username" );
		query.setParameter( "username", username );
		return query.getResultList();

	}

}
