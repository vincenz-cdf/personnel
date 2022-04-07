package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import java.time.LocalDate;
import personnel.SauvegardeImpossible;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}
	
	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}
	
	Option editerEmploye(final Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateArrivee(employe, null));
			menu.add(changerDateDepart(employe, null));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {
			try {
				employe.setPassword(getString("Nouveau password : "));
			}
			catch (RuntimeException e) {
				System.out.println("Le mot de passe ne contient pas au moins 8 caract�res ou de chiffres ");
			}
		});
		
	}
	
	private Option changerDateArrivee(final Employe employe, LocalDate dateArrivee) {

		return new Option("Changer la date d'arrivée", "datea", ()->
		{
			try {
			    System.out.println("Date d'arrivée");
				employe.setDateArrivee(LocalDate.parse(getString("Date d'arrivée (YYYY-MM-DD) : ")));

		    } 
			catch (SauvegardeImpossible e) {
				System.out.println("Date d'arrivée incorrecte. ");
		}});
	}


	private Option changerDateDepart(final Employe employe, LocalDate dateDepart) {
		return new Option("Changer la date de départ", "dated", ()->
		{
			try {
			  employe.setDateDepart(LocalDate.parse(getString("Date d'arrivée (YYYY-MM-DD) : ")));
		 } 
			catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}});
	}
}
