package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testEmploye
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void estAdminFalse() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		assertFalse(employe.estAdmin(ligue) );
	}
	@Test
	void estRootFalse() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		assertFalse(employe.estRoot());
	}
	
	@Test
	void estRootTrue() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		assertTrue(root.estRoot());
	}
	
	@Test 
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		employe.setNom("jambon");
		assertEquals("jambon", employe.getNom());
	}
	
	@Test 
	void setPrenom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		employe.setPrenom("Depardieu");
		assertEquals("Depardieu", employe.getPrenom());
	}
	
	@Test
	void setMail() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		employe.setMail("G.Bouchard@outlook.fr");
		assertEquals("G.Bouchard@outlook.fr", employe.getMail());
	}
	
	@Test
	void setPassword() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		employe.setPassword("password");
		assertTrue(employe.checkPassword("password"));
	}

	@Test
	void remove() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
	}
	
	@Test
	void removeAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("FlÃ©chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2016-09-05"), null); 
		ligue.setAdministrateur(employe);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
	}
	
	@Test
	void removeRoot() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () ->root.remove());
	}
	
	void getDateDepart() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-04-05"), LocalDate.parse("2017-02-04"));
		assertEquals(LocalDate.parse("2017-02-04"), employe.getDateDepart());
	}

	@Test
	void setDateDepart() throws SauvegardeImpossible, SauvegardeImpossible
	{
		try {
			Ligue ligue = gestionPersonnel.addLigue("nomLigue");
			Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-07-05"), LocalDate.parse("2017-09-07"));
			employe.setDateDepart(LocalDate.parse("2019-01-09"));
			assertEquals(LocalDate.parse("2019-01-09"), employe.getDateDepart());
			employe.setDateDepart(null);
			assertEquals(null, employe.getDateDepart());
			employe.setDateDepart(LocalDate.parse("2019-01-08"));
		}
		catch (SauvegardeImpossible e) {
			assertTrue(e instanceof SauvegardeImpossible);
		}
	}

	@Test
	void getDateArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("nomLigue");
		Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-09-05"), LocalDate.parse("2017-07-02"));
		assertEquals(LocalDate.parse("2016-09-05"), employe.getDateArrivee());
	}

	@Test
	void setDateArrivee() throws SauvegardeImpossible, SauvegardeImpossible
	{
		try {
			Ligue ligue = gestionPersonnel.addLigue("nomLigue");
			Employe employe = ligue.addEmploye("Joseph", "Mozart", "josephmozart@gmail.com", "admin", LocalDate.parse("2016-01-05"), LocalDate.parse("2017-09-02"));
			employe.setDateArrivee(LocalDate.parse("2016-01-04"));
			assertEquals(LocalDate.parse("2016-01-04"), employe.getDateArrivee());
			employe.setDateArrivee(null);
			assertEquals(null, employe.getDateArrivee());
			employe.setDateArrivee(LocalDate.parse("2016-01-04"));
		} 
		catch (SauvegardeImpossible e) {
			assertTrue(e instanceof SauvegardeImpossible);
		}
	}
}