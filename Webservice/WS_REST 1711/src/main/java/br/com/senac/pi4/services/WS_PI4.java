package br.com.senac.pi4.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
 
@Path("/v1")
public class WS_PI4 {
 
    @GET
    @Path("/login/")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginCliente(@QueryParam("login") String loginUsuario, @QueryParam("senha") String senhaUsuario) throws Exception {
        String resposta = "";
        List<Cliente> resultadoCliente = new ArrayList<Cliente>();
        
        resultadoCliente = checarLogin(loginUsuario, senhaUsuario);
        
        if(resultadoCliente.size() > 0){ //se Lista for maior que Zero
            try {
                String objetoJSON = new Gson().toJson(resultadoCliente);
                
                resposta = objetoJSON;
            } catch (Exception e) {
                return("Erro na criação do JSON:" + e);
            }
            
        } else {
            resposta = Util.constructJSON("Login", false, "Usuário ou Senha incorretos!");
        }
        return resposta;
    }
        
    private List<Cliente> checarLogin(String loginUsuario, String senhaUsuario) throws Exception{
        ArrayList<Cliente> resultadoCliente = new ArrayList<Cliente>();
        
        if(Util.isNotNull(loginUsuario) && Util.isNotNull(senhaUsuario)){ //checar NaN
            try {
                resultadoCliente = (ArrayList<Cliente>) ConexaoDB.checarLogin(loginUsuario, senhaUsuario);
            } catch (Exception e2) {
                throw(e2);
            }
        } else {
            throw new Exception("Erro! Login Falhou!");
        }
        return resultadoCliente;
    }
     
    @GET
    @Path("/registrar/")
    @Produces(MediaType.APPLICATION_JSON)
    public String gravarCliente(@QueryParam("nomeCompletoCliente") String nomeCompletoCliente,
                                @QueryParam("emailCliente") String emailCliente,
                                @QueryParam("senhaCliente") String senhaCliente,
                                @QueryParam("CPFCliente") String CPFCliente,
                                @QueryParam("celularCliente") String celularCliente, //NULL
                                @QueryParam("telComercialCliente") String telComercialCliente,//NULL
                                @QueryParam("telResidencialCliente") String telResidencialCliente,//NULL
                                @QueryParam("dtNascCliente") Date dtNascCliente,//NULL
                                @QueryParam("recebeNewsletter") boolean recebeNewsletter) //NULL
                                throws Exception {
        
        String resposta = "";

        int codigoRetorno = registrarCliente(nomeCompletoCliente,
                                            emailCliente,
                                            senhaCliente,
                                            CPFCliente,
                                            celularCliente, //NULL
                                            telComercialCliente,//NULL
                                            telResidencialCliente,//NULL
                                            dtNascCliente,//NULL
                                            recebeNewsletter); //NULL
        
        if(codigoRetorno == 0){
                resposta = Util.constructJSON("register",true);
        }else if(codigoRetorno == 1){
                resposta = Util.constructJSON("register",false, "Usuário já registrado!");
        }else if(codigoRetorno == 2){
                resposta = Util.constructJSON("register",false, "Caracteres especiais não são permitidos no usuário ou senha.");
        }else if(codigoRetorno == 3){
                resposta = Util.constructJSON("register",false, "Erro!");
        }
        
        return resposta;
    }
    
