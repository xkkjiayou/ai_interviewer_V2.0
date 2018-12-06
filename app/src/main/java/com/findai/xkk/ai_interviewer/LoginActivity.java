package com.findai.xkk.ai_interviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_username;
    EditText et_password;
    Button btn_login;
    Button btn_tiaozhuan_register;
    String username;
    String password;
    Commiuncate_Server cs = new Commiuncate_Server();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        user.setDeviceid(tm.getDeviceId(0));
//        System.out.println("232323232__3232");
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
        btn_login = findViewById(R.id.btn_login);
        btn_tiaozhuan_register = findViewById(R.id.btn_tiaozhuan_register);

        btn_login.setOnClickListener(this);
        btn_tiaozhuan_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (username == null || username.equals("") || password == null || password.equals("")) {
                    Toast.makeText(this, "请输入账号密码哦", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    user = new User();
                    user.setPassword(password);
                    user.setUsername(username);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String json = cs.post_login(user);
                                System.out.println(json);
                                Gson gson = new Gson();
                                User login_user = gson.fromJson(json, User.class);
                                Looper.prepare();
                                if (!login_user.getStatus().equals("success")) {
                                    Toast.makeText(getBaseContext(), login_user.getStatus(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "欢迎回来", Toast.LENGTH_LONG).show();
                                    ACache.get(getBaseContext()).put(GlobalParams.Para_USER, login_user);
                                    Intent intent = new Intent(getBaseContext(),JobCenterActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("user", login_user);
                                    intent.putExtra("user", bundle);
                                    startActivity(intent);
                                }
                                Looper.loop();
//                            Looper.loop();
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                        }
                    });
                    thread.start();
                }
                break;
            case R.id.btn_tiaozhuan_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
        }
    }
}
