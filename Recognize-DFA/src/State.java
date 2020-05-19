import java.util.HashMap;

/**
 * This class is the data structure used for storing DFA states' data
 *
 * @author mahvash
 * @version 1.0
 */
public class State {
    private String name;
    private HashMap<String, State> edges;

    public State(String name){
        this.name = name;
        edges = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    public HashMap getEdges(){
        return edges;
    }
    public State getMappedState(String alphabet){
        return edges.get(alphabet);
    }
    public void addMapping(String alphabet, State state){
        edges.put(alphabet, state);
    }
}