    private int registrarCliente(String nomeCompletoCliente,
            String emailCliente,
            String senhaCliente,
            String CPFCliente,
            String celularCliente, //NULL
            String telComercialCliente, //NULL
            String telResidencialCliente, //NULL
            Date dtNascCliente, //NULL
            boolean recebeNewsletter) { //NULL

            int result = 3;
            if(Util.isNotNull(nomeCompletoCliente) && Util.isNotNull(emailCliente) && Util.isNotNull(senhaCliente) && Util.isNotNull(CPFCliente)){
                try {
                    if(ConexaoDB.inserirCliente(nomeCompletoCliente,
                                                emailCliente,
                                                senhaCliente,
                                                CPFCliente,
                                                celularCliente, //NULL
                                                telComercialCliente, //NULL
                                                telResidencialCliente, //NULL
                                                dtNascCliente, //NULL
                                                recebeNewsletter)){ //NULL
                        result = 0;
                    }
                } catch(SQLException sqle){
                    //Violação de Chave-Primária: usuário já registrado
                    if(sqle.getErrorCode() == 1062){
                            result = 1;
                    } 
                    //Erro devido à utilização de caractereres especiais
                    else if(sqle.getErrorCode() == 1064){
                            System.out.println(sqle.getErrorCode());
                            result = 2;
                    }
                }
                catch (Exception e) {
                    //Demais erros
                    result = 3;
                }
        } else {
            //Demais erros
            result = 3;
        }
        return result;
    }
    
    @GET
    @Path("/registrar_endereco/")
    @Produces(MediaType.APPLICATION_JSON)
    public String gravarEndereco(@QueryParam("idCliente") int idCliente,
                                @QueryParam("nomeEndereco") String nomeEndereco,
                                @QueryParam("logradouroEndereco") String logradouroEndereco,
                                @QueryParam("numeroEndereco") String numeroEndereco,
                                @QueryParam("CEPEndereco") String CEPEndereco,
                                @QueryParam("cidadeEndereco") String cidadeEndereco,
                                String complementoEndereco, //NULL
                                String PaisEndereco, //NULL
                                Date UFEndereco) //NULL
                                throws SQLException { 
        
        String resposta = "";

        int codigoRetorno = registrarEndereco(idCliente,
                                nomeEndereco,
                                logradouroEndereco,
                                numeroEndereco,
                                CEPEndereco,
                                cidadeEndereco,
                                complementoEndereco, //NULL
                                PaisEndereco, //NULL
                                UFEndereco); //NULL
        
        if(codigoRetorno == 0){
                resposta = Util.constructJSON("register",true);
        }else if(codigoRetorno == 1){
                resposta = Util.constructJSON("register",false, "Usuário já registrado!");
        }else if(codigoRetorno == 2){
                resposta = Util.constructJSON("register",false, "Caracteres especiais não são permitidos no usuário ou senha.");
        }else if(codigoRetorno == 3){
                resposta = Util.constructJSON("register",false, "Erro!");
        }
        
        return resposta;
    }
    
    private int registrarEndereco(int idCliente,
                                    String nomeEndereco,
                                    String logradouroEndereco,
                                    String numeroEndereco,
                                    String CEPEndereco,
                                    String cidadeEndereco,
                                    String complementoEndereco, //NULL
                                    String PaisEndereco, //NULL
                                    Date UFEndereco) { //NULL

        int result = 3;
        if(Util.isNotNull(nomeEndereco) && Util.isNotNull(logradouroEndereco) 
           && Util.isNotNull(numeroEndereco) && Util.isNotNull(CEPEndereco) && Util.isNotNull(cidadeEndereco)){

            try {
                if(ConexaoDB.inserirEndereco(idCliente,
                                            nomeEndereco,
                                            logradouroEndereco,
                                            numeroEndereco,
                                            CEPEndereco, //NULL
                                            cidadeEndereco, //NULL
                                            complementoEndereco, //NULL
                                            PaisEndereco, //NULL
                                            UFEndereco)){ //NULL
                    result = 0;
                }
            } catch(SQLException sqle){
                //Violação de Chave-Primária: endereço já registrado
                if(sqle.getErrorCode() == 1062){
                        result = 1;
                } 
                //Erro devido à utilização de caractereres especiais
                else if(sqle.getErrorCode() == 1064){
                        System.out.println(sqle.getErrorCode());
                        result = 2;
                }
            }
            catch (Exception e) {
                //Demais erros
                result = 3;
            }
    } else {
        //Demais erros
        result = 3;
    }
    return result;
}

