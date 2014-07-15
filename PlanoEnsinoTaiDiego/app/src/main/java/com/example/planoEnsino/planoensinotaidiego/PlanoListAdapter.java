package com.example.planoEnsino.planoensinotaidiego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;

import java.util.List;

/**
 * Created by Taiane on 14/07/2014.
 */
public class PlanoListAdapter extends BaseAdapter {
    private Context context;
    private List<Plano> lista;
    public PlanoListAdapter(Context context, List<Plano> lista) { 
        this.context = context; 
        this.lista = lista; 
    } 
    
    public int getCount() { 
        return lista.size(); 
    } 
    
    public Object getItem(int position) { 
        return lista.get(position); 
    } 
    
    public long getItemId(int position) { 
        return position; 
    } 
    
    public View getView(int position, View convertView, ViewGroup parent) { // Recupera a plano da posição atual
        Plano p = lista.get(position); 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View view = inflater.inflate(R.layout.plano_linha_tabela, null); 
        TextView materia = (TextView) view.findViewById(R.id.materia); 
        materia.setText(p.materia); 
        TextView professor = (TextView) view.findViewById(R.id.professor);
        professor.setText(p.professor);
        TextView conteudo = (TextView) view.findViewById(R.id.conteudo);
        conteudo.setText(String.valueOf(p.conteudo));
        return view;
    }
}