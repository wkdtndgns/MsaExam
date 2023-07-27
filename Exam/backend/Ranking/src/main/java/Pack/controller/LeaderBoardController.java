package Pack.controller;


import Pack.entity.LeaderBoard;
import Pack.repository.RankRepository;
import Pack.vo.FormVo;
import Pack.vo.ValueVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.coyote.Response;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


//@RestController
//@RequestMapping("/rank")
//@RequiredArgsConstructor
//public class LeaderBoardController {
//    @Autowired
//    RestTemplate rt;
//    private final RankRepository rankRepository;
//
//    @GetMapping("/hello")
//    public String getAll() {
//        return rankRepository.findByName("mskim").toString();
//    }
//}

@Component
public class LeaderBoardController {
    @Value("${link.S1Server}")
    private String s1Link;

    @Autowired
    RestTemplate rt;
    @Autowired
    RankRepository rankRepository;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = "exchange01", type = ExchangeTypes.DIRECT),
            value = @Queue(name = "queue01"),
            key = "routingKey01"
    ))
    public void getUserCollect(Message msg) {
        System.out.println("[EVENT] Receive RabbitMQ Message");
        String contentType = msg.getMessageProperties().getContentType();

        if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
            // JSON인 경우 아무 작업 없이 리턴
            System.out.println("[WARN] Receive JSON Content Type => return");
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            FormVo deserializedFormVo = objectMapper.readValue(msg.getBody(), FormVo.class);
            String truncatedName = deserializedFormVo.getName().substring(0, Math.min(deserializedFormVo.getName().length(), 250));
            Integer examKey = deserializedFormVo.getExamKey();

            ValueVo valueVo = getExamInfo(examKey);
            List<Long> valueList = valueVo.getExam();
            Optional<LeaderBoard> existingRecord = rankRepository.findByName(deserializedFormVo.getName());

            if (existingRecord.isPresent()){
                LeaderBoard leaderBoard = existingRecord.get();
                Long updatedValue = (valueList.get(0) > 5 && valueList.get(1) > 5) ? 12L : 10L;
                leaderBoard.setScore(leaderBoard.getScore() + updatedValue);
                rankRepository.save(leaderBoard);
                System.out.println("DB UPDATE");
            }else {
                Long newValue = (valueList.get(0) > 5 && valueList.get(1) > 5) ? 12L : 10L;
                rankRepository.save(new LeaderBoard(truncatedName, newValue));
                System.out.println("DB INSERT");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    ValueVo getExamInfo(Integer examKey) throws IOException, JsonProcessingException, Exception{
        ResponseEntity<ValueVo> result = rt.exchange(
                s1Link + examKey,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ValueVo>() {
                }
        );
        System.out.println(String.format("[INFO] Get exam Info , %s", result.toString()));
        ValueVo valueVo = result.getBody();
        return valueVo;
    }
}