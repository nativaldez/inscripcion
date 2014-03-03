package org.ticpy.tekoporu.inscripcion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.inscripcion.business.CursoBC;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.transaction.Transactional;
import org.ticpy.tekoporu.util.ResourceBundle;

@ViewController
public class CursoMB {
	@Inject
	private CursoBC bc;

	public List<Alumno> getAlumnosMatriculados() {
		return bc.obtenerAlumnosMatriculados();
	}

	@Inject
	private ResourceBundle bundle;
	@Inject
	private Logger logger;

	private String nombreAlumno;

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	
	@Transactional
	public void matricular() {
		bc.matricular(new Alumno(nombreAlumno));
		String mensaje = bundle.getString("matricula.exito",nombreAlumno);
		logger.info(mensaje);
	}

		
}