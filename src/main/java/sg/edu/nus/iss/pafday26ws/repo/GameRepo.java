package sg.edu.nus.iss.pafday26ws.repo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.pafday26ws.model.Game;

@Repository
public class GameRepo {
    @Autowired
    private MongoTemplate template;

    private final String G_NAME = "name";
    private final String G_GAME = "game";
    private final String G_GID = "gid";

    /* db.game
    .find({name : {$regex : "monopoly", $options : "i"}})
    .limit(25)
    .skip(0) */
    public List<Game> getGameByName(String name) {

        Criteria c = Criteria.where(G_NAME).regex(name, "i");
        Query query = Query.query(c).limit(25).skip(0);

        List<Document> list = template.find(query, Document.class, G_GAME);
        List<Game> gameList = new ArrayList<>();

        for(Document d : list) {
            Game game = new Game(
                d.getObjectId("_id"),
                d.getString("name"));
           
                gameList.add(game);    
        }

        return  gameList;
        
    }
/* db.game.find()
    .sort({ranking : 1})
    .limit(25)
    .skip(0) */
    public List<Game> getGamesByRank(String name) {
        Criteria c = Criteria.where(G_NAME).regex(name, "i");
        Query query = Query.query(c).with(Sort.by(Sort.Direction.ASC, "ranking")).limit(25).skip(0);  

        return template.find(query, Game.class, G_GAME);
      }

      /* db.game.find({
        $and : [
            {name : { $regex: "monopoly", $options : "i"}},
            {gid : 684}
        ]
}) */
    public Document getGameDetailsByGid(int gid) {
        Criteria c = Criteria.where(G_GID).is(gid);
        Query query = Query.query(c);

        return template.findOne(query, Document.class, G_GAME);
    }  

    public Integer getGameCount() { 
   
        List<Game> list = template.findAll(Game.class, G_GAME);

        return list.size();
    }    

    public List<Integer> getRankings(int gid) { 
        Criteria c = Criteria.where(G_GID).is(gid);
        Query query = Query.query(c);

        List<Document> list =  template.find(query, Document.class, G_GAME);
        List<Integer> rankingList = new ArrayList<>();

        for(Document g : list) {
            Integer i = g.getInteger("ranking");
            rankingList.add(i);
        }

        return rankingList;
    }

    public Boolean findGid(int gid) {

        Criteria c = Criteria.where(G_GID).is(gid);
        Query query = Query.query(c);

        Boolean result = template.exists(query, Boolean.class, G_GAME);
        
        return result; 
    }
}
