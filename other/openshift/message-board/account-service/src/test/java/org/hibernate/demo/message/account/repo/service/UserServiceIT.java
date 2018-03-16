/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.repo.service;

import javax.inject.Inject;

import org.hibernate.demo.message.account.core.entity.User;
import org.hibernate.demo.message.account.core.service.UserService;
import org.hibernate.demo.message.account.repo.service.util.DeploymentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Andrea Boriero
 */
@RunWith(Arquillian.class)
public class UserServiceIT {
	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private UserService userService;

	@Test
	public void testFindUser(){
		User non_existing_user = userService.findByUsername( "NON_EXISTING_USER" );

	}
}
