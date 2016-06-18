package cn.edu.zafu.easemob.netapp;

import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Moe Winder on 2016/5/12.
 */
public class ConnNet {

    private static final  String urlLogin="http://182.48.119.26:18080/Therapista/user/login";
    //private static final String URLVAR="http://182.48.119.26:18080/Test/LoginController/login.json";
    //暂时做参考，未调用
    public String getConn(String urlpath) {
        String result = "";
        try {
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username","admin"));
            params.add(new BasicNameValuePair("password","root"));

            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost = new HttpPost(urlpath);
            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
                result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
            else
            {
                result="登录失败！";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = e.toString();
        }
        return result;

    }

    //注册事件
    public String doRegister(String urlpath, String email, String password, String telephone)
    {
        String result = "";
        if(!isEmail(email)){
            result = "邮箱格式不正确，请重新输入";
        }
        else if(!isTelephone(telephone)){
            result = "手机号码格式不正确，请重新输入";
        }
        else if(!checkPassword(password)){
            result = "密码应不少于6位";
        }
        else{
            try {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email",email));
                params.add(new BasicNameValuePair("password",password));
                params.add(new BasicNameValuePair("tel",telephone));

                HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                HttpPost httpPost = new HttpPost(urlpath);
                httpPost.setEntity(entity);
                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(httpPost);

                if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
                {
                    result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                    Log.v("asd",result);
                    try{
                        String[] result_temp = result.split("\"");
                        result = result_temp[4];
                        result = result.replace("\\","");
                    }
                    catch (Exception ex){
                        ex.printStackTrace();;
                    }
                }
                else
                {
                    result="注册失败！";
                }

            } catch (Exception e) {
                e.printStackTrace();
                result = e.toString();
            }
        }
        return result;
    }

    public String doLogin(String email, String password) {
        String result = "";
        try {
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",email));
            params.add(new BasicNameValuePair("password",password));

            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost = new HttpPost(urlLogin);
            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
                result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
            else
            {
                result="登录失败！";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = e.toString();
        }
        return result;
    }

    //解析JSON
    public String parseJson(String jsonstr){
        String status="12";
        try{
            JSONObject jsonobj = new JSONObject(jsonstr);
            status = jsonobj.getString("addUser");
        }
        catch (JSONException ex){
            ex.printStackTrace();;
        }
        return status;
    }

    //判断邮箱地址合法性
    private boolean isEmail(String email){
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //判断手机号码合法性
    private boolean isTelephone(String tel){
        return tel.matches("[1][358]\\d{9}");
    }

    //密码长度大于6
    private boolean checkPassword(String password){
        return password.length()>=6 ? true:false;
    }

}
