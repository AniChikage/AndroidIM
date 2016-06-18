package cn.edu.zafu.easemob.activity;

import android.app.Activity;
import android.content.Intent;
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
 * Created by Moe Winder on 2016/5/12. comicyueyu@gmail.com
 */

        public class Register extends Activity {

            private Button btn_register;
            private Button btn_return;
            private EditText et_email;
            private EditText et_password;
            private EditText et_telephone;
            private static String urlPath="http://182.48.119.26:18080/Therapista/user/addUser";
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.register);

                init();
                btn_register.setOnClickListener(new conn());
                btn_return.setOnClickListener(new returnMain());
    }

    //初始化各参数
    private void init()
    {
        btn_register=(Button)findViewById(R.id.register_btn_register);
        btn_return=(Button)findViewById(R.id.register_btn_return);
        et_email=(EditText)findViewById(R.id.register_et_email);
        et_password=(EditText)findViewById(R.id.register_et_password);
        et_telephone=(EditText)findViewById(R.id.register_et_telephone);
    }

    //注册函数
    class conn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {

                public void run() {
                    ConnNet operaton=new ConnNet();
                    String result=operaton.doRegister(urlPath,et_email.getText().toString(),et_password.getText().toString(),et_telephone.getText().toString());
                    Message msg=new Message();
                    msg.obj=result;
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }

    //返回主界面
    class returnMain implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(Register.this, Main.class);
            Register.this.finish();
            startActivity(intent);
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String string=(String) msg.obj;
            if(string.equals("registered email")){
                Toast.makeText(Register.this, "该邮箱已经注册！", Toast.LENGTH_SHORT).show();
            }
            else if(string.equals("1")){
                Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Register.this, string, Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
}
