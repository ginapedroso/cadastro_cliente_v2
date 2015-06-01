package br.com.cadastro.model;

import java.sql.ResultSet;

public abstract class BaseService<E extends BasePojo> implements BaseCrud<E> {

	public E extract(ResultSet rs) throws Exception{
		return null;
	}
}
