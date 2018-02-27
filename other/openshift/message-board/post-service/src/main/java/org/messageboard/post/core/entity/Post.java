/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.messageboard.post.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * @author Fabio Massimo Ercoli
 */
@Entity
@Indexed
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
	@SequenceGenerator(name = "post_gen", sequenceName = "post_seq", initialValue = 0)
	private Long id;

	@Field(analyze= Analyze.NO)
	private String username;

	@Field(analyze=Analyze.YES)
	private String body;

	@OneToMany
	@Cascade( CascadeType.PERSIST )
	private Set<Tag> tags = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date moment = new Date();

	public Post() {
	}

	public Post(String username, String body) {
		this.username = username;
		this.body = body;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addTag(String tagName) {
		this.tags.add( new Tag( tagName ) );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null || getClass() != o.getClass() ) return false;

		Post post = (Post) o;

		return id.equals( post.id );
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "Post{" +
			"id=" + id +
			", username='" + username + '\'' +
			", body='" + body + '\'' +
			", tags=" + tags +
			", moment=" + moment +
			'}';
	}
}
