package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSQLite {

	private Connection conexao;
	
	/**
	 * Conecta a um banco de dados e cria o banco se nao existir.
	 * @return
	 */
	public boolean conectar() {
		try {
			String url = "jdbc:sqlite:database/sqlite.db";
			
			this.conexao = DriverManager.getConnection(url);
			
		} catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		System.out.println("Conectou");
		return true;
	}
	
	public boolean desconectar() {
		try 
		{
			
			if(this.conexao.isClosed() == false)
			{
				this.conexao.close();
			}
			
		} 
		catch(SQLException e) 
		{
			System.err.println(e.getMessage());
			return false;
		}
		
		System.out.println("Desconectou");
		return true;
	}
	
	/**
	 * Cria os Statemens para nosso sqls serem executados
	 * @return
	 */
	public Statement criarStatement()
	{
		try {
				return this.conexao.createStatement();
		} catch(SQLException e) {
			return null;
		}
	}
	
	
	public PreparedStatement criarPreparedStatement(String sql)
	{
		try {
				return this.conexao.prepareStatement(sql);
		} catch(SQLException e) {
			return null;
		}
	}
	
	
	public Connection getConexao()
	{
		return this.conexao;
	}
}
