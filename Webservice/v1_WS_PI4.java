import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
@Path("/v1")
public class WS_PI4 {
 
    /**
     *
     * @param loginUsuario
     * @param senhaUsuario
     * @return
     * @throws SQLException
     */
    @GET
    @Path("/login/{param}")
    public Response loginCliente(@PathParam("login") String loginUsuario, @PathParam("senha") String senhaUsuario) throws SQLException {

        Connection conn = null;
        PreparedStatement p = null;

        try {
            conn = Database.get().conn();  

            p = conn.prepareStatement(
            "SELECT [idCliente]" +
            "FROM [dbo].[Cliente]" +
            "WHERE 1 = 1" +
            "AND [emailCliente] = ?" +
            "AND [senhaCliente] = ?");

            p.setString(1, loginUsuario);
            p.setString(2, senhaUsuario);

            ResultSet rs = p.executeQuery(); //Resultset retorna resultados da query de Login

            if (!rs.isBeforeFirst()) {    
                //System.out.println("Cliente n„o encontrado!");
                //return false;
                return Response.status(401).entity("").build(); //401 - N„o autorizado
            } else {
                //System.out.println("Cliente encontrado com sucesso!");
                //return true;
                return Response.status(200).entity("").build(); //200 - Sucesso
            }
        } catch(SQLException ex) {
            System.err.println("Erro Conn: " + ex.getMessage());
            //return false;
            return Response.status(401).entity("").build(); //401 - N„o autorizado
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            //return false;
            return Response.status(401).entity("").build(); //401 - N„o autorizado
        } finally {
            if (p != null){
                p.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }
	
    /**
     *
     * @param nomeCompletoCliente
     * @param emailCliente
     * @param senhaCliente
     * @param CPFCliente
     * @return
     * @throws SQLException
     */
    @POST
    @Path("/cadastroC/{param}")
    public Response gravarCliente(@PathParam("nomeCompletoCliente") String nomeCompletoCliente,
                            @PathParam("emailCliente") String emailCliente,
                            @PathParam("senhaCliente") String senhaCliente,
                            @PathParam("CPFCliente") String CPFCliente
                            /*,String celularCliente = null,
                            String telComercialCliente = null,
                            String telResidencialCliente = null,
                            Date dtNascCliente = null,
                            Bool recebeNewsletter = null*/) throws SQLException {

        Connection conn = null;
        PreparedStatement p = null;

        try {
            conn = Database.get().conn(); 

            p = conn.prepareStatement(
                            "INSERT INTO [dbo].[Cliente] VALUES" +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?))");

            p.setString(1, nomeCompletoCliente);
            p.setString(2, emailCliente);
            p.setString(3, senhaCliente);
            p.setString(4, CPFCliente);
            /*p.setString(5, celularCliente);
            p.setString(6, telComercialCliente);
            p.setString(7, telResidencialCliente);
            p.setString(8, dtNascCliente);
            p.setString(9, recebeNewsletter);*/

            p.executeUpdate();
            return Response.status(200).entity("").build(); //401 - Sucesso
        } catch(SQLException ex) {
            System.err.println("Erro Conn: " + ex.getMessage());
            //return false; //CÛdigo erro?
            return Response.status(303).entity("").build(); //303 - See other
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            //return false; //CÛdigo erro?
            return Response.status(303).entity("").build(); //303 - See other
        } finally {
            if (p != null){
                p.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }
    
    /**
     *
     * @param idCliente
     * @param nomeEndereco
     * @param logradouroEndereco
     * @param numeroEndereco
     * @param CEPEndereco
     * @param cidadeEndereco
     * @return
     * @throws SQLException
     */
    @POST
    @Path("/cadastroE/{param}")
    public Response gravarEnderecoCliente(@PathParam("idCliente") int idCliente,  // retorna true ou false para cria√ß√£o de Endere√ßo
                                        @PathParam("nomeEndereco") String nomeEndereco,
                                        @PathParam("logradouroEndereco") String logradouroEndereco,
                                        @PathParam("numeroEndereco") String numeroEndereco,
                                        @PathParam("CEPEndereco") String CEPEndereco,
                                        @PathParam("cidadeEndereco") String cidadeEndereco
                                        /*,String complementoEndereco = null,
                                        String PaisEndereco = null,
                                        Date UFEndereco = null*/) throws SQLException { 

        Connection conn = null;
        PreparedStatement p = null;

        try {
            conn = Database.get().conn();  

            p = conn.prepareStatement(
            "INSERT INTO [dbo].[Endereco] VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?))");

            p.setInt(1, idCliente);
            p.setString(2, nomeEndereco);
            p.setString(3, logradouroEndereco);
            p.setString(4, numeroEndereco);
            p.setString(5, CEPEndereco);
            //p.setString(6, complementoEndereco);
            p.setString(7, cidadeEndereco);
            //p.setString(8, PaisEndereco);
            //p.setString(9, UFEndereco);

            p.executeUpdate();
            return Response.status(200).entity("").build(); //401 - Sucesso
        } catch(SQLException ex) {
            System.err.println("Erro Conn: " + ex.getMessage());
            //return false; //CÛdigo erro?
            return Response.status(303).entity("").build(); //303 - See other
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            //return false; //CÛdigo erro?
            return Response.status(303).entity("").build(); //303 - See other
        } finally {
            if (p != null){
                p.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }
        
    public static class Database {
        private static Database instance = null;

        private Database () {};

        public static Database get () {
            if (instance == null){
                instance = new Database();
            }
            return instance;
        }

        public Connection conn () throws Exception {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://i9yueekhr9.database.windows.net;user=TSI@i9yueekhr9.database.windows.net;password=SistemasInternet123;database=juliet");

            return conn;
        }
    }
}