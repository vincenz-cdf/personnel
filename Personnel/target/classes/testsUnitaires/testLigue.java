package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty", null, null); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("billard");
		assertEquals("billard", ligue.getNom());
	}
	
	@Test
	void setAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("John", "Doe", "jhondoe@mail.com", "admin", null, null);
		ligue.setAdministrateur(employe);
		assertEquals(employe, ligue.getAdministrateur());
	}
	@Test
	void removeEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("John", "Doe", "jhondoe@mail.com", "admin", null, null);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
	}
	@Test
	void setAdminException() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Ligue ligue2 = gestionPersonnel.addLigue("billard");
		Employe employe = ligue.addEmploye("John", "Doe", "jhondoe@mail.com", "admin", null, null);
		assertThrows(DroitsInsuffisants.class, () -> ligue2.setAdministrateur(employe));
	}
	
	@Test
	void removeLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.remove();
		assertFalse(gestionPersonnel.getLigues().contains(ligue));
	}
}
