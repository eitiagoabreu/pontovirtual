package dao;

import conexao.ConexaoSQLite;

/**
 * Classe responsavel pelo calculo de Horas Extras
 * @author Tiago Abreu
 * @version 1.0
 */

public class HoraExtraDAO {
	
	public HoraExtraDAO(ConexaoSQLite pConexaoSQLite)
	{
	}
	
	public String consultar()
	{
		String consultar = "SELECT * FROM (\r\n"
				+ "SELECT m.entrada AS ex1, \r\n"
				+ "CASE WHEN m.entrada < h.entrada THEN h.entrada ELSE h.saida END AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE (m.entrada < h.entrada AND m.saida > h.entrada) OR (m.entrada < h.entrada AND m.saida < h.saida)\r\n"
				+ "ORDER BY h.entrada ASC\r\n"
				+ "LIMIT 1\r\n"
				+ ") AS E1\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT ex1, ex2 FROM (\r\n"
				+ "(\r\n"
				+ "SELECT h.saida AS ex1\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.saida > h.saida AND m.saida > h.entrada\r\n"
				+ "ORDER BY h.entrada ASC\r\n"
				+ "LIMIT 1\r\n"
				+ ") AS EE1,\r\n"
				+ "(\r\n"
				+ "SELECT \r\n"
				+ "h.entrada AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.saida > h.saida \r\n"
				+ "ORDER BY h.entrada DESC   \r\n"
				+ "LIMIT 1\r\n"
				+ ") AS EE2\r\n"
				+ ") WHERE ex1 < ex2\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT * FROM (\r\n"
				+ "SELECT \r\n"
				+ "h.saida AS ex1,\r\n"
				+ "m.saida AS ex2\r\n"
				+ "FROM tblMarcacoesFeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.saida > h.saida \r\n"
				+ "ORDER BY h.entrada DESC   \r\n"
				+ "LIMIT 1\r\n"
				+ ") AS E3";
		
		return consultar;
	}
	
}
