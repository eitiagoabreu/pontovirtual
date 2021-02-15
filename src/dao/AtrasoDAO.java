package dao;

import conexao.ConexaoSQLite;

/**
 * Classe responsavel pelo calculo de Atrasos
 * @author Tiago Abreu
 * @version 1.0
 */

public class AtrasoDAO {
	
	public AtrasoDAO(ConexaoSQLite pConexaoSQLite)
	{
	}	
	
	public String consultar()
	{
		String consultar = "SELECT * FROM (\r\n"
				+ "SELECT h.entrada AS ex1, m.entrada AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.entrada > h.entrada AND m.saida < h.saida\r\n"
				+ "ORDER BY h.entrada ASC\r\n"
				+ "LIMIT 1\r\n"
				+ ") AS E1\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT * FROM (\r\n"
				+ "SELECT m.saida AS ex1, h.saida AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.saida < h.saida\r\n"
				+ "ORDER BY m.saida DESC\r\n"
				+ "LIMIT 1\r\n"
				+ ") AS E2\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT * FROM (\r\n"
				+ "SELECT h.entrada AS ex1, m.entrada AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE ((m.entrada < h.entrada AND m.saida < m.entrada) \r\n"
				+ "AND ((h.entrada > 12 AND h.saida > 12) OR (h.entrada > 12 AND m.entrada < 12)))\r\n"
				+ "OR \r\n"
				+ "((m.entrada < h.entrada AND m.saida > m.entrada AND m.entrada > h.saida) \r\n"
				+ "AND ((h.entrada > 12 AND m.entrada < 12) OR (h.entrada > 12 AND m.entrada < 12)))\r\n"
				+ " OR \r\n"
				+ "((m.entrada < h.entrada AND m.saida > m.entrada AND m.entrada < h.entrada and h.entrada > h.saida) \r\n"
				+ "AND ((h.entrada > 12 AND m.entrada < 12)))\r\n"
				+ "ORDER BY h.entrada ASC\r\n"
				+ "LIMIT 1\r\n"
				+ ") AS E3\r\n"
				+ "";
		
		return consultar;
	}
}
