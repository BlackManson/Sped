package com.dell.sped;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmailLogin;
    EditText editTextPasswordLogin;
    Button buttonLoginIn;
    ProgressDialog progressDialogLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmailLogin = (EditText)findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = (EditText)findViewById(R.id.editTextPasswordLogin);
        buttonLoginIn = (Button)findViewById(R.id.buttonLoginIn);
        progressDialogLogin = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        buttonLoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmailLogin.getText().toString().trim();
                String password = editTextPasswordLogin.getText().toString().trim();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    progressDialogLogin.setTitle("Logowanie");
                    progressDialogLogin.setMessage("Proszę czekać");
                    progressDialogLogin.setCanceledOnTouchOutside(false);
                    progressDialogLogin.show();
                    loginUser(email,password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialogLogin.dismiss();
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    progressDialogLogin.hide();
                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
