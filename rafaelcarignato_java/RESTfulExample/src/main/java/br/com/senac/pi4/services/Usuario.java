package br.com.senac.pi4.services;

public class Usuario {
	
	private String loginUsuario;
	private String senhaUsuario;
	private String nomeUsuario;
	private String tipoPerfil;
	private Byte ativo;
	public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
	public String getSenhaUsuario() {
		return senhaUsuario;
	}
	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getTipoPerfil() {
		return tipoPerfil;
	}
	public void setTipoPerfil(String tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}
	public Byte getAtivo() {
		return ativo;
	}
	public void setAtivo(Byte ativo) {
		this.ativo = ativo;
	}
	
	
	
}
