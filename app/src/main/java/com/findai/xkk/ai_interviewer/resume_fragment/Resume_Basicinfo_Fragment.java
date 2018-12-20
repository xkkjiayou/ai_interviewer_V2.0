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
public class Resume_Basicinfo_Fragment extends Fragment {

    EditText et_birthday;
    EditText et_ideal;
    EditText et_email;
    EditText et_tele;
    EditText et_gender;
    EditText et_nativeplace;
    EditText et_name;
    Resume resume = new Resume();
    Button btn_next_resume;
    ResumeInterface ri;

    public Resume_Basicinfo_Fragment(ResumeInterface cb) {
        this.ri = cb;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_basicinfo_resume_fragment, container, false);
        Bundle bundle = getArguments();

//        System.out.println(bundle.);
        resume = (Resume) bundle.getSerializable("resume");
        et_birthday = view.findViewById(R.id.et_birthday);
        et_ideal = view.findViewById(R.id.et_ideal);
        et_email = view.findViewById(R.id.et_email);
        et_tele = view.findViewById(R.id.et_tele);
        et_gender = view.findViewById(R.id.et_gender);
        et_nativeplace = view.findViewById(R.id.et_nativeplace);
        et_name = view.findViewById(R.id.et_name);

        et_birthday.setText(resume.getBirthday());
        et_ideal.setText(resume.getIdeal());
        et_email.setText(resume.getEmail());
        et_tele.setText(resume.getTele());
        et_gender.setText(resume.getGender());
        et_nativeplace.setText(resume.getNativeplace());
        et_name.setText(resume.getName());


//
//        btn_next_resume = view.findViewById(R.id.btn_next_resume);
//
//        btn_next_resume.setOnClickListener(this);
        return view;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        String set_birthday = et_birthday.getText().toString();
        String set_ideal = et_ideal.getText().toString();
        String set_email = et_email.getText().toString();
        String set_tele = et_tele.getText().toString();
        String set_gender = et_gender.getText().toString();
        String set_nativeplace = et_nativeplace.getText().toString();
        String set_name = et_name.getText().toString();
        String[] ss = new String[]{set_birthday, set_ideal, set_email, set_tele, set_gender, set_nativeplace, set_name};
        if (set_birthday == null || set_birthday.equals("")
                || set_ideal == null || set_ideal.equals("")
                || set_email == null || set_email.equals("")
                || set_tele == null || set_tele.equals("")
                || set_gender == null || set_gender.equals("")
                || set_nativeplace == null || set_nativeplace.equals("")
                || set_name == null || set_name.equals("")) {
            Toast.makeText(getContext(), "请填写内容哦", Toast.LENGTH_LONG).show();
            return;
        }
        ri.resume_basicinfo_interface_impl(ss);
    }

//    @Override
//    public void onClick(View v) {
//        String set_birthday = et_birthday.getText().toString();
//        String set_ideal = et_ideal.getText().toString();
//        String set_email = et_email.getText().toString();
//        String set_tele = et_tele.getText().toString();
//        String set_gender = et_gender.getText().toString();
//        String set_nativeplace = et_nativeplace.getText().toString();
//        String set_name = et_name.getText().toString();
//        String[] ss = new String[]{set_birthday,set_ideal,set_email,set_tele,set_gender,set_nativeplace,set_name};
//        if(set_birthday==null||set_birthday.equals("")
//                ||set_ideal==null||set_ideal.equals("")
//                ||set_email==null||set_email.equals("")
//                ||set_tele==null||set_tele.equals("")
//                ||set_gender==null||set_gender.equals("")
//                ||set_nativeplace==null||set_nativeplace.equals("")
//                ||set_name==null||set_name.equals("")){
//            Toast.makeText(getContext(),"请填写内容哦",Toast.LENGTH_LONG).show();
//            return;
//        }
//        ri.resume_basicinfo_interface_impl(ss);
//    }
}
