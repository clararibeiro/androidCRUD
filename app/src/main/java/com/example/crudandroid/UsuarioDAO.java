package com.example.crudandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("cpf", usuario.getCpf());
        values.put("telefone", usuario.getTelefone());
        values.put("email", usuario.getEmail());
        return banco.insert("usuario",null,values);
    }

    public List<Usuario> obterTodos(){
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = banco.query("usuario", new String[]{"id","nome","cpf","telefone","email"},null,null,null,null,null );
        while(cursor.moveToNext()){
            Usuario u = new Usuario();
            u.setId(cursor.getInt(0));
            u.setNome(cursor.getString(1));
            u.setCpf(cursor.getString(2));
            u.setTelefone(cursor.getString(3));
            u.setEmail(cursor.getString(4));
            usuarios.add(u);
        }
        return usuarios;
    }

    public void excluir(Usuario u){
        banco.delete("usuario","id = ?", new String[]{u.getId().toString()});
    }

    public void atualizar(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("cpf", usuario.getCpf());
        values.put("telefone", usuario.getTelefone());
        values.put("email", usuario.getEmail());
        banco.update("usuario",values,"id = ?", new String[]{usuario.getId().toString()});
    }
}
