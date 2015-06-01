package br.com.cadastro.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.cadastro.pojo.Cliente;

public class SimpleTable extends AbstractTableModel{

	//constantes p/identificar colunas
    private final int ID = 0;
    private final int NOME = 1;
    
    private final String colunas[]={"Id","Nome"};
    private final List<Cliente> valores;
	
    public SimpleTable(List<Cliente> valores) {
		this.valores = valores;
	}
    
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return valores.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Cliente cliente = valores.get(linha);
		 
        //retorna o valor da coluna
        switch (coluna) {
        case ID:
            return cliente.getId();
        case NOME:
            return cliente.getNome();
        default:
            throw new IndexOutOfBoundsException("Coluna Inválida!!!");
        }
	}
	
	public int indexOf(Cliente empregado) {
        return valores.indexOf(empregado);
    }
	
	public void onAdd(Cliente empregado) {
        valores.add(empregado);
        fireTableRowsInserted(indexOf(empregado), indexOf(empregado));
    }

	public void onAddAll(List<Cliente> dadosIn) {
        valores.addAll(dadosIn);
        fireTableDataChanged();
    }
 
    public void onRemove(int rowIndex) {
        valores.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
 
    public void onRemove(Cliente empregado) {
        int indexBefore=indexOf(empregado);
        valores.remove(empregado);  
        fireTableRowsDeleted(indexBefore, indexBefore);
    }
}
