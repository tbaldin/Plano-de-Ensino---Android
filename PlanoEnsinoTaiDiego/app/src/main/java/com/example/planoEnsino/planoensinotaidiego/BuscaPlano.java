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

        setContentView(R.layout.form_buscar_pessoa);

        ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(this);
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

        EditText materia = (EditText) findViewById(R.id.campoMateria);
        EditText professor = (EditText) findViewById(R.id.campoProfessor);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

// Recupera o nome da pessoa
        String materiaPlano = materia.getText().toString();

// Busca a pessoa pelo nome
        Plano plano = buscarPlano(materiaPlano);

        if (plano != null) {
// Atualiza os campos com o resultado
            professor.setText(plano.professor);
            conteudo.setText(String.valueOf(plano.conteudo));
        } else {
// Limpa os campos
            professor.setText("");
            conteudo.setText("");

            Toast.makeText(BuscaPlano.this, "Nenhum plano encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    // Busca um carro pelo nome
    protected Plano buscarPlano(String materiaPlano) {
        Plano plano = CadastroPlano.repositorio.buscarPlanoPorMateria(materiaPlano);
        return plano;
    }

}