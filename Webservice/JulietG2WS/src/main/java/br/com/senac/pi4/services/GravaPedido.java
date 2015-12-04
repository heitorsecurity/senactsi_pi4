package br.com.senac.pi4.services;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gravaPedido")
public class GravaPedido {

    public Pedido insertPedido(int idCliente,
            int idStatus,
            int idTipoPagto,
            int idEndereco,
            int idAplicacao) throws SQLException {
        Connection conn = null;
        PreparedStatement p = null;
        Pedido ped = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataPedido = new Date();

        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
                    "INSERT INTO [dbo].[Pedido] VALUES (?, ?, ?, ?, ?, ?)");

            p.setInt(1, idCliente);
            p.setInt(2, idStatus);
            p.setString(3, dateFormat.format(dataPedido));
            p.setInt(4, idTipoPagto);
            p.setInt(5, idEndereco);
            p.setInt(6, idAplicacao);

            int gravacao = p.executeUpdate();

            if (gravacao > 0) {
                //Gravou Pedido com Sucesso
                ped = getNumeroPedido(idCliente);
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
        return ped;
    }

    @GET
    @Path("/{idCliente}/{idStatus}/{idTipoPagto}/{idEndereco}/{idAplicacao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gravaPedido(@PathParam("idCliente") int idCliente,
            @PathParam("idStatus") int idStatus,
            @PathParam("idTipoPagto") int idTipoPagto,
            @PathParam("idEndereco") int idEndereco,
            @PathParam("idAplicacao") int idAplicacao) throws SQLException {

        Pedido order = null;

        try {
            order = insertPedido(idCliente, idStatus, idTipoPagto, idEndereco, idAplicacao);
        } catch (Exception e) {
            return Response.status(500).entity("{\"Erro\":\"Problemas na conexao com o Servidor. Tente novamente!\"}").build();
        }
        if (order == null) {
            return Response.status(404).entity("{\"Erro\":\"Erro na gravacao/recuperacao do Pedido!\"}").build();
        }
        return Response.status(200).entity(order).build();
    }

    public Pedido getNumeroPedido(int idCliente) throws SQLException {
        Connection dbConn = null;
        PreparedStatement p2 = null;
        Pedido numeroPedido = null;

        try {
            dbConn = Database.get().conn();

            p2 = dbConn.prepareStatement(
                    "SELECT TOP 1 [idPedido],[idCliente],[idStatus],[dataPedido],[idTipoPagto],[idEndereco], [idAplicacao]"
                    + "FROM [Juliet].[dbo].[Pedido]"
                    + "WHERE idCliente = ?"
                    + "ORDER BY dataPedido DESC");

            p2.setInt(1, idCliente);

            ResultSet rs = p2.executeQuery();

            while (rs.next()) {
                numeroPedido = new Pedido();

                numeroPedido.setIdPedido(rs.getInt("idPedido"));
                numeroPedido.setIdCliente(rs.getInt("idCliente"));
                numeroPedido.setIdStatus(rs.getInt("idStatus"));
                numeroPedido.setDataPedido(rs.getDate("dataPedido"));
                numeroPedido.setIdTipoPagto(rs.getInt("idTipoPagto"));
                numeroPedido.setIdEndereco(rs.getInt("idEndereco"));
                numeroPedido.setIdAplicacao(rs.getInt("idAplicacao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (p2 != null) {
                p2.close();
            }
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return numeroPedido;
    }
}
