package dao;

import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoSQLite;

/**
 * Classe responsavel por toda operação com o banco de dados
 * @author Tiago Abreu
 * @version 1.0
 */


public class MarcacoesFeitasDAO {
	
	private final ConexaoSQLite conexaoSQLite;
	
	private int id;
	private String entrada;
	private String saida;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}
	
	
	public MarcacoesFeitasDAO(ConexaoSQLite pConexaoSQLite)
	{
		this.conexaoSQLite = pConexaoSQLite;
	}
	
	public void criarTabelaMarcacoesFeitas()
	{
		String sql = "CREATE TABLE IF NOT EXISTS tblMarcacoesFeitas"
				+ "("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "entrada TEXT NOT NULL,"
				+ "saida TEXT NOT NULL"
				+ ");";
		
		//executando o sql de criar tabela
		boolean conectou = false;
		
		try {
			conectou = this.conexaoSQLite.conectar();
			
			Statement stmt = this.conexaoSQLite.criarStatement();
			
			stmt.execute(sql);
			System.out.println("Criou Tabela 'tblHorarioTrabalho'");
		}catch(SQLException e) {
			//mensagem de erro na criacao da tabela
			
		} finally {
			if(conectou) {
				this.conexaoSQLite.desconectar();
			}
		}
	}
	
	
	public String insert()
	{
		String insert = "INSERT INTO tblMarcacoesFeitas ("
				+ "entrada,"
				+ "saida"
				+ ") VALUES (?,?)";
		
		return insert;
	}
	
	
	public String consultar()
	{
		String consultar = "SELECT entrada, saida FROM tblMarcacoesFeitas";
		
		return consultar;
	}
	
	public void limpar()
	{
		String sql = "DELETE FROM tblMarcacoesFeitas";
		
		//executando o sql de criar tabela
		boolean conectou = false;
		
		try {
			conectou = this.conexaoSQLite.conectar();
			
			Statement stmt = this.conexaoSQLite.criarStatement();
			
			stmt.execute(sql);
			System.out.println("Limpou 'tblMarcacoesFeitas'");
		}catch(SQLException e) {
			//mensagem de erro na criacao da tabela
			
		} finally {
			if(conectou) {
				this.conexaoSQLite.desconectar();
			}
		}
	}

}
