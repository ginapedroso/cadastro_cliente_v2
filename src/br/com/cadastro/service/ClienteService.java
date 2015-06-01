package br.com.cadastro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.cadastro.dao.ClienteDAO;
import br.com.cadastro.model.BaseService;
import br.com.cadastro.pojo.Cliente;

public class ClienteService extends BaseService<Cliente> {

	@Override
	public void create(Cliente pojo) {
		ClienteDAO dao = new ClienteDAO();
		dao.create(pojo);
	}

	@Override
	public void update(Cliente pojo) {
		ClienteDAO dao = new ClienteDAO();
		dao.update(pojo);
	}

	@Override
	public void delete(Long id) {
		ClienteDAO dao = new ClienteDAO();
		dao.delete(id);
	}

	@Override
	public Cliente readById(Long id) {
		ClienteDAO dao = new ClienteDAO();
		return dao.readById(id);
	}

	@Override
	public List<Cliente> readByCriteria(Map<String, Object> criteria) {
		ClienteDAO dao = new ClienteDAO();
		return dao.readByCriteria(criteria);
	}

}
