import java.io.*;
import java.util.ArrayList;

/**
 * This is the class that mines the data of a DFA from a file and stores it in an
 * appropriate data structure
 *
 * @author mahvash
 * @version 1.0
 */
public class DFA {
    private static ArrayList alphabet = new ArrayList();
    private static ArrayList<State> states = new ArrayList<>();
    private static ArrayList<State> endPoints = new ArrayList<>();

    public static ArrayList getAlphabet() {
        return alphabet;
    }

    public static ArrayList<State> getStates() {
        return states;
    }

    public static ArrayList<State> getEndPoints() {
        return endPoints;
    }

    public static State getStartingPoint() {
        return startingPoint;
    }

    private static State startingPoint = null;

    /**
     * This method reads a file and stores its data in a stateDFA data structure
     * @param fileName is the name of the file in which the data is stored
     */
    public static void makeDFA(String fileName){
        File file = new File(fileName);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st = null;
            int count = 0;
            while (true) {
                try {
                    if ((st = br.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //reading alphabet
                if (count == 0) {
                    for (int i = 0; i < st.length(); i++) {
                        alphabet.add(st.charAt(i)+"");
                        i++;
                    }
                }
                //reading and making states
                else if (count == 1) {
                    for (int i = 0; i < st.length(); i++) {
                        String stateName = "";
                        while (i<st.length() && st.charAt(i) != ' ') {
                            stateName += st.charAt(i);
                            i++;
                        }
                        State thisState = new State(stateName);
                        states.add(thisState);
                    }
                }
                //reading starting points
                else if (count == 2) {
                    for (int i = 0; i <states.size() ; i++) {
                        if(st.equals(states.get(i).getName())){
                            startingPoint = states.get(i);
                            break;
                        }
                    }
                }
                //reading end points
                else if (count == 3) {
                    ArrayList names = new ArrayList();
                    for (int i = 0; i < st.length(); i++) {
                        String stateName = "";
                        while (i<st.length() && st.charAt(i) != ' ') {
                            stateName += st.charAt(i);
                            i++;
                        }
                        names.add(stateName);
                    }

                    for (int i = 0; i <names.size() ; i++) {
                        for (int j = 0; j <states.size() ; j++) {
                            if(states.get(j).getName().equals(names.get(i))){
                                endPoints.add(states.get(j));
                            }
                        }
                    }
                }
                //adding edges to our nodes
                else {

                    int word =0;
                    int start = 0, end = 0;
                    String mapAlphabet = null;
                    for (int i = 0; i < st.length(); i++) {
                        String mapping = "";
                        while (i<st.length() && st.charAt(i) != ' ') {
                            mapping += st.charAt(i);
                            i++;
                        }
                        switch (word){
                            case 0:
                                for (int j = 0; j <states.size() ; j++) {
                                    if(states.get(j).getName().equals(mapping)){
                                        start = j;
                                    }
                                }
                                break;
                            case 1:
                                mapAlphabet = mapping;
                                break;
                            case 2:
                                for (int j = 0; j <states.size() ; j++) {
                                    if(states.get(j).getName().equals(mapping)){
                                        end = j;
                                    }
                                }
                                states.get(start).addMapping(mapAlphabet, states.get(end));
                                break;
                        }
                        word++;
                    }
                }
                count++;
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
