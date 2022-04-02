package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import java.time.LocalDate;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("GÃ©rer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrÃ©e par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {

			for (Employe employes: ligue.getEmployes())
			System.out.println(employes);

			if(ligue.getEmployes().size() == 0)
			{
				System.out.println("Pas d'employÃ©, veuillez en ajouter");
			}
		});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue, null));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}
	
	private Menu editerEmploye(Employe employe)
	{
		Menu menu = new Menu("Editer " + employe.getNom());
		menu.add(modifierEmploye(employe));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("SÃ©lectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private List<Employe> selectionnerEmploye()
	{
		return new List<Employe>("SÃ©lectionner un employÃ©", "e", 
				() -> new ArrayList<>(gestionPersonnel.getEmployes()),
				(element) -> editerEmploye(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue) throws SauvegardeImpossible
	{
		return new Option("Ajouter un employÃ©", "a",
				() -> 
				{
					ligue.addEmploye(getString("nom : "), 
							getString("prenom : "), 
							getString("mail : "), 
						    getString("password : "), 
						    LocalDate.parse(getString("Date d'arrivée (YYYY-MM-DD) : ")), 
						    null
						    );
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("GÃ©rer les employÃ©s de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		try {
			menu.add(ajouterEmploye(ligue));
		} catch (SauvegardeImpossible e) {
			System.err.println("Impossiblede sauvegarder cet employe");
			e.printStackTrace();
		}
		menu.add(selectionnerEmploye());
		menu.addBack("q");
		return menu;
	}

	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		return new List<>("Supprimer un employÃ©", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}
	
	private List<Employe> changerAdministrateur(final Ligue ligue, final Employe employe)
	{
		List<Employe> EmployeList = new List<>("Changer administrateur", "c",
				   ()-> new ArrayList<>(ligue.getEmployes()),
				   (index, element) -> {
					   ligue.setAdministrateur(element);
				   });
		  EmployeList.addBack("q");
		  return EmployeList;
	}		

	private Option modifierEmploye(final Employe employe)
	{
		return new Option("Modifier l'employÃ©", "e", () -> {
			employeConsole.editerEmploye(employe);
		});
	}
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {
			try {
				ligue.remove();
			} 
			catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		});
	}
	
}
