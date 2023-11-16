package com.example.student_course_selection_system.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.student_course_selection_system.R;
import com.example.student_course_selection_system.Util.NetUtilPlus;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 创建参数的键值对映射
                final Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                // 使用Thread执行后台网络请求
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 使用NetUtilPlus类发送POST请求
                            final String response = NetUtilPlus.net("login", params, "POST");
                            // 在主线程中处理返回值
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在请求完成后处理返回值
                                    if (response != null) {
                                        // 输出返回值的全部内容
                                        Log.d("LoginActivity", response);
                                        if(response.equals("student")){
                                            Intent intent = new Intent(LoginActivity.this, CourseActivity.class);
                                            intent.putExtra("username", username); // 将用户名作为额外数据传递
                                            startActivity(intent);
                                        }
                                        if(response.equals("teacher")){
                                            Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);
                                            intent.putExtra("username", username); // 将用户名作为额外数据传递
                                            startActivity(intent);
                                        }
                                        if(response.equals("administrator")){
                                            Intent intent = new Intent(LoginActivity.this, AdministratorActivity.class);
                                            intent.putExtra("username", username); // 将用户名作为额外数据传递
                                            startActivity(intent);
                                        }
                                    } else {
                                        // 请求失败
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                // 启动线程
                thread.start();
            }
        });
    }
}
