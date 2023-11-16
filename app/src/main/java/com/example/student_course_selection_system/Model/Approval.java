package com.example.student_course_selection_system.Model;

public class Approval {
    private String studentId;
    private String teacherId;
    private String id;
    private String state;
    private String result;
    private String beginTime;
    private String endTime;
    private String courseName;
    private String rejectReason;
    private String chooseReason;
    private String confirm;
    private String secondResult;
    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getSecondResult() {
        return secondResult;
    }

    public void setSecondResult(String secondResult) {
        this.secondResult = secondResult;
    }

    public Approval(String studentId, String teacherId, String id, String state, String result, String beginTime, String endTime, String courseName) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.id = id;
        this.state = state;
        this.result = result;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Approval() {

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getChooseReason() {
        return chooseReason;
    }

    public void setChooseReason(String chooseReason) {
        this.chooseReason = chooseReason;
    }

    @Override
    public String toString() {
        if("able".equals(state)){
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "可选" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 课程名称='" + courseName + '\'';
        }
        else if("unapproved".equals(state)){
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "第一次审批中" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 选择原因='" + chooseReason + '\''+
                    ", 课程名称='" + courseName + '\'';
        } else if ("end".equals(state)&&"true".equals(secondResult)&&"true".equals(result)) {
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "已结束" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 选择原因='" + chooseReason + '\'' +
                    ", 初次审批结果='" + result + '\'' +
                    ", 课程名称='" + courseName + '\'' +
                    ", 二次审批结果='" + secondResult + '\''+
                    ", 最终结果='" + "true" + '\'' ;
        } else if(("false".equals(result)||"false".equals(secondResult))&&"end".equals(state)&&!"null".equals(secondResult)) {
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "已结束" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 课程名称='" + courseName + '\'' +
                    ", 选择原因='" + chooseReason + '\''+
                    ", 拒绝原因='" + rejectReason + '\'' +
                    ", 初次审批结果='" + result + '\'' +
                    ", 二次审批结果='" + secondResult + '\'' +
                    ", 最终结果='" + "false" + '\'' ;
        }
        else if("end".equals(state)&&"false".equals(result)){
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "第二次审批中" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 课程名称='" + courseName + '\''+
                    ", 选择原因='" + chooseReason + '\''+
                    ", 初次审批结果='" + result + '\'' +
                    ", 拒绝原因='" + rejectReason + '\'';
        }else if("end".equals(state)&&"true".equals(result)){
            return  "申请id='" + id + '\'' +
                    ", 申请状态='" + "第二次审批中" + '\'' +
                    ", 学生id='" + studentId + '\'' +
                    ", 教师名称='" + teacherId + '\'' +
                    ", 选择原因='" + chooseReason + '\''+
                    ", 初次审批结果='" + result + '\'' +
                    ", 课程名称='" + courseName + '\'';
        }
        return "toString写错了。。";
    }
}
