package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registrarEndereco")
public class RegistroEndereco {

    public boolean insertEndereco(int idCliente,
                                String nomeEndereco,
                                String logradouroEndereco,
                                String numeroEndereco,
                                String CEPEndereco,
                                String cidadeEndereco,
                                String complementoEndereco, //NULL
                                String PaisEndereco, //NULL
                                String UFEndereco) //NULL
                                throws SQLException {
        Connection conn = null;
        PreparedStatement p = null;
        boolean enderecoRegistrado = false;
        
        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                "INSERT INTO [dbo].[Endereco] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            p.setInt(1, idCliente);
            p.setString(2, nomeEndereco);
            p.setString(3, logradouroEndereco);
            p.setString(4, numeroEndereco);
            p.setString(5, CEPEndereco);
            p.setString(6, complementoEndereco);
            p.setString(7, cidadeEndereco);
            p.setString(8, PaisEndereco);
            p.setString(9, UFEndereco);

            int gravacao = p.executeUpdate();

            if (gravacao > 0) {
                enderecoRegistrado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return enderecoRegistrado;
    }

    @GET
    @Path("/{idCliente}/{nomeEndereco}/{logradouroEndereco}/{numeroEndereco}/{CEPEndereco}/{cidadeEndereco}/{complementoEndereco}/{PaisEndereco}/{UFEndereco}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gravaEndereco(  @PathParam("idCliente") int idCliente,
                                    @PathParam("nomeEndereco") String nomeEndereco,
                                    @PathParam("logradouroEndereco") String logradouroEndereco,
                                    @PathParam("numeroEndereco") String numeroEndereco,
                                    @PathParam("CEPEndereco") String CEPEndereco,
                                    @PathParam("cidadeEndereco") String cidadeEndereco,
                                    @PathParam("complementoEndereco") String complementoEndereco, //NULL
                                    @PathParam("PaisEndereco") String PaisEndereco, //NULL
                                    @PathParam("UFEndereco") String UFEndereco) throws SQLException {

        try {
            if(insertEndereco(idCliente, nomeEndereco, logradouroEndereco, numeroEndereco, CEPEndereco, cidadeEndereco, complementoEndereco, PaisEndereco, UFEndereco)){
                //TRUE: Cliente registrado com sucesso
                return Response.status(200).entity("{\"Status\":\"Registrado!\"}").build();
            } else {
                //FALSE: Erro registrando Cliente
                return Response.status(404).entity("{\"Status\":\"Erro!\"}").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity(null).build();
        }
    }
}