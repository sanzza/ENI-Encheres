package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurDAOJDBCImpl implements UtilisateurDAO {

	private static final String INSERT = "INSERT INTO UTILISATEURS VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL = "SELECT * FROM UTILISATEURS";
	private static final String GET_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?,"  +
											"email=?, telephone=?, rue=?, code_postal=?, ville=?," +
											" mot_de_passe=?, credit=?, administrateur=?";
	private static final String DELETE = "DELETE UTILISATEURS WHERE no_utilisateur=?";
	
	private static EnchereDAO enchereDao = new EnchereDAOJDBCImpl();
	private static ArticleVenduDAO articleDao = new ArticleVenduDAOJDBCImpl();
	
	@Override
	public void insert(Utilisateur utilisateur) {
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(INSERT);
            requete.setString(1, utilisateur.getPseudo());
            requete.setString(2, utilisateur.getNom());
            requete.setString(3, utilisateur.getPrenom());
            requete.setString(4, utilisateur.getEmail());
            requete.setString(5, utilisateur.getTelephone());
            requete.setString(6, utilisateur.getRue());
            requete.setString(7, utilisateur.getCodePostal());
            requete.setString(8, utilisateur.getVille());
            requete.setString(9, utilisateur.getPassword());
            requete.setInt(10, utilisateur.getCredit());
            requete.setBoolean(11, utilisateur.isAdministrateur());
            
            requete.executeUpdate();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
		
	}

	@Override
	public List<Utilisateur> getAll() {
		
		List<Utilisateur> list = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			 PreparedStatement requete = cnx.prepareStatement(GET_ALL);
			 ResultSet rs = requete.executeQuery();
			
			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setPassword(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				utilisateur.setArticlesVendus(articleDao.getByVendeur());
				utilisateur.setEncheres(enchereDao.getByEncherisseur());
				
				list.add(utilisateur);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public Utilisateur getById(int id) {
		
		Utilisateur utilisateur = null;
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement requete = cnx.prepareStatement(GET_BY_ID);
			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();
			
			if(rs.next()) 
			{
				utilisateur = new Utilisateur();
				utilisateur.setId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				utilisateur.setArticlesVendus(articleDao.getByVendeur());
				utilisateur.setEncheres(enchereDao.getByEncherisseur());
			}
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return utilisateur;
	}

	@Override
	public void update(Utilisateur utilisateur) {
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement requete = cnx.prepareStatement(UPDATE);
			requete.setString(1, utilisateur.getPseudo());
            requete.setString(2, utilisateur.getNom());
            requete.setString(3, utilisateur.getPrenom());
            requete.setString(4, utilisateur.getEmail());
            requete.setString(5, utilisateur.getTelephone());
            requete.setString(6, utilisateur.getRue());
            requete.setString(7, utilisateur.getCodePostal());
            requete.setString(8, utilisateur.getVille());
            requete.setString(9, utilisateur.getPassword());
            requete.setInt(10, utilisateur.getCredit());
            requete.setBoolean(11, utilisateur.isAdministrateur());
            
            requete.executeUpdate();
           
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void delete(int id) {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement requete = cnx.prepareStatement(DELETE);
            requete.setInt(1, id);
            requete.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}