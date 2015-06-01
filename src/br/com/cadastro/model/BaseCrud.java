package br.com.cadastro.model;

import java.util.List;
import java.util.Map;

public interface BaseCrud<E extends BasePojo> {
	
	public void create(E BasePojo);
	
	public void update(E BasePojo);
	
	public void delete(Long id);
	
	public E readById(Long id);
	
	public List<E> readByCriteria(Map<String,Object> criteria);

}
