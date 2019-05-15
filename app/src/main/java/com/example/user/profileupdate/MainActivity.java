package com.example.user.profileupdate;

import android.app.AlertDialog;
import android.arch.core.executor.TaskExecutor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {


    private EditText et2;
    private Button b1;
    private Button b2;
    private String verificationId;
    private FirebaseAuth mAuth;
    String name3,email3,adhar3,image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = getInstance();

        //et1 = (EditText) findViewById(R.id.editText_mobile);

        final EditText et1 = (EditText) findViewById(R.id.editText_mobile);
        et2 = (EditText) findViewById(R.id.editText_otp);
        b1 = (Button) findViewById(R.id.button_next);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String number = et1.getText().toString().trim();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("id",number);
                editor.commit();





                if (number.isEmpty() || number.length() < 10) {
                    et1.setError("number is required");
                    et1.requestFocus();
                    return;
                }

                sendVerificationCode(number);



                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                          //  boolean success = jsonResponse.getBoolean("success");

                            name3 = jsonResponse.getString("ename");
                            Log.d("phonetype value ", jsonResponse.getString("ename"));

                            email3 = jsonResponse.getString("pemail");
                            Log.d("phonetype value ", jsonResponse.getString("pemail"));

                             adhar3 = jsonResponse.getString("padhar");
                            Log.d("phonetype value ", jsonResponse.getString("padhar"));

                             image_path = jsonResponse.getString("image_path");
                            Log.d("phonetype value ", jsonResponse.getString("image_path"));
                            System.out.println(image_path);







//                            if (success){
//
//
//                            }
//                            else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setMessage("Register failed")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(number, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(registerRequest);


            }
        });


        b2 = (Button) findViewById(R.id.button_submit);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = et2.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {

                    et2.setError("enter code...");
                    et2.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    public void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    public void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Intent intent = new Intent(MainActivity.this, Profile1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         if (image_path==null){
                                image_path="0";
                         }
                             Bundle bundle=new Bundle();
                             bundle.putString("ename",name3);
                            bundle.putString("pemail",email3);
                            bundle.putString("padhar",adhar3);
                            bundle.putString("image_path",image_path);
                            Log.d("TAG", "onComplete: "+image_path);
                              intent.putExtras(bundle);

//                            intent.putExtra("ename",name3);
//                            intent.putExtra("pemail",email3);
//                            intent.putExtra("padhar",adhar3);
//                            intent.putExtra("image_path",image_path);

                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                et2.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();



        }
    };

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//
//            Intent intent = new Intent(this, Main2Activity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//            startActivity(intent);
//        }
//    }
}