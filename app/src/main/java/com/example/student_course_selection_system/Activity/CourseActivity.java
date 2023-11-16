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
import com.example.student_course_selection_system.Model.Selection;
import com.example.student_course_selection_system.R;
import com.example.student_course_selection_system.Util.NetUtilPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity {

    private ListView selectionListView;
    private Button exitButton;
    private Button viewApplicationsButton;
    int j=1;
    private JSONArray jsonArray; // 声明一个成员变量来保存解析的JSONArray

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        selectionListView = findViewById(R.id.courseListView); // 注意修改ListView的ID
        exitButton = findViewById(R.id.exitButton);
        viewApplicationsButton = findViewById(R.id.viewApplicationButton);

        // 使用NetUtilPlus类发送GET请求获取所有Selections
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = NetUtilPlus.net("selections", (Map<String, String>) null, "GET"); // 注意修改请求的URL
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                // 解析返回的Selection列表数据
                                jsonArray = JSON.parseArray(response); // 将解析的JSONArray保存到成员变量中
                                List<String> selectionStrings = parseSelectionList();
                                if (selectionStrings != null && !selectionStrings.isEmpty()) {
                                    // 将Selection数据显示在ListView中
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                            CourseActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            selectionStrings
                                    );
                                    selectionListView.setAdapter(adapter);
                                } else {
                                    Toast.makeText(CourseActivity.this, "目前还没有选课哟~", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(CourseActivity.this, "获取课表失败了~", Toast.LENGTH_SHORT).show();
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

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
            }
        });

        viewApplicationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到学生申请界面
                String username = getIntent().getStringExtra("username");
                String studentId = getStudentId(username);
                System.out.println(username);
                System.out.println(studentId);
                if (studentId != null) {
                    Intent intent = new Intent(CourseActivity.this, StudentApprovalActivity.class);
                    intent.putExtra("studentId", studentId);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(CourseActivity.this, "学生不存在喵~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<String> parseSelectionList() {
        try {
            List<String> selectionStrings = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String stuName = jsonObject.getString("stuName");
                String courseId = jsonObject.getString("courseId");
                String studentId = jsonObject.getString("studentId");
                String teacherName = jsonObject.getString("teacherName");
                String courseName = jsonObject.getString("courseName");

                Selection selection = new Selection(id, stuName, courseId, studentId, teacherName, courseName);
                String username = getIntent().getStringExtra("username");
                if (stuName.equals(username)) {
                    selectionStrings.add("Selection " + (j++) + ": " + selection.toString());
                }
            }

            return selectionStrings;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getStudentId(String username) {
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String stuName = jsonObject.getString("stuName");
                String studentId = jsonObject.getString("studentId");
                System.out.println("studentName:" + stuName);
                if (stuName.equals(username)) {
                    return studentId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(username.equals("zhangsan")){
            return "20216645";
        }
        return "20226645";
    }
}
