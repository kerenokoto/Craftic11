package com.craftic.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.craftic.DAL.UserRepository;
import com.craftic.Entities.User;
import com.craftic.R;

import java.util.List;


/**
 * Created by keren on 4/28/15.
 */

public class LoginActivity extends Activity {

    private Button btnLogin,btnRegister;
    private EditText usernameInput, passwordInput;
    private String username, password;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btnLogin(v);
            }
        });
    }

    public void initializeView(){
        btnLogin = (Button) findViewById(R.id.loginBtn);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
    }

    public void btnLogin(View view)
    {
        username = usernameInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();


        User userfromDBD = null;
        try {
            UserRepository userrepo = new UserRepository(ctx);
            List<User> listofUsers4mDB = userrepo.getUser(username);
            userfromDBD = listofUsers4mDB.get(0);
        } catch (Exception e) {
           // e.printStackTrace();
        }

        if (userfromDBD != null)
        {
           boolean userCatType =  userfromDBD.getCategorytype().equalsIgnoreCase("Normal");
            if (userCatType)
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("uname", userfromDBD.getUsername());
                startActivity(intent);
            }
            else
            {
                Intent myIntent =new Intent(LoginActivity.this, HomeActivity.class);

                myIntent.putExtra("uname", userfromDBD.getUsername());
                startActivity(myIntent);
            }
        }

       /*
         String myHint = "Username/Password cannot be less than 6 characters";
        String usernameAndPasswordCannotBeEmpty = "Username and Password cannot be Empty";
        String usernameOrPasswordCannotBeEmpty = "Username or Password Field cannot be Empty";
        String success = "Successfully Logged In";
        String myUserName = "kerenjay";
        String myPassword = "admin123";

       if (username.equals(myUserName)){
            if(password.equals(myPassword)){
                Toast.makeText(this, success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

        else if(username.length() <= 5){
            if(password.length() <=5){
                Toast.makeText(this, myHint, Toast.LENGTH_LONG).show();
            }
        }*/


        /*else if (username.equals("") && password.equals("")){
            Toast.makeText(this, usernameAndPasswordCannotBeEmpty, Toast.LENGTH_LONG).show();
        }*

        /*else if (password.equals("") || password.equals("")){
            Toast.makeText(this, usernameOrPasswordCannotBeEmpty, Toast.LENGTH_LONG).show();
        }*/

        else{
            Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
        }



    }

}
