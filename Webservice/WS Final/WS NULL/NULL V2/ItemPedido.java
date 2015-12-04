package br.com.senac.pi4.services;

public class ItemPedido {

    int idPedido;
    int qtdProduto;
    double precoVendaItem;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public double getPrecoVendaItem() {
        return precoVendaItem;
    }

    public void setPrecoVendaItem(double precoVendaItem) {
        this.precoVendaItem = precoVendaItem;
    }
}
