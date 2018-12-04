package com.findai.xkk.ai_interviewer.job_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findai.xkk.ai_interviewer.LoginActivity;
import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.Resume_Warehouse_Activity;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.WelcomeIndexActivity;
import com.findai.xkk.ai_interviewer.domain.User;

@SuppressLint("ValidFragment")
public class Myinfo_Index_maintop_Fragment extends Fragment implements View.OnClickListener{

    LinearLayout ll_myingo_setting;
    TextView tv_username;
    TextView tv_userinfo;
    LinearLayout ll_user_panel ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myinfo_fragment, container, false);
        User user = (User) ACache.get(getContext()).getAsObject(GlobalParams.Para_USER);
        tv_userinfo = view.findViewById(R.id.tv_userinfo);
        tv_username = view.findViewById(R.id.tv_nickname);
        ll_myingo_setting = view.findViewById(R.id.ll_myingo_setting);
        ll_myingo_setting.setOnClickListener(this);
        if(user == null){
            tv_username.setText("请登录");
            tv_userinfo.setText("");
            ll_user_panel = view.findViewById(R.id.ll_user_panel);
            ll_user_panel.setOnClickListener(this);
        }else {

            tv_username.setText(user.getNickname());
            tv_userinfo.setText(""+user.getDegree()+" | "+user.getUniversity()+" | "+user.getMajor()+"");

        }
//        RadioGroup radioGroup = view.findViewById(R.id.tv_question_radio);
//        Bundle bundle = getArguments();
//        Question q = (Question) bundle.getSerializable("question");
//        TextView qtitle = (TextView) view.findViewById(R.id.tv_question_title);
//        qtitle.setText(q.getTitle());
//        int i = 0;
//        for (String qchoose_item : q.getQuestion_choose_items()) {
//            RadioButton rb = new RadioButton(getContext());
//            rb.setId(i);
////            rb.setb
//            rb.setText(qchoose_item);
//            i++;
//            radioGroup.addView(rb);
//        }
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                System.out.println("------------------");
////                String answer = ((RadioButton)group.getChildAt(checkedId)).getText().toString();
////                System.out.println(answer);
//                callbackQuestion_choose_fragment.get_question_answer(checkedId);
//            }
//        });
//        btn_kj = view.findViewById(R.id.btn_kj_interview);
//        btn_kj.setOnClickListener(this);
        return view;

    }

//    public interface callbackQuestion_Choose_Fragment {
//        public int get_question_answer(int answer);
//    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ll_user_panel:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_myingo_setting:
                intent = new Intent(getContext(), Resume_Warehouse_Activity.class);
                startActivity(intent);
                break;


        }
    }
}
