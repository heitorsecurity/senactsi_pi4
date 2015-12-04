package br.com.senac.pi4.services;

class Produto {
    private int    idProduto;
    private String nomeProduto;
    private String descProduto;
    private Double precProduto;
    private Double descontoPromocao;
    private int    idCategoria;
    private String nomeCategoria;
    private int    qtdProdutoDisponivel;
    private byte   imagem;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public Double getPrecProduto() {
        return precProduto;
    }

    public void setPrecProduto(Double precProduto) {
        this.precProduto = precProduto;
    }

    public Double getDescontoPromocao() {
        return descontoPromocao;
    }

    public void setDescontoPromocao(Double descontoPromocao) {
        this.descontoPromocao = descontoPromocao;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    
    public int getQtdProdutoDisponivel() {
        return qtdProdutoDisponivel;
    }

    public void setQtdProdutoDisponivel(int qtdProdutoDisponivel) {
        this.qtdProdutoDisponivel = qtdProdutoDisponivel;
    }

    public byte getImagem() {
        return imagem;
    }

    public void setImagem(byte imagem) {
        this.imagem = imagem;
    }
}
