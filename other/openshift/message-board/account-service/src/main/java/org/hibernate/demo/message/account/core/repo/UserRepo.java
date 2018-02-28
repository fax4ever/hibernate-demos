/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.core.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;

import org.hibernate.demo.message.account.core.entity.User;

/**
 * @author Andrea Boriero
 */
@ApplicationScoped
public class UserRepo {

	@Inject
	private EntityManager em;

	public void add(@Valid User user) {
		em.persist( user );
	}

	public User findByUserName(String userName) {
		Query query = em.createQuery( "from User u where u.userName = :userName" );
		query.setParameter( "userName", userName );
		return (User) query.getSingleResult();
	}

}
