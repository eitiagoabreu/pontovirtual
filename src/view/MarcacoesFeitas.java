
package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import conexao.ConexaoSQLite;
import dao.MarcacoesFeitasDAO;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe responsavel por gerenciar as marcações feitas.
 * @author Tiago Abreu
 * @version 1.0
 */

public class MarcacoesFeitas {


	private JFrame frmMarcacoesFeitas;
	private static JTable jTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarcacoesFeitas window = new MarcacoesFeitas();
					window.frmMarcacoesFeitas.setVisible(true);
					
					
					ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
					
					//Cria a tabela no banco de dados caso nao exista.
					MarcacoesFeitasDAO daoMarcacoesFeitas = new MarcacoesFeitasDAO(conexaoSQLite);
					daoMarcacoesFeitas.criarTabelaMarcacoesFeitas();
					
					//Carrega o jTable com os dados assim que a tela é carregada.
					consultar();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void show() {
		MarcacoesFeitas MF = new MarcacoesFeitas();
		MF.frmMarcacoesFeitas.setVisible(true);
		
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		//Cria a tabela no banco de dados caso nao exista.
		MarcacoesFeitasDAO daoMarcacoesFeitas = new MarcacoesFeitasDAO(conexaoSQLite);
		daoMarcacoesFeitas.criarTabelaMarcacoesFeitas();
		
		//Carrega o jTable com os dados assim que a tela é carregada.
		consultar();
	}
	
	/**
	 * Create the application.
	 */
	public MarcacoesFeitas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMarcacoesFeitas = new JFrame();
		frmMarcacoesFeitas.setResizable(false);
		frmMarcacoesFeitas.setTitle("Marcacoes Feitas");
		frmMarcacoesFeitas.setBounds(100, 100, 450, 300);
		frmMarcacoesFeitas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMarcacoesFeitas.getContentPane().setLayout(null);
		
		JLabel lblCaminhoTela = new JLabel("Ponto Virtual > Marca\u00E7\u00F5es Feitas");
		lblCaminhoTela.setBounds(10, 11, 414, 14);
		frmMarcacoesFeitas.getContentPane().add(lblCaminhoTela);
		
		JLabel lblEntrada = new JLabel("Entrada");
		lblEntrada.setBounds(10, 37, 46, 14);
		frmMarcacoesFeitas.getContentPane().add(lblEntrada);
		
		JLabel lblSaida = new JLabel("Sa\u00EDda");
		lblSaida.setBounds(10, 70, 46, 14);
		frmMarcacoesFeitas.getContentPane().add(lblSaida);
		
		final JFormattedTextField txtEntrada = new JFormattedTextField(Mascara("##:##"));
		txtEntrada.setBounds(66, 36, 110, 20);
		frmMarcacoesFeitas.getContentPane().add(txtEntrada);
		
		final JFormattedTextField txtSaida = new JFormattedTextField(Mascara("##:##"));
		txtSaida.setBounds(66, 67, 110, 20);
		frmMarcacoesFeitas.getContentPane().add(txtSaida);
		
		
		JButton btnMarcar = new JButton("Marcar");
		btnMarcar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( txtEntrada.getText().trim().length() < 5 || txtSaida.getText().trim().length() < 5)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Preencha os campos Entrada e Saída", "Validação", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				cadastrar(txtEntrada.getText(), txtSaida.getText());
				txtEntrada.setText("");
				txtSaida.setText("");
			}
		});
		btnMarcar.setBounds(66, 99, 110, 23);
		frmMarcacoesFeitas.getContentPane().add(btnMarcar);
		
		jTable = new JTable();
		jTable.setBounds(10, 133, 414, 82);
		frmMarcacoesFeitas.getContentPane().add(jTable);
		
		JButton btnLimpar = new JButton("Limpar Todas Marca\u00E7\u00F5es");
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(224, 226, 200, 23);
		frmMarcacoesFeitas.getContentPane().add(btnLimpar);
		
		
	}
	
	/**
	 * Funcao responsavel por criar a mascara (00:00) no campo de horas.
	 * @param Mascara
	 * @return
	 */
	public MaskFormatter Mascara(String Mascara){
        
        MaskFormatter F_Mascara = new MaskFormatter();
        try{
            F_Mascara.setMask(Mascara); //Atribui a mascara
            F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento 
        }
        catch (Exception excecao) {
        excecao.printStackTrace();
        } 
        return F_Mascara;
	} 
	
	/**
	 * Funcao responsavel por cadastrar as Marcações no banco de dados.
	 * @param pEntrada
	 * @param pSaida
	 */
	public void cadastrar(String pEntrada, String pSaida)
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		MarcacoesFeitasDAO daoMarcacoesFeitas = new MarcacoesFeitasDAO(conexaoSQLite);
		

		daoMarcacoesFeitas.setEntrada(pEntrada);
		daoMarcacoesFeitas.setSaida(pSaida);
		
		conexaoSQLite.conectar();
		
		PreparedStatement preparedStatement = conexaoSQLite.criarPreparedStatement(daoMarcacoesFeitas.insert());
		
		try {
			
			preparedStatement.setString(1, daoMarcacoesFeitas.getEntrada());
			preparedStatement.setString(2, daoMarcacoesFeitas.getSaida());
			
			int result = preparedStatement.executeUpdate();
			
			if(result == 1)
			{
				consultar();
			} else {
				
			}
			
		}catch(SQLException e) {
			
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			conexaoSQLite.desconectar();
		}
		
	}
	
	/**
	 * Função responsavel por consultar os dados no banco de dados para carregar no jTable.
	 */
	public static void consultar()
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		ResultSet rs = null;
		
		MarcacoesFeitasDAO daoMarcacoesFeitas = new MarcacoesFeitasDAO(conexaoSQLite);
	
		conexaoSQLite.conectar();
		
		PreparedStatement preparedStatement = conexaoSQLite.criarPreparedStatement(daoMarcacoesFeitas.consultar());
		
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
	
	/**
	 * Funcao responsavel por limpar a base de dados para realizar novos testes.
	 */
	public static void limpar()
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		MarcacoesFeitasDAO daoMarcacoesFeitas = new MarcacoesFeitasDAO(conexaoSQLite);
		daoMarcacoesFeitas.limpar();
		
		consultar();
		
	}
}
