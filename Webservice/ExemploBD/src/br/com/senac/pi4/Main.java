package br.com.senac.pi4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {	
		
		try {
			selectExample();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			insertExample();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			deleteExample();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public static void selectExample () throws Exception {
		//exemplo de select
		Connection conn = null;
		PreparedStatement psta = null;
		try {
			conn = Database.get().conn();		
			psta = conn.prepareStatement("select * from Produto where nomeProduto like ?");
			psta.setString(1, "%Allstar%");
			ResultSet rs = psta.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("nomeProduto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psta != null)
				psta.close();
			if (conn != null)
				conn.close ();
		}
	}
	
	public static void insertExample () throws Exception {
		//exemplo de insert
		Connection conn = null;
		PreparedStatement psta = null;
		try {
			conn = Database.get().conn();		
			psta = conn.prepareStatement("insert into Usuario (loginUsuario, senhaUsuario, nomeUsuario, tipoPerfil, usuarioAtivo) values (?,?,?,?,?)");
			psta.setString(1, "fabioToledo");
			psta.setString(2, "abcd1234");
			psta.setString(3, "Fabio de Toledo Pereira");
			psta.setString(4, "A");
			psta.setByte(5, (byte)1);
			psta.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (psta != null)
				psta.close();
			if (conn != null)
				conn.close ();
		}
	}
	
	public static void deleteExample () throws Exception {
		//exemplo de delete
		Connection conn = null;
		PreparedStatement psta = null;
		try {
			conn = Database.get().conn();		
			psta = conn.prepareStatement("delete Usuario where loginUsuario = ?");
			psta.setString(1, "fabioToledo");			
			int x = psta.executeUpdate();
			System.out.println(x);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psta != null)
				psta.close();
			if (conn != null)
				conn.close ();
		}
	}

}
