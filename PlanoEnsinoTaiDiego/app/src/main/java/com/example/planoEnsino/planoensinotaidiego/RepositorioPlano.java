package com.example.planoEnsino.planoensinotaidiego;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taiane on 14/07/2014.
 */
public class RepositorioPlano {
    private static final String CATEGORIA = "dados";
    // Nome do banco
    private static final String NOME_BANCO = "dados_android";
    // Nome da tabela
    public static final String NOME_TABELA = "plano";
    protected SQLiteDatabase db;
    public RepositorioPlano(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }
    protected RepositorioPlano() {
    // Apenas para criar uma subclasse...
    }

    // Salva a Plano, insere um novo ou atualiza
    public long salvar(Plano plano) {
        long id = plano.id;
        if (id != 0) {
            atualizar(plano);
        } else {
            // Insere novo
            id = inserir(plano);
        }
        return id;
    }
    // Insere uma nova Plano
    public long inserir(Plano plano) {
        ContentValues values = new ContentValues();
        values.put(Planos.MATERIA  , plano.materia);
        values.put(Planos.PROFESSOR, plano.professor);
        values.put(Planos.CONTEUDO, plano.conteudo);
        long id = inserir(values);
        return id;
    }

    // Insere uma nova Plano
    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }

    // Atualiza a Plano no banco. O id da Plano é utilizado.
    public int atualizar(Plano plano) {
        ContentValues values = new ContentValues();
        values.put(Planos.MATERIA, plano.materia);
        values.put(Planos.PROFESSOR, plano.professor);
        values.put(Planos.CONTEUDO, plano.conteudo);
        String _id = String.valueOf(plano.id);
        String where = Planos._ID + "=?";
        String[] whereArgs = new String[] { _id };
        int count = atualizar(values, where, whereArgs);
        return count;
    }

    // Atualiza a Plano com os valores abaixo
    // A cláusula where é utilizada para identificar a Plano a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }

    // Deleta a Plano com o id fornecido
    public int deletar(long id) {
        String where = Planos._ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        int count = deletar(where, whereArgs);
        return count;
    }

    // Deleta a Plano com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    // Busca a Plano pelo id
    public Plano buscarPlano(long id) {
        // select * from Plano where _id=?
        Cursor c = db.query(true, NOME_TABELA, Plano.colunas, Planos._ID + "=" + id, null, null, null, null, null);
        if (c.getCount() > 0) {
            // Posicinoa no primeiro elemento do cursor
            c.moveToFirst();
            Plano plano = new Plano();
            // Lê os dados
            plano.id = c.getLong(0);
            plano.materia = c.getString(1);
            plano.professor = c.getString(2);
            plano.conteudo = c.getInt(3);
            return plano;
        }
        return null;
    }

    // Retorna um cursor com todas as Planos
    public Cursor getCursor() {
        try {
            return db.query(NOME_TABELA, Plano.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o Plano: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todas as Planos
    public List<Plano> listarPlanos() {
        Cursor c = getCursor();
        List<Plano> planos = new ArrayList<Plano>();
        if (c.moveToFirst()) {
            // Recupera os índices das colunas
            int idxId = c.getColumnIndex(Planos._ID);
            int idxMateria = c.getColumnIndex(Planos.MATERIA);
            int idxProfessor = c.getColumnIndex(Planos.PROFESSOR);
            int idxConteudo = c.getColumnIndex(Planos.CONTEUDO);
            // Loop até o final
            do {
                Plano plano = new Plano();
                planos.add(plano);
                // recupera os atributos da Plano
                plano.id = c.getLong(idxId);
                plano.materia = c.getString(idxMateria);
                plano.professor = c.getString(idxProfessor);
                plano.conteudo = c.getInt(idxConteudo);
            } while (c.moveToNext());
        }
        return planos;
    }

    // Busca a Plano pelo nome "select * from Plano where nome=?"
    public Plano buscarPlanoPorMateria(String materia) {
        Plano plano = null;
        try {
            // Idem a: SELECT _id,nome,cpf,idade from Plano where nome = ?
            Cursor c = db.query(NOME_TABELA, Plano.colunas, Planos.MATERIA + "='" + materia + "'", null, null, null, null);
            // Se encontrou...
            if (c.moveToNext()) {
                plano = new Plano();
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                plano.id = c.getLong(0);
                plano.materia = c.getString(1);
                plano.professor = c.getString(2);
                plano.conteudo = c.getInt(3);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar a Plano pela materia: " + e.toString());
            return null;
        }
        return plano;
    }

    // Busca uma Plano utilizando as configurações definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de Plano
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs, String groupBy,                         String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    // Fecha o banco
    public void fechar() {
        if (db != null) {
            db.close();
        }
    }
}
