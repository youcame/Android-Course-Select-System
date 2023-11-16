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
import com.example.student_course_selection_system.Model.Student;
import com.example.student_course_selection_system.Model.Teacher;
import com.example.student_course_selection_system.R;
import com.example.student_course_selection_system.Util.NetUtilPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdministratorActivity extends AppCompatActivity {

    private ListView studentListView;
    private ListView teacherListView;
    private Button viewApprovalButton;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        backButton = findViewById(R.id.backButton);
        studentListView = findViewById(R.id.studentListView);
        teacherListView = findViewById(R.id.teacherListView);
        viewApprovalButton = findViewById(R.id.viewApprovalButton);

        fetchStudents();
        fetchTeachers();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdministratorActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        viewApprovalButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdministratorActivity.this, AdministratorApprovalActivity.class);
            startActivity(intent);
        });
    }



    private void fetchStudents() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = NetUtilPlus.net("students", (Map<String, String>) null, "GET");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                List<String> studentStrings = parseStudentList(response);
                                if (studentStrings != null && !studentStrings.isEmpty()) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                            AdministratorActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            studentStrings
                                    );
                                    studentListView.setAdapter(adapter);
                                } else {
                                    Toast.makeText(AdministratorActivity.this, "No students found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdministratorActivity.this, "Failed to get students", Toast.LENGTH_SHORT).show();
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

    private List<String> parseStudentList(String response) {
        try {
            JSONArray jsonArray = JSON.parseArray(response);
            List<String> studentStrings = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String password = jsonObject.getString("password");
                Student student = new Student(id, password, name);
                studentStrings.add(student.toString());
            }
            return studentStrings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fetchTeachers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = NetUtilPlus.net("teachers", (Map<String, String>) null, "GET");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                List<String> teacherStrings = parseTeacherList(response);
                                if (teacherStrings != null && !teacherStrings.isEmpty()) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                            AdministratorActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            teacherStrings
                                    );
                                    teacherListView.setAdapter(adapter);
                                } else {
                                    Toast.makeText(AdministratorActivity.this, "No teachers found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdministratorActivity.this, "Failed to get teachers", Toast.LENGTH_SHORT).show();
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

    private List<String> parseTeacherList(String response) {
        try {
            JSONArray jsonArray = JSON.parseArray(response);
            List<String> teacherStrings = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String password = jsonObject.getString("password");
                Teacher teacher = new Teacher(id, password, name);
                teacherStrings.add(teacher.toString());
            }
            return teacherStrings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
