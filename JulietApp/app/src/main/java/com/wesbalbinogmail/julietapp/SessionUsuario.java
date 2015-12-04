package com.wesbalbinogmail.julietapp;


public class SessionUsuario {

    private String usuarioLogin;
    private String senhaLogin;
    private String emailLogin;
    private boolean usuarioLogado;

    public SessionUsuario(){

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

    public void setEmailLogin(String emailLogin){
        this.emailLogin = emailLogin;
    }

    public String getEmail(){
        return emailLogin;
    }

    public void setUsuarioLogado(boolean usuarioLogado){
        this.usuarioLogado = usuarioLogado;
    }

    public boolean getUsuarioLogado(){
        return usuarioLogado;
    }


    public static final SessionUsuario INSTANCE = new SessionUsuario();

    public static SessionUsuario getInstance(){
        return INSTANCE;
    }

}
