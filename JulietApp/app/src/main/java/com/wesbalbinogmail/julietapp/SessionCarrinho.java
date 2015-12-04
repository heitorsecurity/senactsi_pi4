package com.wesbalbinogmail.julietapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionCarrinho {


    private Map<Integer, ItemCart> carrinho = new HashMap<>();
    private static SessionCarrinho ourInstance = new SessionCarrinho();

    public static SessionCarrinho getInstance() {
        return ourInstance;
    }

    private SessionCarrinho() {
    }

    public Map<Integer, ItemCart> getItens() {
        return carrinho;
    }

    public void AddItemCarrinho(ItemCart produto){
        ItemCart carrinhoProduto = carrinho.get(produto.getIdProduto());
        if (carrinhoProduto != null) {
            carrinhoProduto.setQuantidade(carrinhoProduto.getQuantidade() + produto.getQuantidade());
        }
        else {
            carrinho.put(produto.getIdProduto(), produto);
        }
    }

    public void subtraiProduto(int idProduto,int qtd){
        ItemCart carrinhoProduto = carrinho.get(idProduto);

        if(carrinhoProduto.getIdProduto() == idProduto){
            if(carrinhoProduto.getQuantidade() <= qtd){
                carrinho.remove(idProduto);
            }else{
                carrinhoProduto.setQuantidade(carrinhoProduto.getQuantidade() - qtd);
            }
        }
    }

    public  void somaProduto(int  idProduto,int qtd){
        ItemCart carrinhoProduto = carrinho.get(idProduto);
        carrinhoProduto.setQuantidade(carrinhoProduto.getQuantidade() + qtd);
    }

    public void deleteProduto(int idProduto){
        carrinho.remove(idProduto);
    }


    public Map<Integer, ItemCart> getCarrinho() {
        List<ItemCart> listCarrinho = new ArrayList<ItemCart>(carrinho.values());
        return carrinho;
    }
}