    @GET
    @Path("/detalhe_produto/")
    @Produces(MediaType.APPLICATION_JSON)
    public String detalheProduto(@QueryParam("idProduto") int idProduto) throws Exception {
        String resposta = "";
        List<Produto> resultadoProduto = new ArrayList<Produto>();
        
        resultadoProduto = checarProduto(idProduto);
        
        if(resultadoProduto.size() > 0){ //se Lista for maior que Zero
            try {
                String objetoJSON = new Gson().toJson(resultadoProduto);
                
                resposta = objetoJSON;
            } catch (Exception e) {
                return("Erro na criação do JSON:" + e);
            }
            
        } else {
            resposta = Util.constructJSON("Produto", false, "Produto não encontrado ou Quantidade não disponível!");
        }
        return resposta;
    }
        
    private List<Produto> checarProduto(int idProduto) throws Exception{
        List<Produto> resultadoProduto = new ArrayList<Produto>();
        
        if(idProduto > 0){ //checar NaN
            try {
                resultadoProduto = ConexaoDB.checarProduto(idProduto);
            } catch (Exception e2) {
                throw(e2);
            }
        } else {
            throw new Exception("Erro! ID do Produto inválido!");
        }
        return resultadoProduto; 
    }
    
    @GET
    @Path("/lista_categorias/")
    @Produces(MediaType.APPLICATION_JSON)
    public String retornaCategoria() throws Exception {
        String resposta = "";
        List<Categoria> resultadoCategoria = new ArrayList<Categoria>();
        
        resultadoCategoria = checarCategoria();
        
        if(resultadoCategoria.size() > 0){ //se Lista for maior que Zero
            try {
                String objetoJSON = new Gson().toJson(resultadoCategoria);
                
                resposta = objetoJSON;
            } catch (Exception e) {
                return("Erro na criação do JSON:" + e);
            }
            
        } else {
            resposta = Util.constructJSON("Categoria", false, "Problema no Retorno da lista de Categorias!");
        }
        return resposta;
    }
        
    private List<Categoria> checarCategoria() throws Exception{
        List<Categoria> resultadoCategoria = new ArrayList<Categoria>();
        
            try {
                resultadoCategoria = ConexaoDB.checarCategoria();
            } catch (Exception e2) {
                throw(e2);
            }
        return resultadoCategoria; 
    }
    
    @GET
    @Path("/busca_produto/")
    @Produces(MediaType.APPLICATION_JSON)
    public String retornaProduto(@QueryParam("stringBusca") String stringBusca) throws Exception {
        String resposta = "";
        List<Produto> resultadoBusca = new ArrayList<Produto>();
        
        resultadoBusca = buscarProduto(stringBusca);
        
        if(resultadoBusca.size() > 0){ //se Lista for maior que Zero
            try {
                String objetoJSON = new Gson().toJson(resultadoBusca);
                
                resposta = objetoJSON;
            } catch (Exception e) {
                return("Erro na criação do JSON:" + e);
            }
            
        } else {
            resposta = Util.constructJSON("Busca", false, "Falha ao buscar produto!");
        }
        return resposta;
    }
        
    private List<Produto> buscarProduto(String stringBusca) throws Exception{
        List<Produto> resultadoBusca = new ArrayList<Produto>();
        
            try {
                resultadoBusca = ConexaoDB.buscarProduto(stringBusca);
            } catch (Exception e2) {
                throw(e2);
            }
        return resultadoBusca; 
    }
    
    @GET
    @Path("/gravar_pedido/")
    @Produces(MediaType.APPLICATION_JSON)
    public String gravarItemPedido(@QueryParam("idProduto") int idProduto,
                                    @QueryParam("idPedido") int idPedido,
                                    @QueryParam("qtdProduto") int qtdProduto,
                                    @QueryParam("precoVendaItem") double precoVendaItem) 
                                    throws Exception {
        
        String resposta = "";

        int codigoRetorno = gravaItemPedido(idProduto, 
                                            idPedido,
                                            qtdProduto, 
                                            precoVendaItem); 
        
        if(codigoRetorno == 0){
                resposta = Util.constructJSON("register",true);
        }else if(codigoRetorno == 1){
                resposta = Util.constructJSON("register",false, "Pedido já registrado!");
        }else if(codigoRetorno == 2){
                resposta = Util.constructJSON("register",false, "Caracteres especiais não são permitidos!");
        }else if(codigoRetorno == 3){
                resposta = Util.constructJSON("register",false, "Erro!");
        }
        
        return resposta;
    }
    
