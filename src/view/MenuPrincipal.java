package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe responsavel por exibir a tela de menu principal.
 * @author Tiago Abreu
 * @version 1.0
 */

public class MenuPrincipal {

	private JFrame frmPontoVirtual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.frmPontoVirtual.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPontoVirtual = new JFrame();
		frmPontoVirtual.setResizable(false);
		frmPontoVirtual.setTitle("Ponto Virtual | Menu Principal");
		frmPontoVirtual.setBounds(100, 100, 450, 300);
		frmPontoVirtual.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPontoVirtual.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 22);
		frmPontoVirtual.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Ponto Virtual");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Marca\u00E7\u00F5es Feitas");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("clicou no botao");
				MarcacoesFeitas MF = new MarcacoesFeitas();
				MF.show();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		
		
		
		JMenu mnNewMenu_1 = new JMenu("Relat\u00F3rios");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Atraso");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Atraso A = new Atraso();
				A.show();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Hora Extra");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				HoraExtra HE = new HoraExtra();
				HE.show();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_2 = new JMenu("Configura\u00E7\u00F5es");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Horario de Trabalho");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("clicou no botao");
				HorarioDeTrabalho HT = new HorarioDeTrabalho();
				HT.show();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JLabel lblNewLabel = new JLabel("Bem-vindo(a) ao Ponto Virtual iZeus Jr.  :)");
		lblNewLabel.setBounds(10, 57, 414, 14);
		frmPontoVirtual.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Utilize o menu superior para acessar as ferramentas disponiveis");
		lblNewLabel_1.setBounds(10, 82, 414, 14);
		frmPontoVirtual.getContentPane().add(lblNewLabel_1);
	}
}
