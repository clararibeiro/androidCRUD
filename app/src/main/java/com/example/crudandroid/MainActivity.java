package com.example.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MainActivity extends AppCompatActivity {
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText email;
    private UsuarioDAO dao;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.edtNome);
        cpf = findViewById(R.id.edtCPF);
        telefone = findViewById(R.id.edtTelefone);
        email = findViewById(R.id.edtEmail);
        dao = new UsuarioDAO(this);

        //Criando as máscaras para os campos cpf e telefone
        SimpleMaskFormatter smf_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf = new MaskTextWatcher(cpf,smf_cpf);
        cpf.addTextChangedListener(mtw_cpf);

        SimpleMaskFormatter smf_telefone = new SimpleMaskFormatter("(NN) NNNNN - NNNN");
        MaskTextWatcher mtw_telefone = new MaskTextWatcher(telefone,smf_telefone);
        telefone.addTextChangedListener(mtw_telefone);

        Intent it = getIntent();
        if(it.hasExtra("usuario")){
            usuario = (Usuario) it.getSerializableExtra("usuario");
            nome.setText(usuario.getNome());
            cpf.setText(usuario.getCpf());
            telefone.setText(usuario.getTelefone());
            email.setText(usuario.getEmail());
        }
    }
    public void cadastrar (View view){
        if (usuario == null) {
            Usuario u = new Usuario();
            u.setNome(nome.getText().toString());
            u.setCpf(cpf.getText().toString());
            u.setTelefone(telefone.getText().toString());
            u.setEmail(email.getText().toString());
            long id = dao.inserir(u);
            Toast.makeText(this, "Usuario inserido com id: " + id, Toast.LENGTH_SHORT).show();
        }else{
            usuario.setNome(nome.getText().toString());
            usuario.setCpf(cpf.getText().toString());
            usuario.setTelefone(telefone.getText().toString());
            usuario.setEmail(email.getText().toString());
            dao.atualizar(usuario);
            Toast.makeText(this, "Usuario foi atualizado ", Toast.LENGTH_SHORT).show();
        }
    }
}
