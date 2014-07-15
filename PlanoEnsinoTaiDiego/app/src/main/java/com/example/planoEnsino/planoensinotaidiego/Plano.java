package com.example.planoEnsino.planoensinotaidiego;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import com.example.planoEnsino.planoensinotaidiego.Plano.Planos;

/**
 * Created by Taiane on 14/07/2014.
 */
public class Plano {
    public static String[] colunas = new String[] {
            Planos._ID, Planos.MATERIA, Planos.PROFESSOR, Planos.CONTEUDO
    };
    public static final String AUTHORITY = "com.example.planoEnsino.planoensinotaidiego.provider.plano";
    public long id;
    public String materia;
    public String professor;
    public int conteudo;

    public Plano() {
    }

    public Plano(String mat, String profe, int conteu) {
        super(); this.materia = mat;
        this.professor = profe;
        this.conteudo = conteu;
    }

    public Plano(long id, String materi, String profe, int conteu) {
        super();
        this.id = id;
        this.materia = materi;
        this.professor = profe;
        this.conteudo = conteu;
    }
    /** * Classe interna para representar as colunas e ser utilizada por um Content
     * Provider * *
     * Filha de BaseColumns que já define (_id e _count), para seguir o padrão *
     * Android */
    public static final class Planos implements BaseColumns {// Não pode instanciar esta Classe
        private Planos() {
        }
    }
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/planos");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.planos";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.planos";
    public static final String DEFAULT_SORT_ORDER = "_id ASC";
    public static final String MATERIA = "materia";
    public static final String PROFESSOR = "professor";
    public static final String CONTEUDO = "CONTEUDO";
    public static Uri getUriId(long id) { // Adiciona o id na URI default do /planos
        Uri uriPlanos = ContentUris.withAppendedId(Planos.CONTENT_URI, id);
        return uriPlanos;
    }

    @Override public String toString() {
        return "Materia: " + materia + ", Professor: " + professor + ", Conteudo: " + conteudo;
    }
}



