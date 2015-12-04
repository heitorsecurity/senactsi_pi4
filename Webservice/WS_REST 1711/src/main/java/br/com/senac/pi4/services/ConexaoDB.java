package br.com.senac.pi4.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ConexaoDB {
    @SuppressWarnings("finally")
    public static Connection criarConexao() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (ClassNotFoundException e){
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            return con;            
        }
    }
    
    static List<Cliente> checarLogin(String loginUsuario, String senhaUsuario) throws Exception {
        List<Cliente> detalheLogin = new ArrayList<Cliente>();
        Connection dbConn = null;
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
                "SELECT TOP 1 [idCliente]" +
                ", [emailCliente]" +
                ", [nomeCompletoCliente]" +
                "FROM [dbo].[Cliente]" +
                "WHERE 1 = 1" +
                "AND [emailCliente] = ?" +
                "AND [senhaCliente] = ?");

            p.setString(1, loginUsuario);
            p.setString(2, senhaUsuario);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                
                cli.setIdCliente(rs.getInt("idCliente"));
                cli.setEmailCliente(rs.getString("emailCliente"));
                cli.setNomeCompletoCliente(rs.getString("nomeCompletoCliente"));
                
                detalheLogin.add(cli);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return detalheLogin;
    }
    
    static boolean inserirCliente(  String nomeCompletoCliente,
                                    String emailCliente,
                                    String senhaCliente,
                                    String CPFCliente,
                                    String celularCliente, //NULL
                                    String telComercialCliente,//NULL
                                    String telResidencialCliente,//NULL
                                    Date dtNascCliente,//NULL
                                    boolean recebeNewsletter) //NULL
                                    throws Exception {
        
        boolean clienteRegistrado = false;
        Connection dbConn = null;
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
                "INSERT INTO [dbo].[Cliente] VALUES" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?))");

            p.setString(1, nomeCompletoCliente);
            p.setString(2, emailCliente);
            p.setString(3, senhaCliente);
            p.setString(4, CPFCliente);
            p.setString(5, celularCliente);
            p.setString(6, telComercialCliente);
            p.setString(7, telResidencialCliente);
            p.setDate  (8, dtNascCliente);
            p.setBoolean(9, recebeNewsletter);

            int gravacao = p.executeUpdate();
            if (gravacao > 0) {
                clienteRegistrado = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return clienteRegistrado;
    }

        static boolean inserirEndereco( int idCliente,
                                        String nomeEndereco,
                                        String logradouroEndereco,
                                        String numeroEndereco,
                                        String CEPEndereco,
                                        String cidadeEndereco,
                                        String complementoEndereco, //NULL
                                        String PaisEndereco, //NULL
                                        Date UFEndereco) //NULL
                                        throws Exception {
        
        boolean enderecoRegistrado = false;
        Connection dbConn = null;
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
            "INSERT INTO [dbo].[Endereco] VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?))");

            p.setInt(1, idCliente);
            p.setString(2, nomeEndereco);
            p.setString(3, logradouroEndereco);
            p.setString(4, numeroEndereco);
            p.setString(5, CEPEndereco);
            p.setString(6, complementoEndereco);
            p.setString(7, cidadeEndereco);
            p.setString(8, PaisEndereco);
            p.setDate(9, UFEndereco);

            int gravacao = p.executeUpdate();
            if (gravacao > 0) {
                enderecoRegistrado = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return enderecoRegistrado;
    }
    
        static List<Produto> checarProduto(int idProduto) throws Exception {
        Connection dbConn = null;
        List<Produto> detalheProduto = new ArrayList<Produto>();
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
            "SELECT TOP 1 PROD.[idProduto]" +
                    ",PROD.[nomeProduto]" +
                    ", PROD.[descProduto]" +
                    ", PROD.[precProduto]" +
                    ", PROD.[descontoPromocao]" +
                    ", PROD.[imagem]" +
                    ", CAT.[idCategoria]" +
                    ", CAT.[nomeCategoria]" +
                    ", EST.[qtdProdutoDisponivel]" +
            "FROM [dbo].[Produto] PROD" +
                    "JOIN [dbo].[Categoria] CAT ON CAT.idCategoria = PROD.idCategoria," +
                    "JOIN [dbo].[Estoque] EST ON EST.idProduto = PROD.idProduto" +
            "WHERE 1 = 1" +
                    "AND PROD.[idProduto] = ?" +
                    "AND PROD.[ativoProduto] = S" +
                    "AND  EST.[qtdProdutoDisponivel] > 0");

            p.setInt(1, idProduto);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                Produto prod = new Produto();
                
                prod.setIdProduto(rs.getInt("idProduto"));
                prod.setNomeProduto(rs.getString("nomeProduto"));
                prod.setDescProduto(rs.getString("descProduto"));
                prod.setPrecProduto(rs.getDouble("precProduto"));
                prod.setDescontoPromocao(rs.getDouble("descontoPromocao"));
                prod.setImagem(rs.getByte("imagem"));
                prod.setIdCategoria(rs.getInt("idCategoria"));
                prod.setNomeCategoria(rs.getString("nomeCategoria"));
                prod.setQtdProdutoDisponivel(rs.getInt("qtdProdutoDisponivel"));
                
                detalheProduto.add(prod);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
       return detalheProduto;
    }
        
        static List<Categoria> checarCategoria() throws Exception {
        Connection dbConn = null;
        List<Categoria> detalheCategoria = new ArrayList<Categoria>();
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
            "SELECT    [idCategoria]" +
                    ", [nomeCategoria]" +
                    ", [descCategoria]" +
            "FROM [dbo].[Categoria]");

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                Categoria cat = new Categoria();
                
                cat.setIdCategoria(rs.getInt("idCategoria"));
                cat.setNomeCategoria(rs.getString("nomeCategoria"));
                cat.setDescCategoria(rs.getString("descCategoria"));
                
                detalheCategoria.add(cat);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
       return detalheCategoria;
    }
        
        static List<Produto> buscarProduto(String ParametroBusca) throws Exception {
        Connection dbConn = null;
        List<Produto> detalheProduto = new ArrayList<Produto>();
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
            "SELECT TOP 1 PROD.[idProduto]" +
                    ",PROD.[nomeProduto]" +
                    ", PROD.[descProduto]" +
                    ", PROD.[precProduto]" +
                    ", PROD.[descontoPromocao]" +
                    ", PROD.[imagem]" +
                    ", CAT.[idCategoria]" +
                    ", CAT.[nomeCategoria]" +
                    ", EST.[qtdProdutoDisponivel]" +
            "FROM [dbo].[Produto] PROD" +
                    "JOIN [dbo].[Categoria] CAT ON CAT.idCategoria = PROD.idCategoria," +
                    "JOIN [dbo].[Estoque] EST ON EST.idProduto = PROD.idProduto" +   
            "WHERE 1 = 1" +
                "AND PROD.[nomeProduto] LIKE %?%" +
                "OR  PROD.[descProduto] LIKE %?%" +
                "AND PROD.[ativoProduto] = S" +
                "AND  EST.[qtdProdutoDisponivel] > 0");

            p.setString(1, ParametroBusca);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                Produto prod = new Produto();
                
                prod.setIdProduto(rs.getInt("idProduto"));
                prod.setNomeProduto(rs.getString("nomeProduto"));
                prod.setDescProduto(rs.getString("descProduto"));
                prod.setPrecProduto(rs.getDouble("precProduto"));
                prod.setDescontoPromocao(rs.getDouble("descontoPromocao"));
                prod.setImagem(rs.getByte("imagem"));
                prod.setIdCategoria(rs.getInt("idCategoria"));
                prod.setNomeCategoria(rs.getString("nomeCategoria"));
                prod.setQtdProdutoDisponivel(rs.getInt("qtdProdutoDisponivel"));
                
                detalheProduto.add(prod);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
       return detalheProduto;
    }

        static boolean inserirItemPedido(int idProduto, int idPedido, int qtdProduto, double precoVendaItem) throws SQLException, Exception {
        boolean retornoItemPedido = false;
        Connection dbConn = null;

        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
                "INSERT INTO [dbo].[ItemPedido] VALUES" +
                "(?, ?, ?, ?)");

            p.setInt(1, idProduto);
            p.setInt(2, idPedido);
            p.setInt(3, qtdProduto);
            p.setDouble(4, precoVendaItem);

            int gravacao = p.executeUpdate();
            if (gravacao > 0) {
                retornoItemPedido = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return retornoItemPedido;
    }

        static List<Pedido> selecionaPedido(int idCliente, Date dataPedido) throws SQLException, Exception {
        Connection dbConn = null;
        List<Pedido> retornoPedido = new ArrayList<Pedido>();
        
        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
            "SELECT [idPedido]" +
            ",[idCliente]" +
            ",[idStatus]" +
            ",[dataPedido]" +
            ",[idTipoPagto]" +
            ",[idEndereco]" +
            " ,[idAplicacao]" +
            " FROM [Juliet].[dbo].[Pedido]" +
            "WHERE idCliente = ? AND dataPedido = ?" +
            "ORDER BY dataPedido DESC");

            p.setInt(1, idCliente);
            p.setDate(2, dataPedido);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                Pedido ped = new Pedido();
                
                ped.setIdPedido(rs.getInt("idPedido"));
                ped.setIdCliente(rs.getInt("idCliente"));
                ped.setIdStatus(rs.getInt("idStatus"));
                ped.setDataPedido(rs.getDate("dataPedido"));
                ped.setIdTipoPagto(rs.getInt("idTipoPagto"));
                ped.setIdEndereco(rs.getInt("idEndereco"));
                ped.setIdAplicacao(rs.getInt("idAplicacao"));
                
                retornoPedido.add(ped);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
       return retornoPedido;
    }

        static boolean inserirPedido(int idPedido, int idCliente, int idStatus, Date dataPedido, int idTipoPagto, int idEndereco, int idAplicacao) throws SQLException, Exception {
        boolean retornoPedido = false;
        Connection dbConn = null;

        try {
            try{
                dbConn = ConexaoDB.criarConexao();
            } catch (Exception e) {
                throw e;
            }

            PreparedStatement p = null;

            p = dbConn.prepareStatement(
                "INSERT INTO [dbo].[Pedido] VALUES" +
                "(?, ?, ?, ?, ?, ?, ?)");

            p.setInt(1, idPedido);
            p.setInt(2, idCliente);
            p.setInt(3, idStatus);
            p.setDate(4, dataPedido);
            p.setInt(3, idTipoPagto);
            p.setInt(3, idEndereco);
            p.setInt(3, idAplicacao);

            int gravacao = p.executeUpdate();
            if (gravacao > 0) {
                retornoPedido = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return retornoPedido;
    }
}
