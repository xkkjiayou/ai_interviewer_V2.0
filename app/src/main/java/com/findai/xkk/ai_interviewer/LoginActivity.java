package com.findai.xkk.ai_interviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.JobList;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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
        et_username = findViewById(R.id.et_username);
        btn_login = findViewById(R.id.btn_login);
        btn_tiaozhuan_register = findViewById(R.id.btn_tiaozhuan_register);

        btn_login.setOnClickListener(this);
        btn_tiaozhuan_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                final Button btn_login = (Button) v;
                btn_login.setEnabled(false);
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (username == null || username.equals("") || password == null || password.equals("")) {
//                    Toast.makeText(this, "请输入账号密码哦", Toast.LENGTH_LONG).show();
                    TastyToast.makeText(getApplicationContext(), "请输入账号密码哦", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    btn_login.setEnabled(true);
                    return;
                } else {
                    user = new User();
                    user.setPassword(password);
                    user.setUsername(username);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Looper.prepare();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try{
                                String json = cs.post_login(user);
                                System.out.println(json);
                                Gson gson = new Gson();
                                User login_user = gson.fromJson(json, User.class);

                                if (!login_user.getStatus().equals("success")) {
                                    TastyToast.makeText(getApplicationContext(), login_user.getStatus(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//                                            btn_login.setEnabled(true);
                                } else {
//                                            btn_login.setText("登录中……");
//                                    Toast.makeText(getBaseContext(), "欢迎回来", Toast.LENGTH_LONG).show();
                                    TastyToast.makeText(getApplicationContext(), "欢迎回来", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                                    ACache.get(getBaseContext()).put(GlobalParams.Para_USER, login_user);

                                    final JobList joblist = cs.get_joblist(20);
                                    Intent intent = new Intent(getBaseContext(), JobCenterActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("joblist", joblist);
//                                    intent.putExtra("joblist", bundle);
//                                    bundle.putSerializable("user", login_user);
                                    intent.putExtra("joblist", bundle);
                                    startActivity(intent);
                                    finish();
//
//
//                                    startActivity(intent);
//                                    finish();
                                }
                                btn_login.setEnabled(true);
                                Looper.loop();
                            } catch (Exception ex) {
//                                        btn_login.setEnabled(true);
                                ex.printStackTrace();
//                                Looper.loop();
                            }

//                                }
//                            }});

                        }
                    });
                    thread.start();
                }
                break;
            case R.id.btn_tiaozhuan_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
        }
    }
}
