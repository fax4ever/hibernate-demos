/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.entity;

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
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Fabio Massimo Ercoli
 */
@Entity
@Indexed
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
	@SequenceGenerator(name = "post_gen", sequenceName = "post_seq", initialValue = 1)
	private Long id;

	@Field(analyze= Analyze.NO)
	@NotEmpty
	private String username;

	@Field(analyze=Analyze.YES)
	@NotEmpty
	private String body;

	@OneToMany
	@Cascade(CascadeType.PERSIST)
	private Set<Tag> tags = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date moment = new Date();

	private Post() {
	}

	public Post(String username, String body) {
		this.username = username;
		this.body = body;
	}

	public void addTag(String tagName) {
		this.tags.add( new Tag( tagName ) );
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
