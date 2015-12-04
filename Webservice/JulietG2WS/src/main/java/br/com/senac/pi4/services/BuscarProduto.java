package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/buscarProduto")
public class BuscarProduto {

    public List<Produto> selectBuscaProduto(String parametroBusca) throws Exception {
        Connection conn = null;
        PreparedStatement p = null;
        Produto prod = null;
        List<Produto> resultadoProduto = new ArrayList<Produto>();
        
        try {
            conn = Database.get().conn();
            p = conn.prepareStatement(
            "SELECT PROD.[idProduto]" +
                    ",PROD.[nomeProduto]" +
                    ", PROD.[descProduto]" +
                    ", PROD.[precProduto]" +
                    ", PROD.[descontoPromocao]" +
                    ", PROD.[imagem]" +
                    ", CAT.[idCategoria]" +
                    ", CAT.[nomeCategoria]" +
                    ", EST.[qtdProdutoDisponivel]" +
            "FROM [dbo].[Produto] PROD" +
                    "JOIN [dbo].[Categoria] CAT ON CAT.idCategoria = PROD.idCategoria" +
                    "JOIN [dbo].[Estoque] EST ON EST.idProduto = PROD.idProduto" +
            "WHERE 1 = 1" +
                    "AND PROD.[nomeProduto] LIKE ? " +
                    "OR PROD.[descProduto] LIKE ? " +
                    "AND PROD.[ativoProduto] = '1' " +
                    "AND  EST.[qtdProdutoDisponivel] > 0");

            p.setString(1, "'%" + parametroBusca + "%'");
            p.setString(2, "'%" + parametroBusca + "%'");

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                prod = new Produto();
                
                prod.setIdProduto(rs.getInt("idProduto"));
                prod.setNomeProduto(rs.getString("nomeProduto"));
                prod.setDescProduto(rs.getString("descProduto"));
                prod.setPrecProduto(rs.getDouble("precProduto"));
                prod.setDescontoPromocao(rs.getDouble("descontoPromocao"));
                prod.setImagem(rs.getByte("imagem"));
                prod.setIdCategoria(rs.getInt("idCategoria"));
                prod.setNomeCategoria(rs.getString("nomeCategoria"));
                prod.setQtdProdutoDisponivel(rs.getInt("qtdProdutoDisponivel"));
                
                resultadoProduto.add(prod);
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
        return resultadoProduto;
    }

    @GET
    @Path("/{parametroBusca}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuscaProduto(@PathParam("parametroBusca") String parametroBusca) {
        //Produto product = null;
        List<Produto> product2 = new ArrayList<Produto>();

        try {
            product2 = selectBuscaProduto(parametroBusca);
        } catch (Exception e) {
            return Response.status(500).entity("{\"Erro\":\"Problemas na conexao com o Servidor. Tente novamente!\"}").build();
        }
        if (product2.size() <= 0) {
            return Response.status(404).entity("{\"Erro\":\"Produto nao encontrado!\"}").build();
        }
        return Response.status(200).entity(product2).build();
    }
}