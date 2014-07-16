package com.example.planoEnsino.planoensinotaidiego;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;

import java.util.List;

/**
 * Created by Taiane on 14/07/2014.
 */
public class CadastroPlano extends ListActivity {
    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;
    public static RepositorioPlano repositorio;
    private List<Plano> planos;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        repositorio = new RepositorioPlanoScript(this);
        atualizarLista();
    }
    protected void atualizarLista() { // Pega a lista de planos e exibe na tela
        planos = repositorio.listarPlanos();// Adaptador de lista customizado para cada linha de um plano
        setListAdapter(new PlanoListAdapter(this, planos));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
        menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar); return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) { // Clicou no menu
        switch (item.getItemId()) {
            case INSERIR_EDITAR: // Abre a tela com o formulário para adicionar
                startActivityForResult(new Intent(this, EditarPlano.class), INSERIR_EDITAR);
                break;
            case BUSCAR: // Abre a tela para buscar o plano pela materia
                startActivity(new Intent(this, BuscaPlano.class));
                break;
        }
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        EditarPlano(posicao);
    }
    // Recupera o id da materia, e abre a tela de edição
    protected void EditarPlano(int posicao) {
        // Usuário clicou em algum plano da lista
        // Recupera a materia selecionado
        Plano plano = planos.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, EditarPlano.class);
        // / Passa o id da pessoa como parâmetro
        it.putExtra(Planos._ID, plano.id);
        // Abre a tela de edição
        startActivityForResult(it, INSERIR_EDITAR);
    }
    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
        // Quando a activity EditarPlano retornar, seja se foi para adicionar vamos atualizar a lista
        if (codigoRetorno == RESULT_OK) { // atualiza a lista na tela
            atualizarLista();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fecha o banco
        repositorio.fechar();
    }
}
