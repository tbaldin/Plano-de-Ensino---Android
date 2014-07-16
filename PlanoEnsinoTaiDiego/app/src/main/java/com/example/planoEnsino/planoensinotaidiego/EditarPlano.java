package com.example.planoEnsino.planoensinotaidiego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;

/**
 * Created by Taiane on 14/07/2014.
 */
public class EditarPlano extends Activity {
    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private EditText campoMateria;
    private EditText campoProfessor;
    private EditText campoConteudo;
    private long id;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.form_editar_plano);
        campoMateria = (EditText) findViewById(R.id.campoMateria);
        campoProfessor = (EditText) findViewById(R.id.campoProfessor);
        campoConteudo = (EditText) findViewById(R.id.campoConteudo);
        id = null;
        Bundle extras = getIntent().getExtras();

        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Planos._ID);
            if (id != null) {
                Plano p = BuscaPlano(id);
                campoMateria.setText(p.materia);
                campoProfessor.setText(p.professor);
                campoConteudo.setText(String.valueOf(p.conteudo));
            }
        }

        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { // Fecha a tela
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { salvar();
            }
        });
        ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir); if (id == null) {
            btExcluir.setVisibility(View.INVISIBLE);
        } else {
            btExcluir.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        setResult(RESULT_CANCELED);
        finish();
    }
    public void salvar() {
        int idade = 0;
        try {
            idade = Integer.parseInt(campoConteudo.getText().toString());
        } catch (NumberFormatException e) {
        }

        Plano plano = new Plano();
        if (id != null) {
            // É uma atualização
            plano.id = id;
        }
        plano.materia = campoMateria.getText().toString();
        plano.professor = campoProfessor.getText().toString();
        plano.conteudo = campoConteudo.getText().toString();

        // Salvar
        salvarPlano(plano);

        // OK
        setResult(RESULT_OK, new Intent());

        // Fecha a tela
        finish();
    }

    public void excluir() {
        if (id != null) {
            excluirPlano(id);
        }
        // OK
        setResult(RESULT_OK, new Intent());
        // Fecha a tela
        finish();
    }

    // Buscar a plano pelo id
    protected Plano buscarPlano(long id) {
        return CadastroPlano.repositorio.buscarPlano(id);
    }
    // Salvar plano
    protected void salvarPlano(Plano plano) {
        CadastroPlano.repositorio.salvar(plano);
    }

    // Excluir plano
    protected void excluirPlano(long id) {
        CadastroPlano.repositorio.deletar(id);
    }
}