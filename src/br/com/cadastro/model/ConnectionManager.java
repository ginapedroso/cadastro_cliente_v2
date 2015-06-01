package br.com.cadastro.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectionManager {

	private String user = "";
	private String password = "";
	private String driver = "";
	private String url = "";
	private Connection conn = null;
	
	private static ConnectionManager instance = null;
	
	public ConnectionManager() {
		this.user = "postgres";
		this.password = "gina";
		this.url = "jdbc:postgresql://localhost:5432/BD_CLIENTE";
		this.driver = "org.postgresql.Driver";
	}
	
	public static ConnectionManager getIntance() {
		if (instance == null){
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	public Connection getConnection(){
		try {
			if (conn == null || conn.isClosed()){
				this.createConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Banco de dados inexistente !!!");
		}
		return conn;
	}
	
	private void createConnection() throws Exception {
		Class.forName(driver);
		conn = DriverManager.getConnection(url,user,password);
	}

}
