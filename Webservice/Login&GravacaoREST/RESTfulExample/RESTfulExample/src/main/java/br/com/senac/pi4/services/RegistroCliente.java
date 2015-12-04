package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import java.sql.Date;

@Path("/registrarCliente")
public class RegistroCliente {

    public boolean insertCliente(String nomeCompletoCliente, String emailCliente, String senhaCliente, String CPFCliente, String celularCliente, String telComercialCliente, String telResidencialCliente, Date dtNascCliente, boolean recebeNewsletter) throws SQLException {
        Connection conn = null;
        PreparedStatement p = null;
        boolean clienteRegistrado = false;
        
        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                "INSERT INTO [dbo].[Cliente] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            p.setString(1, nomeCompletoCliente);
            p.setString(2, emailCliente);
            p.setString(3, senhaCliente);
            p.setString(4, CPFCliente);
            p.setString(5, celularCliente);
            p.setString(6, telComercialCliente);
            p.setString(7, telResidencialCliente);
            p.setDate  (8, dtNascCliente);
            p.setBoolean(9, recebeNewsletter); //True ou False na URL

            int gravacao = p.executeUpdate();

            if (gravacao > 0) {
                clienteRegistrado = true;
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
        return clienteRegistrado;
    }

    @GET
    @Path("/{nomeCompletoCliente}/{emailCliente}/{senhaCliente}/{CPFCliente}/{celularCliente}/{telComercialCliente}/{telResidencialCliente}/{dtNascCliente}/{recebeNewsletter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gravaCliente(@PathParam("nomeCompletoCliente") String nomeCompletoCliente,
                                 @PathParam("emailCliente") String emailCliente,
                                 @PathParam("senhaCliente") String senhaCliente,
                                 @PathParam("CPFCliente") String CPFCliente,
                                 @PathParam("celularCliente") String celularCliente, //NULL
                                 @PathParam("telComercialCliente") String telComercialCliente,//NULL
                                 @PathParam("telResidencialCliente") String telResidencialCliente,//NULL
                                 @PathParam("dtNascCliente") Date dtNascCliente,//NULL
                                 @PathParam("recebeNewsletter") boolean recebeNewsletter) throws SQLException {

        try {
            if(insertCliente(nomeCompletoCliente, emailCliente, senhaCliente, CPFCliente, celularCliente, telComercialCliente, telResidencialCliente, dtNascCliente, recebeNewsletter)){
                //TRUE: Cliente registrado com sucesso
                return Response.status(200).entity("Cliente registrado com Sucesso!").build();
            } else {
                //FALSE: Erro registrando Cliente
                return Response.status(404).entity("Erro na gravação de Cliente!").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity(null).build();
        }
    }
}