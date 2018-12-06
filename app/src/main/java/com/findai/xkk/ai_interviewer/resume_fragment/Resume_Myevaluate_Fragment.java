package com.findai.xkk.ai_interviewer.resume_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.domain.Resume;

@SuppressLint("ValidFragment")
public class Resume_Myevaluate_Fragment extends Fragment implements View.OnClickListener{
    EditText et_myevaluate;
    Button btn_submit_resume;
    Resume resume = new Resume();
    ResumeInterface ri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_myevaluate_resume_fragment, container, false);
        Bundle bundle = getArguments();
        resume = (Resume)bundle.getSerializable("resume");
        et_myevaluate = view.findViewById(R.id.et_evaluation);
        btn_submit_resume = view.findViewById(R.id.btn_submit_resume);
        btn_submit_resume.setOnClickListener(this);
        et_myevaluate.setText(resume.getSelfdescription());
        return view;
    }

    public Resume_Myevaluate_Fragment(ResumeInterface cb){
        this.ri = cb;
    }

    @Override
    public void onClick(View v) {
        String s = et_myevaluate.getText().toString();
        if(s==null||s.equals("")){
            Toast.makeText(getContext(),"请填写内容哦",Toast.LENGTH_LONG).show();
            return;
        }
        ri.resume_evalution_interface_impl(s);
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        String s = et_myevaluate.getText().toString();
//        if(s==null||s.equals("")){
//            Toast.makeText(getContext(),"请填写内容哦",Toast.LENGTH_LONG).show();
//            return;
//        }
//        ri.resume_evalution_interface_impl(s);
//    }

}
