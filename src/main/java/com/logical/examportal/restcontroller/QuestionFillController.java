package com.logical.examportal.restcontroller;


import com.logical.examportal.entity.QuestionFill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/questionFillAPI")
public interface QuestionFillController {

    @PostMapping("/checked")
    ResponseEntity<?> checkedQuestion(@RequestBody QuestionFill questionFill);

/*    @GetMapping("/getAll")
    ResponseEntity<?> getAllQuestionFIll();*/

    @GetMapping("/getAllByResultId/{resultId}")
    ResponseEntity<?> getAllQuestionFIllByResultId(@PathVariable("resultId") Long resultId);

}
