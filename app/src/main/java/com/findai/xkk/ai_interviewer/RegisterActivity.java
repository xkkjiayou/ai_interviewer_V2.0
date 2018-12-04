package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_username;
    EditText et_password;
    Button btn_register;
    String username;
    String password;
    Commiuncate_Server cs = new Commiuncate_Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        Intent intent = getIntent();
//        QuestionList questionList = (QuestionList)intent.getBundleExtra("bundle_questionlist").getSerializable("questionlist");
//        System.out.println(questionList.getQuestionList().get(0).answer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        et_password = findViewById(R.id.et_password);
        et_username= findViewById(R.id.et_username);
        btn_register = findViewById(R.id.btn_register);


        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if(username==null||username .equals("")||password==null||password.equals("")){
            Toast.makeText(this,"请输入账号密码哦",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
            try {

                String json = cs.post_register(user);
                Gson gson = new Gson();
                User login_user = gson.fromJson(json,User.class);
                if(login_user==null){
                    Toast.makeText(this,"账号已存在",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    ACache.get(this).put(GlobalParams.Para_USER,login_user);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",login_user);
                    intent.putExtra("user",bundle);
                    startActivity(intent);
                }


            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
