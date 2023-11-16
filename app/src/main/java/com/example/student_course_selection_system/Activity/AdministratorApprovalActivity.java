package com.example.student_course_selection_system.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class AdministratorApprovalActivity extends AppCompatActivity {

    private ListView approvalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_show_approval);

        approvalListView = findViewById(R.id.approvalListView);

        fetchApprovals();

        findViewById(R.id.returnButton).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.approveButton).setOnClickListener(view -> {
            Intent intent = new Intent(AdministratorApprovalActivity.this,AdministratorChangeApprovalActivity.class);
            startActivity(intent);
        });
    }

    private void fetchApprovals() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = NetUtilPlus.net("approvals", (Map<String, String>) null, "GET");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                List<String> approvalStrings = parseApprovalList(response);
                                if (approvalStrings != null && !approvalStrings.isEmpty()) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                            AdministratorApprovalActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            approvalStrings
                                    );
                                    approvalListView.setAdapter(adapter);
                                } else {
                                    Toast.makeText(AdministratorApprovalActivity.this, "No approvals found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdministratorApprovalActivity.this, "Failed to get approvals", Toast.LENGTH_SHORT).show();
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

    private List<String> parseApprovalList(String response) {
        try {
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

                approvalStrings.add("Approval " + (i + 1) + ": " + approval.toString());
            }
            return approvalStrings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
