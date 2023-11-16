package com.example.student_course_selection_system.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.student_course_selection_system.Model.Approval;
import com.example.student_course_selection_system.R;
import com.example.student_course_selection_system.Util.NetUtilPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherActivity extends AppCompatActivity {

    private ListView approvalListView;
    private Button backButton;
    private Button chooseApprovalButton;
    int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        approvalListView = findViewById(R.id.approvalListView);
        backButton = findViewById(R.id.backButton);
        chooseApprovalButton = findViewById(R.id.chooseApprovalButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this,LoginActivity.class);
                startActivity(intent);
                //finish(); // 关闭当前Activity
            }
        });

        chooseApprovalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherActivity.this,TeacherChangeApprovalActivity.class);
                startActivity(intent);
            }
        });

        // 使用NetUtilPlus类发送GET请求获取所有审批信息
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = NetUtilPlus.net("approvals", (Map<String, String>) null, "GET");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                // 解析返回的审批信息列表数据
                                List<String> approvalStrings = parseApprovalList(response);
                                if (approvalStrings != null && !approvalStrings.isEmpty()) {
                                    // 将审批信息数据显示在ListView中
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                            TeacherActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            approvalStrings
                                    );
                                    approvalListView.setAdapter(adapter);
                                } else {
                                    Toast.makeText(TeacherActivity.this, "目前还没有审批哦！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(TeacherActivity.this, "获取审批失败~哪里出现了问题呢", Toast.LENGTH_SHORT).show();
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

    private List<String> parseApprovalList(String response) {
        try {
            // 使用 fastjson 解析 JSON 数据
            JSONArray jsonArray = JSON.parseArray(response);
            List<String> approvalStrings = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String state = jsonObject.getString("state");
                String result = jsonObject.getString("result");
                String studentId = jsonObject.getString("studentId");
                String teacherId = jsonObject.getString("teacherId");
                String beginTime = jsonObject.getString("beginTime");
                String endTime = jsonObject.getString("endTime");
                String courseName = jsonObject.getString("courseName");
                String rejectReason = jsonObject.getString("rejectReason");
                String chooseReason = jsonObject.getString("chooseReason");
                String confirm = jsonObject.getString("confirm");
                String secondResult = jsonObject.getString("secondResult");

                Approval approval = new Approval(studentId, teacherId, id, state, result, beginTime, endTime, courseName);
                approval.setRejectReason(rejectReason);
                approval.setChooseReason(chooseReason);
                approval.setConfirm(confirm);
                approval.setSecondResult(secondResult);
                approvalStrings.add("Approval " + (j++) + ": " + approval.toString());
            }

            return approvalStrings;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
