package org.ticpy.tekoporu.inscripcion.security;


import org.ticpy.tekoporu.security.Authenticator;
import org.ticpy.tekoporu.security.User;

public class Autenticador implements Authenticator {

	@Override
	public boolean authenticate() {
		return true;
	}
	@Override
	public User getUser() {
		return new User() {			
			@Override
			public void setAttribute(Object arg0, Object arg1) {
			}
			@Override
			public String getId() {
				return null;
			}
			@Override
			public Object getAttribute(Object arg0) {
				return null;
			}
		};
	}
	@Override
	public void unAuthenticate() {
	}
}
