package com.example.com.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.example.com.Entity.Student;
import com.example.com.Service.StudentService;

import java.util.List;
//import org.springframework.ui.Model;

@Controller
public class StudentController {
	
	@Autowired
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	// handler method to handle list students and return mode and view
	@GetMapping("/students")
	public String listStudents(HttpServletRequest req, Model model) {
		HttpSession session= req.getSession();
		if(session.getAttribute("user")!=null)
		{model.addAttribute("students", studentService.getAllStudents());
		return "home";}
		else
			return "redirect:/login";
	}
	
	
	@GetMapping("/index")
	public String firstPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null){
			return "index";
		}
		//model.addAttribute("students", studentService.getAllStudents());
		else
			return "redirect:/login";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		if(session!=null){
			Student student = new Student();
			model.addAttribute("student", student);
			return "create_student";
		}
		// create student object to hold student form data
		else
			return "login";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(HttpServletRequest request,@PathVariable int id, Model model) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null){
			model.addAttribute("student", studentService.getStudentById(id));
			return "edit_student";
		}
		return 	"redirect:/login";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable int id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setName(student.getName());
		existingStudent.setPhone(student.getPhone());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setStatus(student.getStatus());
		existingStudent.setClassName(student.getClassName());
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	// handler method to handle delete student request
	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable int id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}


	@GetMapping("/students/filter/{className}")
	public String filterStudents(@PathVariable("className") String className, Model model) {
		List<Student> students;

		if (className != null && !className.isEmpty()) {
			// Fetch students by className if provided
			students = studentService.getStudentsByClass(className);
		} else {
			// Fetch all students if className is not provided
			students = studentService.getAllStudents();
		}

		model.addAttribute("students", students);
		return "home"; // The name of your Thymeleaf template
	}
}
