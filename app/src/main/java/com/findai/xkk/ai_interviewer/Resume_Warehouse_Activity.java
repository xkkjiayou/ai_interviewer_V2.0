package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

public class Resume_Warehouse_Activity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout ll_myresume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_resume_warehouse_activity);

        User user = (User)ACache.get(getBaseContext()).getAsObject(GlobalParams.Para_USER);
        if(user == null){
            TastyToast.makeText(getApplicationContext(), "您尚未登录，请进行登录", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ll_myresume = findViewById(R.id.ll_myresume);
        ll_myresume.setOnClickListener(this);
        System.out.println("232323232__3232");
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_myresume:
                Intent intent = new Intent(getBaseContext(),WriteResumeActivity.class);
                startActivity(intent);
        }
    }
}
