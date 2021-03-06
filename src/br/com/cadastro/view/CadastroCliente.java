package br.com.cadastro.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.print.attribute.HashAttributeSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import br.com.cadastro.pojo.Cliente;
import br.com.cadastro.service.ServiceLocator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;

public class CadastroCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CadastroCliente dialog = new CadastroCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CadastroCliente() {
		setTitle("Cadastrar Cliente");
		setBounds(100, 100, 459, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome:");
			lblNome.setBounds(34, 13, 76, 14);
			contentPanel.add(lblNome);
		}
		{
			txtNome = new JTextField();
			txtNome.setBounds(108, 10, 246, 20);
			contentPanel.add(txtNome);
			txtNome.setColumns(30);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGravar = new JButton("Gravar");
				btnGravar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (txtNome.getText() != null && txtNome.getText().trim().length() > 0){
							
							Cliente pojo = new Cliente();
							pojo.setNome(txtNome.getText().toString());
							
							Map<String,Object> criteria = new HashMap<String,Object>();
							criteria.put("nome",txtNome.getText().toString());
							List<Cliente> clientes = ServiceLocator.getClienteService().readByCriteria(criteria);
							if (clientes.size() == 0){
								ServiceLocator.getClienteService().create(pojo);
							}else{
								pojo.setId(clientes.get(0).getId());
								ServiceLocator.getClienteService().update(pojo);
							}
						}else{
							JOptionPane.showMessageDialog(null, "Atributo NOME invalido !!");
						}
					}
				});
				btnGravar.setActionCommand("OK");
				buttonPane.add(btnGravar);
				getRootPane().setDefaultButton(btnGravar);
			}
		}
	}

}
