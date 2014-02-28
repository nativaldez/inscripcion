package org.ticpy.tekoporu.inscripcion.business;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.ticpy.tekoporu.exception.ExceptionHandler;
import org.ticpy.tekoporu.inscripcion.config.InscripcionConfig;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.inscripcion.excepcion.CursoException;
import org.ticpy.tekoporu.inscripcion.persistence.AlumnoDAO;
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
	private AlumnoDAO alumnoDAO;

	@Transactional
	public void matricular(Alumno alumno) {
		if (estaMatriculado(alumno)
				|| obtenerAlumnosMatriculados().size() == config
						.getCapacidadCurso()) {
			throw new CursoException();
		}
		alumnoDAO.insert(alumno);
		logger.info(bundle.getString("matricula.exito", alumno.getNombre()));
	}

	private List<Alumno> obtenerAlumnosMatriculados() {
		return alumnoDAO.findAll();
	}

	@Transactional
	public void vaciarCurso() {
		List<Alumno> alumnos = obtenerAlumnosMatriculados();
		for (Alumno alumno : alumnos) {
			alumnoDAO.delete(alumno);
		}
	}

	public boolean estaMatriculado(Alumno alumno) {
		return obtenerAlumnosMatriculados().contains(alumno);
	}

	@ExceptionHandler
	public void tratar(CursoException e) {
		logger.warn(bundle.getString("matricula.error"));
		throw e;
	}
}
