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
		String consultar = "SELECT CASE WHEN (SELECT maux.saida FROM tblmarcacoesfeitas AS maux WHERE maux.id = m.id-1 AND maux.saida > h.entrada) < m.entrada THEN (SELECT maux.saida FROM tblmarcacoesfeitas AS maux WHERE maux.id = m.id-1) ELSE h.entrada END AS ea1, m.entrada AS ea2 \r\n"
				+ "FROM tblmarcacoesfeitas AS m, tblHorarioTrabalho AS h\r\n"
				+ "WHERE m.entrada > h.entrada AND m.entrada < h.saida \r\n"
				+ "AND ((CASE WHEN m.entrada >= 0 AND m.saida <= 12 THEN 'AM' WHEN m.entrada > 12 AND m.saida <= 18  THEN 'PM' ELSE 'PN' END) = (CASE WHEN h.entrada >= 00 OR h.saida <= 12   THEN 'AM' \r\n"
				+ "WHEN h.entrada > 12 AND h.saida <= 18  THEN 'PM' ELSE 'PN' END) \r\n"
				+ "AND (SELECT COUNT(*) FROM tblmarcacoesfeitas) <= 1)\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT m.saida AS ea1,h.saida AS ea2 \r\n"
				+ "FROM tblmarcacoesfeitas AS m, tblHorarioTrabalho AS h \r\n"
				+ "WHERE m.saida < h.saida AND m.entrada <= h.saida AND ((CASE WHEN m.entrada >= 0 AND m.saida <= 12 OR (SELECT COUNT(*) FROM tblmarcacoesfeitas) <= 1 THEN 'AM' \r\n"
				+ "WHEN m.entrada > 12 AND m.saida <= 18 OR (SELECT COUNT(*) FROM tblmarcacoesfeitas) <= 1 THEN 'PM' ELSE 'PN' END) = (CASE WHEN h.entrada >= 0 AND h.saida <= 12   THEN 'AM' \r\n"
				+ "WHEN h.entrada > 12 AND h.saida <= 18   THEN 'PM' ELSE 'PN' END) \r\n"
				+ "OR (SELECT COUNT(*) FROM tblmarcacoesfeitas) <= 1)";
			
		return consultar;
	}
}
