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

public class StudentChooseApprovalActivity extends AppCompatActivity {

    private EditText editTextApprovalId;
    private EditText editTextApprovalReason;
    private Button backButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_choose_approval);

        editTextApprovalId = findViewById(R.id.editTextApprovalId);
        editTextApprovalReason = findViewById(R.id.editTextApprovalReason);
        backButton = findViewById(R.id.backButton);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String approvalId = editTextApprovalId.getText().toString();
                String reason = editTextApprovalReason.getText().toString();

                // 发送PUT请求
                Map<String, String> params = new HashMap<>();
                params.put("approvalId", approvalId);
                params.put("reason", reason);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String response = NetUtilPlus.net("students/chooseApproval", params, "PUT");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response != null && response.equals("true")) {
                                        Toast.makeText(StudentChooseApprovalActivity.this, "恭喜你，操作成功~", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(StudentChooseApprovalActivity.this, "操作失败了，哪里出现了错误喵~", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String studentId = getIntent().getStringExtra("studentId");
                Intent intent = new Intent(StudentChooseApprovalActivity.this,StudentApprovalActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("studentId",studentId);
                startActivity(intent);
                //finish();
            }
        });
    }
}
