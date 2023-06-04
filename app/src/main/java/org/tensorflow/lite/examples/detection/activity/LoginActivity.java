package org.tensorflow.lite.examples.detection.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.retrofit.ApiBanHang;
import org.tensorflow.lite.examples.detection.retrofit.RetrofitClient;
import org.tensorflow.lite.examples.detection.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView txtdangki;
    Button btndangnhap;
    EditText email,pass;
    FirebaseAuth firebaseAuth;

    FirebaseUser user;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang ;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }


    private void login() {
        String emailedit,passedit;
        emailedit= email.getText().toString().trim();
        passedit=pass.getText().toString().trim();
        if(TextUtils.isEmpty(emailedit)){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(passedit)){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        } else {
            //save
            Paper.book().write("email",emailedit);
            Paper.book().write("pass",passedit);
            if(user != null){
                //user da co dang nhap firebase
                dangNhap(emailedit,passedit);
            }else{
                //user da signout
                firebaseAuth.signInWithEmailAndPassword(emailedit,passedit)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    dangNhap(emailedit,passedit);
                                }
                            }
                        });
            }


            dangNhap(emailedit,passedit);


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


        private void dangNhap(String email, String pass) {
            compositeDisposable.add(apiBanHang.dangNhap(email,pass)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {

                                if(userModel.isSuccess()){
                                    isLogin = true;
                                    Paper.book().write("islogin",isLogin);
                                    Utils.user_current = userModel.getResult().get(0);
                                    //luu lai thong tin nguoi dung
                                    Paper.book().write("user",userModel.getResult().get(0));
                                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }


    private void AnhXa() {
        Paper.init(this);
        firebaseAuth= FirebaseAuth.getInstance();
        email= findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        btndangnhap = findViewById(R.id.btndangnhap);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        if(Paper.book().read("email")!= null && Paper.book().read("pass") != null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
            if(Paper.book().read("islogin") != null) {
                boolean flag = Paper.book().read("islogin");
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },1000);
                }
            }

        }

    }

}