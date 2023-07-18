package sg.edu.nus.iss.pafday26ws.repo.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.pafday26ws.model.Game;
import sg.edu.nus.iss.pafday26ws.repo.GameRepo;

@Service
public class GameService {

    @Autowired
    private GameRepo repo;

    public List<Game> getGameByName(String name) {
        return repo.getGameByName(name);
}

    public List<Game> getGameByRanking(String name) {
        return repo.getGamesByRank(name);
    }

    public Document findGameByGid(int gid) {
       
        return repo.getGameDetailsByGid(gid);
    }

    public Boolean checkIfGidExists(int gid) {
        return repo.findGid(gid);
    }


    public Integer getSumOfRanks(int gid) {
        List<Integer> list = repo.getRankings(gid);
        
        int sum = 0;
        for(int i : list) {
            sum += i;
        }

        System.out.println("--------------------->" + sum);
        return sum;
    }


    public Double getAverage(int gid) {
        Integer gameCount = repo.getGameCount();
        Double avg = Double.valueOf(getSumOfRanks(gid))/gameCount;
        System.out.println("------------------------>" + gameCount);
        System.out.println("--------------------->" + avg); 

        return avg;
    }

}

