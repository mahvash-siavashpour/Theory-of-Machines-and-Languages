import java.util.ArrayList;

public class NodeMappingNFA {
    private String alphabet;
    private ArrayList<StateNFA> state;

    public NodeMappingNFA(String alphabet) {
        this.alphabet = alphabet;
        state = new ArrayList<>();
    }

    public String getAlphabet() {
        return alphabet;
    }

    public ArrayList<StateNFA> getStates() {
        return state;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public void addState(StateNFA state) {
        this.state.add(state);
    }
}
