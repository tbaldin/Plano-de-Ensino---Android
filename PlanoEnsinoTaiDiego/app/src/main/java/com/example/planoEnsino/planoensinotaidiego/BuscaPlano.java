package com.example.planoEnsino.planoensinotaidiego;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;
/**
 * Created by Taiane on 14/07/2014.
 */
public class BuscaPlano extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.form_buscar_plano);

        ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
        //btBuscar.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para não ficar nada pendente na tela
        setResult(RESULT_CANCELED);

        // Fecha a tela
        finish();
    }

    /**
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view) {

        EditText MATERIA = (EditText) findViewById(R.id.campoMateria);
        EditText PROFESSOR = (EditText) findViewById(R.id.campoProfessor);
        EditText CONTEUDO = (EditText) findViewById(R.id.campoConteudo);

        // Recupera o nome da plano
        String nomePlano = MATERIA.getText().toString();

        // Busca a plano pelo nome
        Plano plano = buscaPlano(nomePlano);

        if (plano != null) {
            // Atualiza os campos com o resultado
            PROFESSOR.setText(plano.professor);
            CONTEUDO.setText(String.valueOf(plano.conteudo));
        } else {
            // Limpa os campos
            PROFESSOR.setText("");
            CONTEUDO.setText("");

            Toast.makeText(BuscaPlano.this, "Nenhuma plano encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    // Busca um carro pelo nome
    protected Plano buscaPlano(String nomePlano) {
        Plano plano;
        plano = CadastroPlano.repositorio.buscarPlanoPorMateria(nomePlano);
        return plano;
    }

}