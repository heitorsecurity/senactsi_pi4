package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/listaCategoria")
public class ListaCategoria {

    public List<Categoria> selectCategoria() throws Exception {
        Connection conn = null;
        PreparedStatement p = null;
        Categoria cat = null;
        List<Categoria> resultadoCategoria = new ArrayList<Categoria>();

        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                    "SELECT    [idCategoria]"
                    + ", [nomeCategoria]"
                    + ", [descCategoria]"
                    + "FROM [dbo].[Categoria]");

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                cat = new Categoria();

                cat.setIdCategoria(rs.getInt("idCategoria"));
                cat.setNomeCategoria(rs.getString("nomeCategoria"));
                cat.setDescCategoria(rs.getString("descCategoria"));
                
                resultadoCategoria.add(cat);
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
        return resultadoCategoria;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetalheCategoria() {
        List<Categoria> categ2 = new ArrayList<Categoria>();

        try {
            categ2 = selectCategoria();
        } catch (Exception e) {
            return Response.status(500).entity("{\"Erro\":\"Problemas na conexao com o Servidor. Tente novamente!\"}").build();
        }
        if (categ2.size() <= 0) {
            return Response.status(404).entity("{\"Erro\":\"Nenhuma Categoria nao encontrada!\"}").build();
        }
        return Response.status(200).entity(categ2).build();
    }
}
