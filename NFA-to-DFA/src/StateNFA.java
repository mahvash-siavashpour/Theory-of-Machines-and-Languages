import java.util.ArrayList;

public class StateNFA {
    private String name;
    private ArrayList<NodeMappingNFA> edges;

    public String getName() {
        return name;
    }
    public ArrayList<NodeMappingNFA> getEdges() {
        return edges;
    }


    public StateNFA(String name) {
        this.name = name;
        edges = new ArrayList<>();
    }

    public boolean ifLambda(){
        for (int i = 0; i <edges.size() ; i++) {
            if(edges.get(i).getAlphabet().equals("λ")){
                return true;
            }
        }
        return false;
    }

    public void addMapping(String alphabet, StateNFA state) {
        boolean found = false;
        boolean exists = false;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getAlphabet().equals(alphabet)) {
                for (int j = 0; j <edges.get(i).getStates().size() ; j++) {
                    if(edges.get(i).getStates().get(j).getName().equals(state.getName())){
                        exists = true;
                    }
                }
                if(!exists){
                    edges.get(i).addState(state);
                    found = true;
                    break;
                }
            }
        }
        if (!found && !exists) {
            NodeMappingNFA node = new NodeMappingNFA(alphabet);
            node.addState(state);
            edges.add(node);
        }
    }

    public ArrayList getMappedStates(String alphabet){
        for (int i = 0; i <edges.size() ; i++) {
            if(edges.get(i).getAlphabet().equals(alphabet)){
                return edges.get(i).getStates();
            }
        }
        return null;
    }

    public boolean ifAlphabetFound(String alphabet){
        boolean found = false;
        for (int i = 0; i <edges.size() ; i++) {
            if(edges.get(i).getAlphabet().equals(alphabet)){
                found=true;
            }
        }
        return found;
    }
}
