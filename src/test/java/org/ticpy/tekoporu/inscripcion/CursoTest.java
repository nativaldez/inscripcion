package org.ticpy.tekoporu.inscripcion;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.inscripcion.business.CursoBC;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.inscripcion.excepcion.CursoException;
import org.ticpy.tekoporu.inscripcion.security.Credenciales;
import org.ticpy.tekoporu.junit.DemoiselleRunner;
import org.ticpy.tekoporu.security.SecurityContext;

@RunWith(DemoiselleRunner.class)
public class CursoTest {

	@Inject
	private CursoBC curso;

	@Test
	public void matricularAlumnoConExito() {
		curso.vaciarCurso();
		Alumno alumno = new Alumno("Andres Gonzales");
		curso.matricular(alumno);
		Assert.assertTrue(curso.estaMatriculado(alumno));
	}

	@Test(expected = CursoException.class)
	public void errorMatricularAlumnoDuplicado() {
		curso.vaciarCurso();
		Alumno alumno = new Alumno("Andres Gonzales");
		curso.matricular(alumno);
		curso.matricular(alumno);
	}

	@Test(expected = CursoException.class)
	public void errorMatricularCursoLleno() {
		curso.vaciarCurso();
		for (int i = 1; i <= 5; i++) {
			curso.matricular(new Alumno("Alumno " + i));
		}
		curso.matricular(new Alumno("Alumno 6"));
	}

	@Inject
	private SecurityContext securityContext;
	
	@Inject
	private Credenciales credenciales;

	@Before
	public void setUp() {
		credenciales.setNombre("secretaria");
		credenciales.setContraseña("secreto");
		securityContext.login();
	}
}
