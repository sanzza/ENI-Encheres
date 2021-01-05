package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.RetraitDAO;



public class RetraitDAOJDBCImpl implements RetraitDAO{
	private static final String INSERT="INSERT INTO RETRAITS VALUES(?,?,?)";
	private static final String GET_BY_ID="SELECT * FROM RETRAITS WHERE no_retrait = ?";
	private static final String GET_ALL="SELECT * FROM RETRAITS";
	private static final String UPDATE="UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ?";
	private static final String DELETE="DELETE RETRAITS WHERE no_retrait = ?";
	
	@Override
	public static void insert(Retrait retrait) //INSERT
	{
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(INSERT);
			statement.setString(1, retrait.getRue());
			statement.setString(2, retrait.getCodePostal());
			statement.setString(3, retrait.getVille());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	@Override
	public static Retrait get(int id) //GET_BY_ID
	{
		Retrait retrait = null;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				retrait = new Retrait();
				retrait.setId(rs.getInt("id"));
				retrait.setRue(rs.getInt("rue"));
				retrait.setCodePostal(rs.getInt("codePostal"));
				retrait.setVille(rs.getString("ville"));
			}
		}
		catch(SQLException e)
		{
			
		}
		return retrait;
	}
	
	@Override
	public static List<Retrait> get() // GET_ALL
	{
		List<Retrait> listes = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(GET_ALL);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) 
			{
				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("id"));
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getInt("codePostal"));
				retrait.setVille(rs.getString("ville"));
				listes.add(retrait);
			}
		}
		
		catch(SQLException e)
		{
			
		}
		return listes;
	}
	
	@Override
	public static void update(Retrait retrait) //UPDATE
	{
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(UPDATE);
			
			statement.setString(1, retrait.getRue());
			statement.setString(2, retrait.getCodePostal());
			statement.setString(3, retrait.getVille());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	@Override
	public static void delete(int id) //DELETE
	{
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(DELETE);
			statement.setInt(1,id);
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			
		}
	}
}