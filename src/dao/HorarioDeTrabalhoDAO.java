package dao;

import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConexaoSQLite;

/**
 * Classe responsavel por toda operação com o banco de dados
 * @author Tiago Abreu
 * @version 1.0
 */

public class HorarioDeTrabalhoDAO {

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
	
	
	public HorarioDeTrabalhoDAO(ConexaoSQLite pConexaoSQLite)
	{
		this.conexaoSQLite = pConexaoSQLite;
	}
	
	public void criarTabelaHorarioDeTrabalho()
	{
		String sql = "CREATE TABLE IF NOT EXISTS tblHorarioTrabalho"
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
		String insert = "INSERT INTO tblHorarioTrabalho ("
				+ "entrada,"
				+ "saida"
				+ ") VALUES (?,?)";
		
		return insert;
	}
	
	
	public String consultar()
	{
		String consultar = "SELECT entrada, saida FROM tblHorarioTrabalho";
		
		return consultar;
	}
	
	public String countRegistros()
	{
		String consultar = "SELECT COUNT(*) AS count FROM tblHorarioTrabalho";
		
		return consultar;
	}
	
	public void limpar()
	{
		String sql = "DELETE FROM tblHorarioTrabalho";
		
		//executando o sql de criar tabela
		boolean conectou = false;
		
		try {
			conectou = this.conexaoSQLite.conectar();
			
			Statement stmt = this.conexaoSQLite.criarStatement();
			
			stmt.execute(sql);
			System.out.println("Limpou 'tblHorarioTrabalho'");
		}catch(SQLException e) {
			//mensagem de erro na criacao da tabela
			
		} finally {
			if(conectou) {
				this.conexaoSQLite.desconectar();
			}
		}
	}

	
}
