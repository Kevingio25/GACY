package com.kevinsolano.firebase_tutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText mCorreoLogin, mContrasenaLogin, mCorreoRegis, mContrasenaRegis;

    private Button mBotonLogin, mBotonRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCorreoLogin = (EditText) findViewById(R.id.correoLogin);
        mContrasenaLogin = (EditText) findViewById(R.id.contrasenaLogin);
        mCorreoRegis = (EditText) findViewById(R.id.correoRegis);
        mContrasenaRegis = (EditText) findViewById(R.id.contrasenaRegis);
        mBotonLogin = (Button) findViewById(R.id.botonInicioS);
        mBotonRegis = (Button) findViewById(R.id.botonRegis);

        mAuth = FirebaseAuth.getInstance(); // verifica si tiene inicio de sesión o no
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;

                }

            }
        };

        mBotonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = mCorreoRegis.getText().toString();
                String contrasena = mContrasenaRegis.getText().toString();
                mAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Error en cerrar de sesión", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });


        mBotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = mCorreoLogin.getText().toString();
                String contrasena = mContrasenaLogin.getText().toString();
                mAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
