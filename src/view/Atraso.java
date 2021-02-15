package view;

import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexao.ConexaoSQLite;
import dao.AtrasoDAO;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe responsavel por exibir a tela de relatorios de Atrasos.
 * @author Tiago Abreu
 * @version 1.0
 */

public class Atraso {

	private JFrame frmAtraso;
	private static JTable jTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Atraso window = new Atraso();
					window.frmAtraso.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void show() {
		Atraso A = new Atraso();
		A.frmAtraso.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Atraso() {
		initialize();
		consultar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAtraso = new JFrame();
		frmAtraso.setResizable(false);
		frmAtraso.setTitle("Atraso");
		frmAtraso.setBounds(100, 100, 450, 300);
		frmAtraso.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAtraso.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Relatorios > Atraso");
		lblNewLabel_2.setBounds(10, 11, 414, 14);
		frmAtraso.getContentPane().add(lblNewLabel_2);
		
		jTable = new JTable();
		jTable.setBounds(10, 36, 414, 214);
		frmAtraso.getContentPane().add(jTable);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				consultar();
			}
		});
		btnAtualizar.setBounds(335, 7, 89, 23);
		frmAtraso.getContentPane().add(btnAtualizar);
	}
	
	/**
	 * Função responsavel por consultar os dados no banco de dados para carregar no jTable.
	 */
	public static void consultar()
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		ResultSet rs = null;
		
		AtrasoDAO daoAtraso = new AtrasoDAO(conexaoSQLite);
	
		conexaoSQLite.conectar();
		
		PreparedStatement preparedStatement = conexaoSQLite.criarPreparedStatement(daoAtraso.consultar());
		
		try
		{
			
			rs = preparedStatement.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();

			
			// names of columns
		    Vector<String> columnNames = new Vector<String>();
		    int columnCount = metaData.getColumnCount();
		    for (int column = 1; column <= columnCount; column++) {
		        columnNames.add(metaData.getColumnName(column));
		    }
		    
			// data of the table
		    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		    while (rs.next()) {
		        Vector<Object> vector = new Vector<Object>();
		        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		            vector.add(rs.getObject(columnIndex));
		        }
		        data.add(vector);
		    }
		    
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
			jTable.setModel(tableModel);
			
		} 
		catch(SQLException e)
		{
			
		}
		finally 
		{
			try {
				rs.close();
				preparedStatement.close();
			} catch(SQLException e)
			{
				
			}
			conexaoSQLite.desconectar();
		}
		
	}
}
