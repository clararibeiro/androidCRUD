package com.example.crudandroid;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {
    private List<Usuario> usuarios;
    private Activity activity;
    public UsuarioAdapter(Activity activity, List<Usuario> usuarios) {
        this.activity = activity;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usuarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, parent,false);
        TextView nome = v.findViewById(R.id.txtNome);
        TextView cpf = v.findViewById(R.id.txtCPF);
        TextView telefone = v.findViewById(R.id.txtTelefone);
        TextView email = v.findViewById(R.id.txtEmail);

        Usuario u = usuarios.get(position);
        nome.setText(u.getNome());
        cpf.setText(u.getCpf());
        telefone.setText(u.getTelefone());
        email.setText(u.getEmail());

        return v;
    }
}
