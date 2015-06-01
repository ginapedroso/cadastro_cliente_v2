package br.com.cadastro.service;

import br.com.cadastro.pojo.Cliente;

public class ServiceLocator {

	public static ClienteService getClienteService(){
		return new ClienteService();
	}

}
