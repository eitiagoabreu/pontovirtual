package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import conexao.ConexaoSQLite;
import dao.HorarioDeTrabalhoDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe responsavel por gerenciar os horarios de trabalho.
 * @author Tiago Abreu
 * @version 1.0
 */

public class HorarioDeTrabalho {

	private JFrame frmHorarioDeTrabalho;
	private static JTable jTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HorarioDeTrabalho window = new HorarioDeTrabalho();
					window.frmHorarioDeTrabalho.setVisible(true);
					
					ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
					
					//Cria a tabela no banco de dados caso nao exista.
					HorarioDeTrabalhoDAO daoHorarioTrabalho = new HorarioDeTrabalhoDAO(conexaoSQLite);
					daoHorarioTrabalho.criarTabelaHorarioDeTrabalho();
					
					//Carrega o jTable com os dados assim que a tela é carregada.
					consultar();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void show() {
		HorarioDeTrabalho HT = new HorarioDeTrabalho();
		HT.frmHorarioDeTrabalho.setVisible(true);
		
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		//Cria a tabela no banco de dados caso nao exista.
		HorarioDeTrabalhoDAO daoHorarioTrabalho = new HorarioDeTrabalhoDAO(conexaoSQLite);
		daoHorarioTrabalho.criarTabelaHorarioDeTrabalho();
		
		//Carrega o jTable com os dados assim que a tela é carregada.
		consultar();
	}
	
	/**
	 * Create the application.
	 */
	public HorarioDeTrabalho() {
		initialize();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHorarioDeTrabalho = new JFrame();
		frmHorarioDeTrabalho.setResizable(false);
		frmHorarioDeTrabalho.setTitle("Horario De Trabalho");
		frmHorarioDeTrabalho.setBounds(100, 100, 450, 300);
		frmHorarioDeTrabalho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHorarioDeTrabalho.getContentPane().setLayout(null);
		
		JLabel lblEntrada = new JLabel("Entrada");
		lblEntrada.setBounds(10, 39, 46, 14);
		frmHorarioDeTrabalho.getContentPane().add(lblEntrada);
		
		JLabel lblSaida = new JLabel("Sa\u00EDda");
		lblSaida.setBounds(10, 72, 46, 14);
		frmHorarioDeTrabalho.getContentPane().add(lblSaida);
		
		jTable = new JTable();
		jTable.setBounds(10, 135, 414, 82);
		frmHorarioDeTrabalho.getContentPane().add(jTable);
		
		final JFormattedTextField txtEntrada = new JFormattedTextField(Mascara("##:##"));
		txtEntrada.setBounds(66, 36, 110, 20);
		frmHorarioDeTrabalho.getContentPane().add(txtEntrada);
		
		final JFormattedTextField txtSaida = new JFormattedTextField(Mascara("##:##"));
		txtSaida.setBounds(66, 69, 110, 20);
		frmHorarioDeTrabalho.getContentPane().add(txtSaida);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
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
		btnCadastrar.setBounds(66, 101, 110, 23);
		frmHorarioDeTrabalho.getContentPane().add(btnCadastrar);
		
		JLabel lblCaminhoTela = new JLabel("Configura\u00E7\u00E3o > Horario de Trabalho");
		lblCaminhoTela.setBounds(10, 11, 414, 14);
		frmHorarioDeTrabalho.getContentPane().add(lblCaminhoTela);
		
		JButton btnLimpar = new JButton("Limpar Todos Horarios");
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(224, 228, 200, 23);
		frmHorarioDeTrabalho.getContentPane().add(btnLimpar);
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
	 * Funcao responsavel por cadastrar os horarios de trabalho no banco de dados e restrigindo em apenas 3
	 * @param pEntrada
	 * @param pSaida
	 */
	public void cadastrar(String pEntrada, String pSaida)
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		HorarioDeTrabalhoDAO daoHorarioTrabalho = new HorarioDeTrabalhoDAO(conexaoSQLite);
		
		daoHorarioTrabalho.setEntrada(pEntrada);
		daoHorarioTrabalho.setSaida(pSaida);
		
		conexaoSQLite.conectar();
		
		PreparedStatement preparedStatement0 = conexaoSQLite.criarPreparedStatement(daoHorarioTrabalho.countRegistros());
		
		ResultSet rs = null;
		int count = 0;
		
		try
		{	
			rs = preparedStatement0.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch(SQLException e) {}
			
		System.out.println(count);
		
		if (count < 3) {
			PreparedStatement preparedStatement = conexaoSQLite.criarPreparedStatement(daoHorarioTrabalho.insert());
			
			try {
				
				preparedStatement.setString(1, daoHorarioTrabalho.getEntrada());
				preparedStatement.setString(2, daoHorarioTrabalho.getSaida());
				
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
			
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Permitido cadastrar somente 3 Horarios de Trabalho", "Permissão", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	/**
	 * Função responsavel por consultar os dados no banco de dados para carregar no jTable.
	 */
	public static void consultar()
	{
		ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
		
		ResultSet rs = null;
		
		HorarioDeTrabalhoDAO daoHorarioTrabalho = new HorarioDeTrabalhoDAO(conexaoSQLite);
	
		conexaoSQLite.conectar();
		
		PreparedStatement preparedStatement = conexaoSQLite.criarPreparedStatement(daoHorarioTrabalho.consultar());
		
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
		
		HorarioDeTrabalhoDAO daoHorarioTrabalho = new HorarioDeTrabalhoDAO(conexaoSQLite);
		daoHorarioTrabalho.limpar();
		
		consultar();
		
	}
}
