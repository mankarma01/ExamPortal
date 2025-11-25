package com.logical.examportal.page;

import com.logical.examportal.entity.*;
import com.logical.examportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CollegeRepository collegeRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TermsConditionRepository termsConditionRepository;

    @Autowired
    ExamRulesRepository examRulesRepository;

    @Value("${project.title}")
    private String projectTitle;

    @GetMapping("/home")
    public String showIndexPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "index";
    }

    @GetMapping("/student")
    public String showStudentPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        List<City> cityList = cityRepository.findAll();
        model.addAttribute("cityList", cityList);
        return "student";
    }

    @GetMapping("/studentRegistration")
    public String showStudentRegistrationPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "studentRegistration";
    }

    @GetMapping("/exam")
    public String showExamPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "exam";
    }

    @GetMapping("/examCreate")
    public String showExamCreatePage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "examCreate";
    }

    @GetMapping("/examUpdate/{examId}")
    public String showExamUpdatePage(Model model, @PathVariable Long examId) {
        Optional<Exam> exam = examRepository.findById(examId);

        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("exam", exam.get());
        return "examUpdate";
    }

 /*    @GetMapping("/addExamQuestion/{examId}")
    public String showAddExamQuestionPage(Model model, @PathVariable("examId") Long examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("examId", examId);
        model.addAttribute("exam", exam.get());
        return "addExamQuestion";
    }*/

    @GetMapping("/viewExamQuestion/{examId}/{examSet}")
    public String showViewExamQuestionPage(Model model, @PathVariable("examId") Long examId, @PathVariable("examSet") Character examSet) {
        //List<Question> questionList = questionRepository.findByExamId(examId);
        List<Question> questionList = questionRepository.findByExamIdAndExamSet(examId, examSet);
        Optional<Exam> exam = examRepository.findById(examId);
        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("examId", examId);
        model.addAttribute("examSet", examSet);
        model.addAttribute("exam", exam.get());
        model.addAttribute("questionList", questionList);
        return "viewExamQuestion";
    }

    @GetMapping("/viewExamResult/{examId}")
    public String showViewExamResultPage(Model model, @PathVariable("examId") Long examId) {
        model.addAttribute("examId", examId);
        Optional<Exam> exam = examRepository.findById(examId);
        model.addAttribute("exam", exam.get());
        List<City> cityList = cityRepository.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("projectTitle", projectTitle);
        return "viewExamResult";
    }

    @GetMapping("/result")
    public String showResultPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "result";
    }
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "login";
    }

/*    @PostMapping("/viewScore")
    public String showViewScorePage(Model model, @RequestParam("studentId")Long studentId) {

        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("studentId", studentId);
        return "viewScore";
    }*/

    @GetMapping("/viewScore/{studentId}")
    public String showViewScorePage(Model model, @PathVariable("studentId")Long studentId) {

        Student student = studentRepository.findById(studentId).get();
        List<Result> resultList= resultRepository.findByStudentStudentId(studentId);

        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("studentId", studentId);
        model.addAttribute("student", student);
        model.addAttribute("resultList", resultList);
        return "viewScore";
    }

    @GetMapping("/activeExam")
    public String showActiveExamPage(Model model, @RequestParam("studentId")Long studentId, @RequestParam("examId")Long examId, @RequestParam("resultId")Long resultId) {
        Result result = resultRepository.findById(resultId).get();
        Student student = studentRepository.findById(studentId).get();
        List<Question> questionList = questionRepository.findByExamId(result.getExam().getExamId());
        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("result", result);
        model.addAttribute("student", student);
        model.addAttribute("questionList", questionList);
        return "activeExam";
    }

    @GetMapping("/disclaimer/{studentId}")
    public String showDisclaimerExamPage(Model model,@PathVariable("studentId")Long studentId) {
        Exam exam = examRepository.findLatestActive(true);
        Student student = studentRepository.findById(studentId).get();
        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("exam", exam);
        model.addAttribute("student", student);
        return "disclaimer";
    }

    @GetMapping("/college")
    public String showCollegePage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "college";
    }

    @GetMapping("/collegeCreate")
    public String showCollegeCreatePage(Model model) {
        List<City> cityList = cityRepository.findAll();
        model.addAttribute("projectTitle", projectTitle);
        model.addAttribute("cityList", cityList);
        return "collegeCreate";
    }


    @GetMapping("/collegeUpdate/{collegeId}")
    public String showCollegeUpdatePage(Model model, @PathVariable("collegeId") Long collegeId) {
        Optional<College> college = collegeRepository.findById(collegeId);

        if(college.isPresent()){
            model.addAttribute("projectTitle", projectTitle);
            model.addAttribute("college", college.get());
            List<City> cityList = cityRepository.findAll();
            model.addAttribute("cityList", cityList);
            return "collegeUpdate";
        }else{
            return "college";
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "profile";
    }


    @GetMapping("/termsCondition")
    public String showTermsConditionPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "termsCondition";
    }

    @GetMapping("/tcUpdate/{tcId}")
    public String showTermsConditionUpdatePage(Model model, @PathVariable("tcId") Long tcId) {
        Optional<TermsCondition> termsCondition = termsConditionRepository.findById(tcId);
        if(termsCondition.isPresent()){
            model.addAttribute("projectTitle", projectTitle);
            model.addAttribute("termsCondition", termsCondition.get());
            return "termsConditionUpdate";
        }else{
            return "termsCondition";
        }
    }

    @GetMapping("/examRules")
    public String showExamRulesPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "examRules";
    }

    @GetMapping("/examRulesUpdate/{examRulesId}")
    public String showExamRulesUpdatePage(Model model, @PathVariable("examRulesId") Long examRulesId) {
        Optional<ExamRules> examRule = examRulesRepository.findById(examRulesId);
        if(examRule.isPresent()){
            model.addAttribute("projectTitle", projectTitle);
            model.addAttribute("examRules", examRule.get());
            return "examRulesUpdate";
        }else{
            return "examRules";
        }
    }

    @GetMapping("/city")
    public String showCityPage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "city";
    }

    @GetMapping("/cityCreate")
    public String showCityCreatePage(Model model) {
        model.addAttribute("projectTitle", projectTitle);
        return "cityCreate";
    }


    @GetMapping("/cityUpdate/{cityId}")
    public String showCityUpdatePage(Model model, @PathVariable("cityId") Long cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        if(city.isPresent()){
            model.addAttribute("projectTitle", projectTitle);
            model.addAttribute("city", city.get());
            return "cityUpdate";
        }else{
            return "city";
        }
    }


}