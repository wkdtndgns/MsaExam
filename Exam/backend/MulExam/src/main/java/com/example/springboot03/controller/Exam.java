package com.example.springboot03.controller;

import com.example.springboot03.service.ExamService;
import com.example.springboot03.vo.ExamVo;
import com.example.springboot03.vo.FormVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class Exam {

    @Autowired
    RabbitTemplate rt;

    @Autowired
    ExamService examService;

    @GetMapping("/exam")
    public ExamVo getExam() {
        Integer i = (int) (Math.random() * 81);
        return new ExamVo(ExamService.multiplicationTable.get(i), i);
    }

    @GetMapping("/exam/{id}")
    public ExamVo getExam(@PathVariable Integer id) {
        return new ExamVo(ExamService.multiplicationTable.get(id), id);
    }

    @PostMapping("/exam")
    public boolean postExam(@RequestBody FormVo formVo) throws JsonProcessingException {
        boolean result = ExamService.checkResult(formVo.getExamKey(), formVo.getResult());
        ObjectMapper objectMapper = new ObjectMapper();
        if (result) rt.convertAndSend("exchange01", "routingKey01",objectMapper.writeValueAsString(formVo));

        return result;
    }
}
