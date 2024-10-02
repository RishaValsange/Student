package com.example.demo2.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo2.Entity.Student;
import com.example.demo2.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/addStudent")
    public String showForm(Model model) {
        model.addAttribute("students", new Student());
        return "addStudent"; // Return the view name for adding a student
    }

    @PostMapping("/student")
    public String submit(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "addStudent"; // Redirect to the student list after saving
    }

    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students_list"; // Return the view name for listing students
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@RequestParam("rollNo") int rollNo, @ModelAttribute Student updatedStudent) {
        Student existingStudent = studentRepository.findById(rollNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Roll Number: " + rollNo));

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setBranch(updatedStudent.getBranch());
        existingStudent.setMarks(updatedStudent.getMarks());
        studentRepository.save(existingStudent);

        return "redirect:/list"; // Redirect to the student list after updating
    }

    @PostMapping("/updateUserInline")
    public ResponseEntity<?> updateUserInline(@RequestBody Map<String, String> updateData) {
        try {
            // Extract student roll number, field name, and new value from the request body
            int rollNo = Integer.parseInt(updateData.get("id"));
            String field = updateData.get("field");
            String newValue = updateData.get("value");

            // Retrieve the existing student
            Student existingStudent = studentRepository.findById(rollNo)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid student Roll Number: " + rollNo));

            // Update the specific field based on the field name
            switch (field) {
                case "name":
                    existingStudent.setName(newValue);
                    break;
                case "branch":
                    existingStudent.setBranch(newValue);
                    break;
                case "marks":
                    existingStudent.setMarks(Integer.parseInt(newValue)); // Ensure marks are parsed as an Integer
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field name: " + field);
            }

            // Save the updated student record to the database
            studentRepository.save(existingStudent);

            // Return a success response
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(500).body("Error occurred while updating student: " + e.getMessage());
        }
    }
}
