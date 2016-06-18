package cn.edu.zafu.easemob.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.zafu.easemob.R;
import cn.edu.zafu.easemob.netapp.ConnNet;

/**
 * Created by AniChikage on 2016/6/17.
 */
public class Login extends Activity{

    private Button login_btn_login;
    private EditText login_et_email;
    private EditText login_et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        login_btn_login.setOnClickListener(new login_login());
    }

    //初始化程序
    private void init(){
        login_btn_login=(Button)findViewById(R.id.login_btn_login);
        login_et_email=(EditText)findViewById(R.id.login_et_email);
        login_et_password=(EditText)findViewById(R.id.login_et_password);
    }

    //登录事件
    class login_login implements View.OnClickListener{
        @Override
        public void onClick(View v){
            new Thread(new Runnable() {

                public void run() {
                ConnNet operaton=new ConnNet();
                String result=operaton.doLogin(login_et_email.getText().toString(),login_et_password.getText().toString());
                Message msg=new Message();
                msg.obj=result;
                handler.sendMessage(msg);
            }
            }).start();
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String string=(String) msg.obj;
            Toast.makeText(Login.this, string, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
}
