package sg.edu.nus.iss.pafday26ws.controller;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.pafday26ws.repo.service.GameService;

@RequestMapping
@RestController
public class GameController {
    
    @Autowired
    private GameService service;

    @GetMapping("/games")
    public ResponseEntity<String> getGames(@RequestParam(defaultValue = "0") Integer limit, @RequestParam(defaultValue = "25") Integer offset, String name) {
        
        JsonObject obj = Json.createObjectBuilder()
            .add("games", "" + service.getGameByName(name))
            .add("offset", offset)
            .add("limit", limit)
            .add("total", service.getGameByName(name).size())
            .add("timestamp", "" + new Date())
            .build();

        return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);    
    }

    @GetMapping("/games/rank")
    public ResponseEntity<String> sortGamesByRank(@RequestParam(defaultValue = "0") Integer limit, @RequestParam(defaultValue = "25") Integer offset, String name) {

        JsonObject obj = Json.createObjectBuilder()
            .add("games", "" + service.getGameByRanking(name))
            .add("offset", offset)
            .add("limit", limit)
            .add("total", service.getGameByName(name).size())
            .add("timestamp", "" + new Date())
            .build();

        return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
    }

    @GetMapping("/games/{game_id}")
    public ResponseEntity<String> getGameByGid(@RequestParam int gid) {
        Document doc = service.findGameByGid(gid);

        Double avg = service.getAverage(doc.getInteger("gid"));
        String formatted = String.format("%.ç2f", avg);

        if(service.checkIfGidExists(gid)) {
            JsonObject obj = Json.createObjectBuilder()
            .add("game_id", gid)
            .add("name", doc.getString("name"))
            .add("year", doc.getInteger("year"))
            .add("ranking", doc.getInteger("ranking"))
            .add("average", formatted)
            .add("users_rated", doc.getInteger("users_rated"))
            .add("url", doc.getString("url"))
            .add("thumbnail", doc.getString("image"))
            .add("timestamp", "" + new Date())
            .build();
        
            return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
        } else {
            JsonObject error = Json.createObjectBuilder()
            .add("error", "Game id: " + gid + " does not exist")
            .build();

            return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
