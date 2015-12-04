package br.com.senac.pi4.services;

public class Cliente {

    private int    idCliente;
    private String emailCliente;
    private String nomeCompletoCliente;

    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNomeCompletoCliente() {
        return nomeCompletoCliente;
    }
    public void setNomeCompletoCliente(String nomeCompletoCliente) {
        this.nomeCompletoCliente = nomeCompletoCliente;
    }	
}
