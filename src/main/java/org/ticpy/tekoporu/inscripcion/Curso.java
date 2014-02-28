package org.ticpy.tekoporu.inscripcion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.ticpy.tekoporu.exception.ExceptionHandler;
import org.ticpy.tekoporu.inscripcion.config.InscripcionConfig;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.inscripcion.excepcion.CursoException;
import org.ticpy.tekoporu.stereotype.Controller;
import org.ticpy.tekoporu.util.ResourceBundle;

@Controller
public class Curso {

	private List<Alumno> alumnosMatriculados = new ArrayList<Alumno>();

	@Inject
	private Logger logger;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private InscripcionConfig config;

	@Inject
	private EntityManager em;

	public void matricular(Alumno alumno) {
		if (estaMatriculado(alumno)
				|| obtenerAlumnosMatriculados().size() == config
						.getCapacidadCurso()) {
			throw new CursoException();
		}

		em.getTransaction().begin();
		em.persist(alumno);
		em.getTransaction().commit();

		logger.info(bundle.getString("matricula.exito", alumno.getNombre()));
	}

	public boolean estaMatriculado(Alumno alumno) {
		return obtenerAlumnosMatriculados().contains(alumno);
	}

	private List<Alumno> obtenerAlumnosMatriculados() {
		return em.createQuery("select a from Alumno a").getResultList();
	}
	
	public void vaciarCurso(){
		em.getTransaction().begin();
		List<Alumno> alumnos = obtenerAlumnosMatriculados();
		for (Alumno alumno : alumnos) {
			em.remove(alumno);
		}
		em.getTransaction().commit();
	}

	@ExceptionHandler
	public void tratar(CursoException e) {
		logger.warn(bundle.getString("matricula.error"));
		throw e;
	}
}
