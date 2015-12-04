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

@Path("/gravaItemPedido")
public class GravaItemPedido {

    public boolean insertItemPedido(int idProduto,
                                int idPedido,
                                int qtdProduto,
                                double precoVendaItem) throws SQLException {
        Connection conn = null;
        PreparedStatement p = null;
        boolean itemPedidoGravado = false;
        
        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                "INSERT INTO [dbo].[ItemPedido] VALUES (?, ?, ?, ?)");

            p.setInt(1, idProduto);
            p.setInt(2, idPedido);
            p.setInt(3, qtdProduto);
            p.setDouble(4, precoVendaItem);

            int gravacao = p.executeUpdate();

            if (gravacao > 0) {
                itemPedidoGravado = true;
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
        return itemPedidoGravado;
    }

    @GET
    @Path("/{idProduto}/{idPedido}/{qtdProduto}/{precoVendaItem}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gravaItemPedido(@PathParam("idProduto") int idProduto,
                                    @PathParam("idPedido") int idPedido,
                                    @PathParam("qtdProduto") int qtdProduto,
                                    @PathParam("precoVendaItem") double precoVendaItem) throws SQLException {

        try {
            if(insertItemPedido(idProduto, idPedido, qtdProduto, precoVendaItem)){
                //TRUE: ItemPedido registrado com sucesso
                return Response.status(200).entity("{\"Status\":\"Registrado!\"}").build();
            } else {
                //FALSE: Erro registrando ItemPedido
                return Response.status(404).entity("{\"Status\":\"Erro!\"}").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity("{\"Erro\":\"Problemas na conexao com o Servidor. Tente novamente!\"}").build();
        }
    }
}