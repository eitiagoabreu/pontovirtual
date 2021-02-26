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
		String consultar = "SELECT\r\n"
				+ "m.entrada AS ex1,\r\n"
				+ "CASE WHEN m.saida < h.entrada THEN m.saida ELSE h.entrada END AS ex2\r\n"
				+ "FROM tblmarcacoesfeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.entrada < h.entrada \r\n"
				+ "AND m.id >= h.id\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT \r\n"
				+ "h.saida AS ex1,\r\n"
				+ "CASE WHEN m.saida > (SELECT haux.entrada FROM tblhorariotrabalho AS haux WHERE haux.id = h.id+1) THEN (SELECT haux.entrada FROM tblhorariotrabalho AS haux WHERE haux.id = h.id+1) ELSE m.saida END AS ex2\r\n"
				+ "FROM tblmarcacoesfeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.saida > h.saida AND m.saida > h.entrada\r\n"
				+ "AND m.id <= h.id";
		
		return consultar;
	}
	
}
