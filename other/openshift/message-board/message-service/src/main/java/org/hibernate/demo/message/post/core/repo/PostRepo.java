/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.demo.message.post.core.entity.Post;

import org.slf4j.Logger;

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

}
