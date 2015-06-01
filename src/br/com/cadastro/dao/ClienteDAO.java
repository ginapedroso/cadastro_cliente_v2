package br.com.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import br.com.cadastro.model.BaseCrud;
import br.com.cadastro.model.ConnectionManager;
import br.com.cadastro.pojo.Cliente;

public class ClienteDAO implements BaseCrud<Cliente> {

	private String sql_create = "INSERT INTO cliente(nome) values(?)";
	private String sql_delete = "DELETE FROM cliente WHERE id = ?";
	private String sql_update = "UPDATE cliente SET nome=? WHERE id=?";
	private String sql_readById = "SELECT * FROM cliente WHERE id=?";
	private String sql_criteria = "SELECT * FROM cliente WHERE true";

	@Override
	public void create(Cliente pojo) {
		try {
			Connection conn = new ConnectionManager().getIntance()
					.getConnection();
			PreparedStatement st = conn.prepareStatement(sql_create);
			int i = 0;
			st.setString(++i, pojo.getNome());
			st.execute();
			st.close();
			conn.close();

			JOptionPane.showMessageDialog(null, "Registro gravado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro ao gravar o registro");
		}

	}

	@Override
	public void update(Cliente pojo) {
		try {
			Connection conn = new ConnectionManager().getIntance()
					.getConnection();
			PreparedStatement st = conn.prepareStatement(sql_update);
			int i = 0;
			st.setString(++i, pojo.getNome());
			st.setLong(++i, pojo.getId());
			st.execute();
			st.close();
			conn.close();

			JOptionPane.showMessageDialog(null, "Registro alterado com sucesso");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "erro ao alterar o registro");
		}

	}

	@Override
	public void delete(Long id) {
		try {
			Connection conn = new ConnectionManager().getIntance()
					.getConnection();
			PreparedStatement st = conn.prepareStatement(sql_delete);
			int i = 0;
			st.setLong(++i, id);
			st.execute();
			st.close();
			conn.close();

			JOptionPane.showMessageDialog(null, "Registro deletado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro ao deletar o registro");
		}

	}

	@Override
	public Cliente readById(Long id) {
		Cliente cliente = null;
		try {
			Connection conn = new ConnectionManager().getIntance()
					.getConnection();
			PreparedStatement st = conn.prepareStatement(sql_readById);
			int i = 0;
			st.setLong(++i, id);
			ResultSet rs = st.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					cliente = extract(rs);
				}
			}
			st.close();
			conn.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "erro na pesquisa por id");
		}
		return cliente;
	}

	@Override
	public List<Cliente> readByCriteria(Map<String, Object> criteria) {
		List<Cliente> lista = new ArrayList<Cliente>();

		String sql = sql_criteria;
		if (criteria != null) {
			String criteriaNome = (String) criteria.get("nome");
			if (criteriaNome != null && criteriaNome.trim().length() > 0) {
				sql += " and nome ilike '%" + criteriaNome.toUpperCase()
						+ "%'";
			}

			String criteriaContato = (String) criteria.get("contato");
			if (criteriaContato != null && criteriaContato.trim().length() > 0) {
				sql += " and contato ilike %'" + criteriaContato.toUpperCase()
						+ "%'";
			}
		}

		try {
			Connection conn = new ConnectionManager().getIntance()
					.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					Cliente cliente = extract(rs);
					lista.add(cliente);
				}
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro na pesquisa por criterio ");
		}
		return lista;
	}

	private Cliente extract(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getLong("id"));
		cliente.setNome(rs.getString("nome"));
		return cliente;
	}
}
