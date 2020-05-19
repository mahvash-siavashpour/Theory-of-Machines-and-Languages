import java.util.ArrayList;
import java.util.HashMap;

public class StateDFA {
    private ArrayList<String> names=new ArrayList<>();
    private HashMap<String, StateDFA> edges = new HashMap();

    public ArrayList<String> getNames() {
        return names;
    }

    public void addStateName(String name){
        names.add(name);
    }
    public void addMapping(String alphabet, StateDFA state) {
        edges.put(alphabet, state);
    }

    public StateDFA getMappedState(String alphabet){
        return  edges.get(alphabet);
    }

}
