package org.hibernate.demo.message.post.core.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.HibernateException;

@Provider
public class HibernateExceptionMapper implements ExceptionMapper<HibernateException> {

	@Override
	public Response toResponse(HibernateException e) {
		return Response.status( 500 )
			.entity( new ExceptionMessage( 123, e.getClass().toString(), e.getMessage() ) )
			.type( MediaType.APPLICATION_JSON )
			.build();
	}

}
