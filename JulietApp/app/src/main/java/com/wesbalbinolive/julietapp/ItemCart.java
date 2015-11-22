package com.wesbalbinolive.julietapp;

import android.media.Image;

public class ItemCart {
    private int IdProduto;
    private String Nome;
    private int quantidade;
    private Image img;
    private double valor;

    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ItemCart(int idProduto,String Nome,int quantidade, double valor) {
        this.Nome = Nome;
        this.IdProduto = idProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

}
