package sample.model;

public class Student {

    private final String studentId;
    private final String name;
    private final String email;
    private final String department;
    private final String academicYear;
    private final double gpa;

    public Student(String studentId, String name, String email, String department, String academicYear, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.academicYear = academicYear;
        this.gpa = gpa;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public double getGpa() {
        return gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}
