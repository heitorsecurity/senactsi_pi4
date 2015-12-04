package com.wesbalbinogmail.julietapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListagemProdutosAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private JSONArray itens;
    private Intent listagem;

    public ListagemProdutosAdapter(Context context, JSONArray itens){
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }

    public class PesquisaHolder{
        private TextView txtNomeListagem;
        private TextView txtPrecoListagem;
    }

    @Override
    public int getCount() {
        return itens.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return itens.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PesquisaHolder holder;
        String nomeProduto;
        String precoProduto;
        int idProduto = 0;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_listagem_produtos, parent, false);

            holder = new PesquisaHolder();
            holder.txtNomeListagem = ((TextView) convertView.findViewById(R.id.txtNomeListagem));
            holder.txtPrecoListagem = ((TextView) convertView.findViewById(R.id.txtPrecoListagem));

            convertView.setTag(holder);
        } else {
            holder = (PesquisaHolder) convertView.getTag();
        }

        JSONObject jsonObject = null;
        try {

            listagem = new Intent(convertView.getContext(), ListagemProdutosActivity.class);

            jsonObject = itens.getJSONObject(position);
            nomeProduto = jsonObject.getString("nomeProduto");
            precoProduto = jsonObject.getString("precProduto");
            idProduto = jsonObject.getInt("idProduto");
            listagem.putExtra("idProduto", idProduto);

            holder.txtNomeListagem.setText(nomeProduto);
            holder.txtPrecoListagem.setText(precoProduto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
