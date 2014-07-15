package com.example.planoEnsino.planoensinotaidiego;

import android.content.Context;

/**
 * Created by Taiane on 14/07/2014.
 */
public class RepositorioPlanosScript extends RepositorioPlano {
    // Script para fazer drop na tabela
    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS plano";
    // Cria a tabela com o "_id" sequencial
    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table plano ( _id integer primary key autoincrement, materia text not null,professor text not null,conteudo text not null);",
            "insert into plano(materia,professor,conteudo) values('Programacao','Fernando','blablablblal');",
            "insert into plano(materia,professor,conteudo) values('calculo','joao','balallalaal');",
            "insert into plano(materia,professor,conteudo) values('algoritmo','heheh','dedede');"
    };
    // Nome do banco
    private static final String NOME_BANCO = "baco_dados";
    // Controle de versão
    private static final int VERSAO_BANCO = 1;
    // Nome da tabela
    public static final String TABELA_PESSOA = "plano";
    // Classe utilitária para abrir, criar, e atualizar o banco de dados
    private SQLiteHelper dbHelper;
    // Cria o banco de dados com um script SQL
    public RepositorioPlanoScript(Context ctx) {
        // Criar utilizando um script SQL
        dbHelper = new SQLiteHelper(ctx, RepositorioPlanosScript.NOME_BANCO, RepositorioPlanosScript.VERSAO_BANCO,
                RepositorioPlanosScript.SCRIPT_DATABASE_CREATE, RepositorioPlanosScript.SCRIPT_DATABASE_DELETE);
        // abre o banco no modo escrita para poder alterar também
        db = dbHelper.getWritableDatabase();
    }
    // Fecha o banco
    @Override public void fechar() {
        super.fechar();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

