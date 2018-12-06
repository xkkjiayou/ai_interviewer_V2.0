package com.findai.xkk.ai_interviewer.Http;

import android.util.Log;

import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.Resume;
import com.findai.xkk.ai_interviewer.domain.ResumeWrapper;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Commiuncate_Server {

    Gson gson = new Gson();
    String get_question_url = "http://115.159.59.188:5000/get_question_by_industry_id?iid=";
    String post_answer_url = "http://115.159.59.188:5000/post_answer";
    String post_login_url = "http://115.159.59.188:5000/login";
    String post_register_url = "http://115.159.59.188:5000/register";
    String post_resume_url = "http://115.159.59.188:5000/add_resume";
    String get_resume_by_uid_url = "http://115.159.59.188:5000/get_resume_by_uid?uid=";

    public QuestionList get_question_by_iid(int qid) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String qurl = get_question_url + qid;
        System.out.println(qurl);
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
            System.out.println(str);
            return get_question_list(str);
        } else {

            throw new IOException("Unexpected code " + response);
        }
//        return get_question_list("");
    }

    public String get_my_report_by_id(int uid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String qurl = get_question_url + "get_user_report_list/?userid=" + uid;
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    private QuestionList get_question_list(String resp_json) throws Exception {

//        resp_json = "{\"questionList\":[\n" +
//                "\t\t\t\t{\"qid\":11,\"qtitle\":\"人工智能好不好\",\"question_choose_items\":[\"好的图塔糊涂哦\",\"不好\",\"凑合吧\"],\"answer\":\"\",\"type\":\"0\"}\t,{\"qid\":4,\"qtitle\":\"LSTM原理是什么样的，请简述\",\"question_choose_items\":[],\"answer\":\"\",\"type\":\"1\"},{\"qid\":5,\"qtitle\":\"计算机好不好\",\"question_choose_items\":[\"好\",\"不好\"],\"answer\":\"\",\"type\":\"0\"}\t\t\t\n" +
//                "\t\t\t\t\t\n" +
//                "\n" +
//                "\n" +
//                "\t\t\t\t]}";
        JSONObject json = new JSONObject(resp_json);
        System.out.println(resp_json);
        Gson gson = new Gson();
        QuestionList qlist = gson.fromJson(resp_json, QuestionList.class);
        return qlist;
    }

    public String post_answer(String answer) throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES); // read timeout

        OkHttpClient client = builder.build();
        String url = post_answer_url;
        System.out.println(url);

//        okHttpClient = builder.build();
//        client.conn(30, TimeUnit.SECONDS); // connect timeout
//        client.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout
        RequestBody requestbody = new FormBody.Builder()
                .add("user_answer", answer)
                .build();

        Request request = new Request.Builder()
                .url(post_answer_url)
                .post(requestbody)
                .build();
        System.out.println(request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("面试结果上传", jsonString);

//            Gson gson = new Gson();
            return jsonString;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }


    }

    public String post_login(User user) throws Exception{

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user",gson.toJson(user))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_login_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("登录",jsonString);
            return jsonString;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    public String post_register(User user) throws Exception{
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user",gson.toJson(user))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_register_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("注册",jsonString);
            return jsonString;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }


    public String post_resume(Resume resume) throws Exception{
        OkHttpClient client = new OkHttpClient();
        System.out.println("上传的简历："+gson.toJson(resume));
        RequestBody requestBody = new FormBody.Builder()
                .add("resume",gson.toJson(resume))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_resume_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("注册",jsonString);
            return jsonString;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }


    public Resume get_resume_by_uid(User user) throws Exception{

        OkHttpClient client = new OkHttpClient();
        String qurl = get_resume_by_uid_url + user.getUid();
        System.out.println(qurl);
        Resume resume = new Resume();
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
            System.out.println(str);
            ResumeWrapper rw  = gson.fromJson(str,ResumeWrapper.class);
            if(rw.getStatus().equals("success")){
                resume = rw.getResume();
            }else
            {
                resume = new Resume();
            }
            return resume;
        } else {

//            throw new IOException("Unexpected code " + response);
            return new Resume();

        }
    }
}
