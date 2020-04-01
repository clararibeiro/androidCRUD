package com.example.crudandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuario extends AppCompatActivity {
    private ListView listView;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuario);
        listView = findViewById(R.id.listaUsuarios);
        dao = new UsuarioDAO(this);
        usuarios = dao.obterTodos();
        usuariosFiltrados.addAll(usuarios);
        //ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1,usuariosFiltrados);
        UsuarioAdapter adaptador = new UsuarioAdapter(this,usuariosFiltrados);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraUsuario(newText);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);

    }
    public void procuraUsuario(String nome){
        usuariosFiltrados.clear();
        for(Usuario u : usuarios){
            if(u.getNome().toLowerCase().contains(nome.toLowerCase())){
                usuariosFiltrados.add(u);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Usuario usuarioExcluir = usuariosFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o usuario?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuariosFiltrados.remove(usuarioExcluir);
                        usuarios.remove(usuarioExcluir);
                        dao.excluir(usuarioExcluir);
                        listView.invalidateViews();

                    }
                }).create();
        dialog.show();

    }
    public void cadastrar(MenuItem item){
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Usuario usuarioAtualizar = usuariosFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("usuario", usuarioAtualizar);
        startActivity(it);
    }
    @Override
    public void onResume(){
        super.onResume();
        usuarios = dao.obterTodos();
        usuariosFiltrados.clear();
        usuariosFiltrados.addAll(usuarios);
        listView.invalidateViews();
    }
}
