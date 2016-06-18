package cn.edu.zafu.easemob.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.edu.zafu.easemob.R;

/**
 * Created by AniChikage on 2016/6/16.
 */
public class Main extends Activity {
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();

        btn_login.setOnClickListener(new main_login());
        btn_register.setOnClickListener(new main_register());
    }

    private void init()
    {
        btn_login=(Button)findViewById(R.id.main_btn_login);
        btn_register=(Button)findViewById(R.id.main_btn_register);
    }

    class main_login implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(Main.this, Login.class);
            startActivity(intent);
        }
    }

    //跳转到注册界面
    class main_register implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(Main.this, Register.class);
            startActivity(intent);
        }
    }

}
