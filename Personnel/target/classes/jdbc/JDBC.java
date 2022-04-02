
package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE id = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void updateEmp(Employe employe) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom_employe = ?, prenom_employe = ?, mail_employe = ? , password_employe = ? WHERE id_employe = ?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listLigue;
			listLigue = connection.prepareStatement("DELETE FROM ligue WHERE id = ?");
			listLigue.setInt(1, ligue.getId());
			listLigue.executeUpdate();
			System.out.println("Ligue " + ligue.getNom() + " supprim�");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}
	
	
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try {
			
			PreparedStatement instruction2;
			instruction2 = connection.prepareStatement("insert into employe (nom_employe, prenom_employe, mail_employe, password_employe, date_arrivee, id_ligue) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction2.setString(1, employe.getNom());		
			instruction2.setString(2, employe.getPrenom());	
			instruction2.setString(3, employe.getMail());
			instruction2.setString(4, employe.getPassword());
			instruction2.setString(5, employe.getDateArrivee() == null ? null :  String.valueOf(employe.getDateArrivee()));
			instruction2.setInt(6, employe.getLigue().getId());
			instruction2.executeUpdate();
			ResultSet id = instruction2.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	
	
	public void updateEmploye(Employe employe, String dataList) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
	        instruction = connection.prepareStatement("UPDATE employe SET " + dataList + "= ? WHERE id_employe = ?");
	
			Map <String, String> map = new HashMap<>();
					map.put("nom_employe", employe.getNom());
					map.put("prenom_employe", employe.getPrenom());
					map.put("mail_employe", employe.getMail());
					map.put("password_employe", employe.getPassword());
					map.put("date_arrivee", String.valueOf(employe.getDateArrivee()).isEmpty() ? null : String.valueOf(employe.getDateArrivee()));
					map.put("date_depart", String.valueOf(employe.getDateDepart()).isEmpty() ? null : String.valueOf(employe.getDateDepart()));
		instruction.setString(1, map.get(dataList));
	    instruction.setInt(2, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			
			throw new SauvegardeImpossible(e);
	}
	}
	
	
	
	

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("DELETE FROM employe WHERE id_employe = ?");
			listEmploye.setInt(1, employe.getId());
			listEmploye.executeUpdate();
			System.out.println("Employe " + employe.getNom() + " supprim�");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}

	@Override
	public void SetAdmin(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("UPDATE employe SET admin = ? WHERE id_ligue = ? AND id_employe = ?");
			listEmploye.setInt(1, 1);
			listEmploye.setInt(2, employe.getLigue().getId());
			listEmploye.setInt(3, employe.getId());
			listEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	public void setRoot(Employe employe) 
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("INSERT INTO employe (nom_employe, prenom_employe, mail_employe, password_employe, superAdmin) VALUES (?,?,?,?,?)");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, 1);
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updateRoot(Employe employe) throws SauvegardeImpossible
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom_employe = ?, prenom_employe = ?, mail_employe = ?, password_employe = ? WHERE superAdmin = 1");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	@Override
	public void removeAdmin(Ligue ligue) throws SauvegardeImpossible
	{
		try
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE employe SET admin = 0 WHERE id_ligue = ?");
			tableEmploye.setInt(1, ligue.getId());
			tableEmploye.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	public void setAdmin(Employe employe) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE employe SET admin = (CASE WHEN id_employe = ? THEN 1 WHEN id_employe <> ? THEN 0 END) WHERE id_ligue = ?");
			tableEmploye.setInt(1, employe.getId());
			tableEmploye.setInt(2, employe.getId());
			tableEmploye.setInt(3, employe.getLigue().getId());
			tableEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	public Employe getSuperAdmin(Employe superadmin) throws SauvegardeImpossible
	{
		try {
			Statement intruction = connection.createStatement();
			String requete = "SELECT * FROM employe WHERE superAdmin = 1";
			ResultSet response = intruction.executeQuery(requete);
			if(!response.next()) {
				setRoot(superadmin);
			}
			else {
				superadmin.setId(1);
				String nom = response.getString("nom_employe");
				String prenom = response.getString("prenom_employe");
				String mail =  response.getString("mail_employe");
			    String password = response.getString("password_employe");
			    superadmin.setNom(nom);
			    superadmin.setPrenom(prenom);
			    superadmin.setMail(mail);
			    superadmin.setPassword(password);
			}
		    return superadmin;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	
	public Employe getAdmin(Employe admin) throws SauvegardeImpossible
	{
		try {
			Statement intruction = connection.createStatement();
			String requete = "SELECT * FROM employe WHERE admin = 1";
			ResultSet response = intruction.executeQuery(requete);
			if(!response.next()) {
				setRoot(admin);
			}
			else {
				admin.setId(1);
				String nom = response.getString("nom_employe");
				String prenom = response.getString("prenom_employe");
				String mail =  response.getString("mail_employe");
			    String password = response.getString("password_employe");
			    admin.setNom(nom);
			    admin.setPrenom(prenom);
			    admin.setMail(mail);
			    admin.setPassword(password);
			}
		    return admin;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}	
}