package br.com.cadastro.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.cadastro.model.ConnectionManager;
import br.com.cadastro.model.SimpleTable;
import br.com.cadastro.pojo.Cliente;
import br.com.cadastro.service.ServiceLocator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JPanel conteudoPanel;

	private JTable tabela;
	private DefaultTableModel modelo = new DefaultTableModel();
	private JScrollPane scrollPane;
	
	private SimpleTable tab;

	private final int COL_ID = 0;
	private final int COL_NOME = 1;

	private final String filePath = "report/lista.jasper";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		conteudoPanel = new JPanel();
		contentPane.add(conteudoPanel, BorderLayout.CENTER);

		criarTabela();
		scrollPane = new JScrollPane(tabela);
		conteudoPanel.add(scrollPane);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastroCliente tela = new CadastroCliente();
				tela.setModal(true);
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);

				atualizaTabela();
			}
		});
		buttonPanel.add(btnNovo);

		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int linha = tabela.getSelectedRow();
				if (linha >= 0) {
					Long id = (Long) tabela.getValueAt(linha, COL_ID);
					ServiceLocator.getClienteService().delete(id);
					atualizaTabela();
					//modelo.removeRow(linha);
				} else {
					JOptionPane
							.showMessageDialog(null, "Linha nao selecionada");
				}
			}
		});
		buttonPanel.add(btnDeletar);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int linha = -1;
				linha = tabela.getSelectedRow();
				if (linha >= 0) {
					Long id = (Long) tabela.getValueAt(linha, COL_ID);
					AlteraCliente tela = new AlteraCliente(id);
					tela.setModal(true);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);

					atualizaTabela();
				} else {
					JOptionPane
							.showMessageDialog(null, "Linha nao selecionada");
				}
			}
		});
		buttonPanel.add(btnAlterar);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JasperPrint jp;
				try {
					jp = JasperFillManager.fillReport(filePath, null,
							ConnectionManager.getIntance().getConnection());
					JasperViewer jv = new JasperViewer(jp, false);
					jv.setVisible(true);
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		buttonPanel.add(btnImprimir);

		this.setLocationRelativeTo(null);
	}

	public void criarTabela() {
		tabela = new JTable(modelo);
		modelo.addColumn("id");
		modelo.addColumn("nome");
		getLista(modelo);
	}

	public void getLista(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		List<Cliente> lista = ServiceLocator.getClienteService()
				.readByCriteria(null);
		for (Cliente c : lista) {
			modelo.addRow(new Object[] { c.getId(), c.getNome() });
		}
	}

	public void atualizaTabela() {
		getLista(modelo);
		tabela = new JTable(modelo);
		scrollPane = new JScrollPane(tabela);
		contentPane.validate();
	}
}
