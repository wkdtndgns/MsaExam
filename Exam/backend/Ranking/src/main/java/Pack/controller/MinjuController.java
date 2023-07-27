package Pack.controller;

import Pack.entity.LeaderBoard;
import Pack.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("leaderBoard")
public class MinjuController {
    @Autowired
    private RankRepository rankRepository;

    @RequestMapping("get")
    List<LeaderBoard> getTable(){
        List<LeaderBoard> leaderBoardList = rankRepository.findAll();
        return leaderBoardList;
    }
}
