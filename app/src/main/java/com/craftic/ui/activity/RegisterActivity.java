package com.craftic.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.craftic.DAL.UserRepository;
import com.craftic.Entities.User;
import com.craftic.R;

/**
 * Created by keren on 4/29/15.
 */
public class RegisterActivity extends Activity {
    private Button btnRegister, loginBtn;
    private EditText firstnameinput, lastnameinput, usernameInput, passwordInput, phonenumberInput;
    private Spinner category;
    Context ctx = this;
    private Intent intent;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeView();

     loginBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             intent.setClass(ctx,LoginActivity.class);
             startActivity(intent);
         }
     });

     btnRegister.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             registerThisUser();
         }
     });

    }

//TODO: Add a method to get users location
    public void initializeView() {
        intent = new Intent();
        btnRegister = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        firstnameinput = (EditText) findViewById(R.id.fname);
        lastnameinput = (EditText) findViewById(R.id.lname);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        phonenumberInput = (EditText) findViewById(R.id.phonenumber);

        category = (Spinner) findViewById(R.id.sp_category);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        String myHint = "Username/Password cannot be less than 6 characters";
        String FieldCannotBeEmpty = "Field cannot be Empty";
//        String usernameOrPasswordCannotBeEmpty = "Username or Password Field cannot be Empty";
        String success = "Successfully Signed In";




    }

   public void registerThisUser()
   {

       User saveThisUserInDB = new User();
       saveThisUserInDB.setFname(firstnameinput.getText().toString());
       saveThisUserInDB.setLname(lastnameinput.getText().toString());
       saveThisUserInDB.setUsername(usernameInput.getText().toString());
       saveThisUserInDB.setPassword(passwordInput.getText().toString());
       saveThisUserInDB.setPhonenumber(phonenumberInput.getText().toString());
       saveThisUserInDB.setCategorytype(category.getSelectedItem().toString());

       UserRepository userepo = new UserRepository(ctx);

       if ( userepo.create(saveThisUserInDB) != 0 )
       {
           String success = "sucessfully registered";
           Toast.makeText(this, success, Toast.LENGTH_LONG).show();
       }
      String cat = category.getSelectedItem().toString();
      if(cat.equalsIgnoreCase("normal")){
         // intent.setClass(ctx,MainActivity.class);
          intent.setClass(ctx,LoginActivity.class);

      }else{

          //intent.setClass(ctx,HomeActivity.class);
          intent.setClass(ctx,LoginActivity.class);
          intent.putExtra("category",cat);
      }

       startActivity(intent);

   }
}
