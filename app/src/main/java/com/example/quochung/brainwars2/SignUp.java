package com.example.quochung.brainwars2;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone, edtUsername, edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtUsername = (MaterialEditText) findViewById(R.id.edtUsername);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
            }
        });
    }

}

