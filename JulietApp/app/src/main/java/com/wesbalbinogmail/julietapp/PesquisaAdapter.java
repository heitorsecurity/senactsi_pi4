package com.wesbalbinogmail.julietapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PesquisaAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private JSONArray itens;

    public PesquisaAdapter(Context context, JSONArray itens){
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }



    public class PesquisaHolder{
        private TextView txtNomePesquisa;
        private TextView txtPrecoPesquisa;
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
        String nomeProduto = null;
        String precoProduto;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_pesquisa, parent, false);

            holder = new PesquisaHolder();
            holder.txtNomePesquisa = ((TextView) convertView.findViewById(R.id.txtNomePesquisa));
            holder.txtPrecoPesquisa = ((TextView) convertView.findViewById(R.id.txtPrecoPesquisa));

            convertView.setTag(holder);
        } else {
            holder = (PesquisaHolder) convertView.getTag();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = itens.getJSONObject(position);
            nomeProduto = jsonObject.getString("nomeProduto");
            precoProduto = jsonObject.getString("precProduto");

            holder.txtNomePesquisa.setText(nomeProduto);
            holder.txtPrecoPesquisa.setText(precoProduto);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return convertView;
    }
}
