package org.ticpy.tekoporu.inscripcion.security;


import org.ticpy.tekoporu.security.Authorizer;


public class Autorizador implements Authorizer{

	@Override
	public boolean hasPermission(String arg0, String arg1) {
		return true;
	}

	@Override
	public boolean hasRole(String arg0) {
		return true;
	}

}
