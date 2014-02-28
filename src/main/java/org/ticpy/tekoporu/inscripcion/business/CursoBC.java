package org.ticpy.tekoporu.inscripcion.business;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.exception.ExceptionHandler;
import org.ticpy.tekoporu.inscripcion.config.InscripcionConfig;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.inscripcion.excepcion.CursoException;
import org.ticpy.tekoporu.lifecycle.Startup;
import org.ticpy.tekoporu.security.RequiredPermission;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.transaction.Transactional;
import org.ticpy.tekoporu.util.ResourceBundle;

@BusinessController
public class CursoBC {

	@Inject
	private Logger logger;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private InscripcionConfig config;

	@Inject
	private AlumnoBC alumnoBC;

	@Transactional
	@RequiredPermission(resource = "curso", operation = "matricular")
	public void matricular(Alumno alumno) {
		if (estaMatriculado(alumno)
				|| obtenerAlumnosMatriculados().size() == config
						.getCapacidadCurso()) {
			throw new CursoException();
		}
		alumnoBC.insert(alumno);
		logger.info(bundle.getString("matricula.exito", alumno.getNombre()));
	}

	private List<Alumno> obtenerAlumnosMatriculados() {
		return alumnoBC.findAll();
	}

	@Transactional
	public void vaciarCurso() {
		List<Alumno> alumnos = obtenerAlumnosMatriculados();
		for (Alumno alumno : alumnos) {
			alumnoBC.delete(alumno.getMatricula());
		}
	}

	@RequiredPermission(resource = "curso", operation = "consular")
	public boolean estaMatriculado(Alumno alumno) {
		return obtenerAlumnosMatriculados().contains(alumno);
	}

	@ExceptionHandler
	public void tratar(CursoException e) {
		logger.warn(bundle.getString("matricula.error"));
		throw e;
	}
	
	@Startup
	public void iniciar(){
		logger.info("Iniciando ...");
	}
}
