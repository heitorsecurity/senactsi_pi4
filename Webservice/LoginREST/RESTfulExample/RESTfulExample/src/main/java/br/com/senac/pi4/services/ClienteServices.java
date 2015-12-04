package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

@Path("/loginCliente")
public class ClienteServices {

    public Cliente selectCliente(String login, String senha) throws Exception {
        Connection conn = null;
        PreparedStatement p = null;
        Cliente cli = null;
        
        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                "SELECT TOP 1 [idCliente], [emailCliente], [nomeCompletoCliente]" +
                "FROM [dbo].[Cliente]" +
                "WHERE 1 = 1 AND [emailCliente] = ? AND [senhaCliente] = ?");
            
            p.setString(1, login);
            p.setString(2, senha);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                cli = new Cliente();
                cli.setIdCliente(rs.getInt("idCliente"));
                cli.setEmailCliente(rs.getString("emailCliente"));
                cli.setNomeCompletoCliente(rs.getString("nomeCompletoCliente"));
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
        return cli;
    }

    @GET
    @Path("/{loginCliente}/{senhaCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("loginCliente") String loginCliente, @PathParam("senhaCliente") String senhaCliente) {
        Cliente client = null;

        try {
            client = selectCliente(loginCliente, senhaCliente);
        } catch (Exception e) {
            return Response.status(500).entity(null).build();
        }
        if (client == null) {
            return Response.status(404).entity("Usuário não encontrado!").build();
        }
        return Response.status(200).entity(client).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarCliente(Cliente client) {

        Gson gson = new Gson();
        String jsonUser = gson.toJson(client);
        System.out.println("salvando usuario " + jsonUser);
        return Response.status(200).entity("").build();
    }
}