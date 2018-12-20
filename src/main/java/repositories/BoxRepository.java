package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Box;
import domain.Message;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

    //
    @Query("select b from Actor a inner join a.boxes b where b.name = 'Received messages' and a = ?1")
    public Box getRecievedBoxByActor(Actor actor);

    @Query("select b from Actor a inner join a.boxes b where b.name = 'Spam' and a = ?1")
    public Box getSpamBoxByActor(Actor actor);

    @Query("select b from Actor a inner join a.boxes b where b.name = 'Trash' and a = ?1")
    public Box getTrashBoxByActor(Actor actor);

    @Query("select b from Actor a inner join a.boxes b where b.fatherBox =?1")
    public List<Box> getSonsBox(Box box);

    @Query("select b from Box b inner join b.messages f where f LIKE ?1")
    public List<Box> getCurrentBoxByMessage(Message m);

}