    private int gravaItemPedido(int idProduto,
                                int idPedido,
                                int qtdProduto,
                                double precoVendaItem) {

            int result = 3;
            if((idProduto > 0) && (idPedido > 0) && (qtdProduto > 0) && (precoVendaItem > 0)){
                try {
                    if(ConexaoDB.inserirItemPedido(idProduto,
                                                    idPedido,
                                                    qtdProduto,
                                                    precoVendaItem)){
                        result = 0;
                    }
                } catch(SQLException sqle){
                    //Violação de Chave-Primária: usuário já registrado
                    if(sqle.getErrorCode() == 1062){
                            result = 1;
                    } 
                    //Erro devido à utilização de caractereres especiais
                    else if(sqle.getErrorCode() == 1064){
                            System.out.println(sqle.getErrorCode());
                            result = 2;
                    }
                }
                catch (Exception e) {
                    //Demais erros
                    result = 3;
                }
        } else {
            //Demais erros
            result = 3;
        }
        return result;
    }
    
    @GET
    @Path("/gravar_pedido/")
    @Produces(MediaType.APPLICATION_JSON)
    public String gravarPedido(@QueryParam("idPedido") int idPedido,
                                    @QueryParam("idCliente") int idCliente,
                                    @QueryParam("idStatus") int idStatus,
                                    @QueryParam("dataPedido") Date dataPedido,
                                    @QueryParam("idTipoPagto") int idTipoPagto, 
                                    @QueryParam("idEndereco") int idEndereco,
                                    @QueryParam("idAplicacao") int idAplicacao) 
                                    throws Exception {
        
        String resposta = "";

        int codigoRetorno = gravaPedido(idPedido,
                                            idCliente,
                                            idStatus,
                                            dataPedido,
                                            idTipoPagto, 
                                            idEndereco,
                                            idAplicacao); 
        
        if(codigoRetorno == 0){
                //resposta = Util.constructJSON("register",true);
                ConexaoDB.selecionaPedido(idCliente, dataPedido); //retorna os dados do pedido que acabou de ser registrado
        }else if(codigoRetorno == 1){
                resposta = Util.constructJSON("register",false, "Pedido já registrado!");
        }else if(codigoRetorno == 2){
                resposta = Util.constructJSON("register",false, "Caracteres especiais não são permitidos!");
        }else if(codigoRetorno == 3){
                resposta = Util.constructJSON("register",false, "Erro!");
        }
        
        return resposta;
    }
    
    private int gravaPedido(int idPedido,
                            int idCliente,
                            int idStatus,
                            Date dataPedido,
                            int idTipoPagto, 
                            int idEndereco, 
                            int idAplicacao) { 

    int result = 3;
    if((idPedido > 0) && (idCliente > 0) && (idStatus > 0) && (Util.isNotNull(dataPedido.toString())) && (idTipoPagto > 0) && (idEndereco > 0) && (idAplicacao > 0)){
        try {
            if(ConexaoDB.inserirPedido(idPedido,
                                        idCliente,
                                        idStatus,
                                        dataPedido,
                                        idTipoPagto, //NULL
                                        idEndereco, //NULL
                                        idAplicacao)){ //NULL
                result = 0;
            }
        } catch(SQLException sqle){
            //Violação de Chave-Primária: usuário já registrado
            if(sqle.getErrorCode() == 1062){
                    result = 1;
            } 
            //Erro devido à utilização de caractereres especiais
            else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
            }
        }
        catch (Exception e) {
            //Demais erros
            result = 3;
        }
        } else {
            //Demais erros
            result = 3;
        }
        return result;
    }
    
}