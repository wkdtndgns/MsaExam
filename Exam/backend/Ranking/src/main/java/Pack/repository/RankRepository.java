package Pack.repository;

import Pack.entity.LeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<LeaderBoard, Long> {
    List<LeaderBoard> findAllByName(String name);
    Optional<LeaderBoard> findByName(String name);
//    @Query("select name from leader_board where name=:name)
//    String name findByName2(String name)
}
