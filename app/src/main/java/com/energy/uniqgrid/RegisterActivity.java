package com.energy.uniqgrid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {


    TextView tvLogo,tvAlreadyReg;
    Button bRegister;
    TextInputLayout tilName,tilEmailId,tilPhone,tilPassword;
    String  name,emailid,phone,password;
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        tvLogo = (TextView)findViewById(R.id.tvLogo);
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilEmailId = (TextInputLayout) findViewById(R.id.tilEmailId);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        tvAlreadyReg = (TextView) findViewById(R.id.tvAlreadyReg);

        // Changing Font of Uniqgrid
        Typeface ocrExtendedFont = Typeface.createFromAsset(getAssets(),  "fonts/ocrExtended.TTF");
        tvLogo.setTypeface(ocrExtendedFont);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = tilName.getEditText().getText().toString();
                emailid = tilEmailId.getEditText().getText().toString();
                phone = tilPhone.getEditText().getText().toString();
                password = tilPassword.getEditText().getText().toString();

                if(name.isEmpty()) {
                    tilName.getEditText().setError("Please enter your Name");
                }else if (emailid.isEmpty()) {
                    tilEmailId.getEditText().setError("Please enter your Email-Id");
                } else if (phone.isEmpty()) {
                    tilPhone.getEditText().setError("Please enter your phone no.");
                }else if (password.isEmpty()) {
                    tilPassword.getEditText().setError("Please enter Password");
                }else if(!isValidName(name)) {
                    tilName.getEditText().setError("Name is too short");
                }else if (!isValidEmailAddress(emailid)) {
                    tilEmailId.getEditText().setError("Please enter a valid Email Address");
                } else if (!isValidPhone(phone)) {
                    tilPhone.getEditText().setError("Please enter a valid phone no.");
                }else if (!isValidPassword(password)) {
                    tilPassword.getEditText().setError("Password is too short");
                }else{


                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                   /*  intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("proPicUrl",proPicUrl);*/
                    startActivity(intent);

                               /*  try {
                                   loginInput.put("email",emailid);
                                    loginInput.put("password",password);

                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                  //  pd.show();
                                   // loginConnection();



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } */


                }





            }
        });


        tvAlreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isValidName(String name){
        if(name.length()>=1){
            return  true;
        } else {
            return false;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidPhone(String password){
        if(password.length()>=6){
            return  true;
        } else {
            return false;
        }
    }



    public boolean isValidPassword(String password){
        if(password.length()>=6){
            return  true;
        } else {
            return false;
        }
    }


}
