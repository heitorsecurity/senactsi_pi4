package com.wesbalbinolive.julietapp;


public class SessionProduto {

    private int qtdProduto;
    private String usuarioLogin, senhaLogin;
    private boolean usuarioLogado;

    private  SessionProduto(){

    }

    public void setQuantidade(int qtdProduto){
        this.qtdProduto = qtdProduto;
    }

    public int getQuantidade(){
        return qtdProduto;
    }


    public void setLogin(String usuarioLogin){
        this.usuarioLogin = usuarioLogin;
    }

    public String getLogin(){
        return usuarioLogin;
    }

    public void setSenha(String senhaLogin){
        this.senhaLogin = senhaLogin;
    }

    public String getSenha(){
        return senhaLogin;
    }

    public void setUsuarioLogado(boolean usuarioLogado){
        this.usuarioLogado = usuarioLogado;
    }

    public boolean getUsuarioLogado(){
        return usuarioLogado;
    }


    public static final SessionProduto INSTANCE = new SessionProduto();

    public static SessionProduto getInstance(){
        return INSTANCE;
    }

}
