package sg.edu.nus.iss.pafday26ws.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Game {
    private ObjectId id;
    private String name; 
}
