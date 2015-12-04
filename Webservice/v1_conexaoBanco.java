package webservice;

import java.*;
import java.sql.*;

//CLIENTE, email e senha

public class conexaoBanco {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
	
            boolean result;
            
            result = execLogin("teste","testando");
            System.out.print(result);
            
	}
	
	public static boolean execLogin(String email, String senha) { // retorna true ou false para solicita��o de login
	
		Connection conn;
		
		//Verificando se o driver JDBC est� instalado e pode ser utilizado (testa o servidor onde est� rodando)
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("Erro Driver: ");
			System.err.println(e.getMessage());
		}

		try {
			conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://i9yueekhr9.database.windows.net; databaseName = juliet","TSI","SistemasInternet123");
                        
                        PreparedStatement p = conn.prepareStatement(
                                "SELECT [idCliente]" +
                                "FROM [dbo].[Cliente]" +
                                "WHERE 1 = 1" +
                                "AND [emailCliente] = ?" +
                                "AND [senhaCliente] = ?");
							 
                        p.setString(1, email);
                        p.setString(2, senha);

			ResultSet rs = p.executeQuery(); //Resultset retorna resultados da query de Login

			//System.out.println("Resultado:");

			//Loop retornando todos os resultados
			// while (rs.next()) {
				// String s = rs.getString("LOGIN"); //Colocando o valor do campo "LOGIN" em uma String
				// int i = rs.getInt("ID");		  //Colocando o valor do campo "ID" em uma Inteiro
				// System.out.println(s + " " + i);
			// }
			
			if (!rs.isBeforeFirst()) {    
                            System.out.println("Cliente n�o encontrado!");
                            
                            p.close();
                            conn.close();

                            return false;
                            
                        } else {
                            System.out.println("Cliente encontrado com sucesso!");
                            
                            p.close();
                            conn.close();

                            return true;
                        }

		} catch(SQLException ex) {
			System.err.println("Erro Conn: " + ex.getMessage());
                        return false;
		}
	}
}
