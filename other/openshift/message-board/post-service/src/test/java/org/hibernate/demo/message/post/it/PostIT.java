/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.it;

import static org.hibernate.demo.message.test.util.TestUtil.inTransaction;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceUnitTransactionType;

import org.slf4j.Logger;

import org.hibernate.demo.message.post.core.entity.Post;
import org.hibernate.demo.message.post.core.repo.PostRepo;

/**
 * @author Fabio Massimo Ercoli
 */
@RunWith(Arquillian.class)
public class PostIT {

	@Deployment
	public static WebArchive create() {
		return ShrinkWrap
			.create( WebArchive.class, "post-service.war" )
			.addPackages( true, "org.hibernate.demo.message.post.core" )

			// only for test
			.addPackages( true, "org.hibernate.demo.message.test" )

			.addAsResource( new StringAsset( persistenceXml().exportAsString() ), "META-INF/persistence.xml" )
			.addAsResource( "hotrodclient.properties" )
			.addAsWebInfResource( new File( "src/main/webapp/WEB-INF/jboss-deployment-structure.xml" ) );
	}

	private static PersistenceDescriptor persistenceXml() {
		return Descriptors.create( PersistenceDescriptor.class )
				.version( "2.1" )
				.createPersistenceUnit()
				.name( "primary" )
				.transactionType( PersistenceUnitTransactionType._JTA )
				.provider( "org.hibernate.ogm.jpa.HibernateOgmPersistence" )
				.getOrCreateProperties()
				.createProperty().name( "jboss.as.jpa.providerModule" ).value( "org.hibernate:5.2" ).up()
				.createProperty().name( "wildfly.jpa.hibernate.search.module" ).value( "org.hibernate.search.orm:5.9" ).up()
				.createProperty().name( "hibernate.search.default.directory_provider" ).value( "ram" ).up()
				.createProperty().name( "hibernate.ogm.datastore.provider" ).value( "infinispan_remote" ).up()
				.createProperty().name( "hibernate.ogm.infinispan_remote.configuration_resource_name" ).value( "hotrodclient.properties" ).up()
				.createProperty().name( "hibernate.ogm.datastore.create_database" ).value( "true" ).up()
				.up().up();
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

	}

}
