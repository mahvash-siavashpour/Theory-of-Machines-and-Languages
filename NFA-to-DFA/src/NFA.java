import java.io.*;
import java.util.ArrayList;

/**
 * This class stores all data relevant to NFA including the alphabet, states, starting point
 * and end points.
 *
 * @author mahvash
 * @version 1.0
 */
public class NFA {
    private static ArrayList alphabet = new ArrayList();
    private static ArrayList<StateNFA> states = new ArrayList<>();
    private static ArrayList<StateNFA> endPoints = new ArrayList<>();
    private static StateNFA startingPoint;

    public static ArrayList getAlphabet() {
        return alphabet;
    }

    public static ArrayList<StateNFA> getStates() {
        return states;
    }

    public static ArrayList<StateNFA> getEndPoints() {
        return endPoints;
    }

    public static StateNFA getStartingPoint() {
        return startingPoint;
    }

    /**
     * This method reads the file in which the machine data is written and creates
     * the NFA using that data
     *
     * @param fileName is the name of the text file that contains the NFA data
     */

    public static void makeNFA(String fileName) {
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
                if (count == 0) {
                    for (int i = 0; i < st.length(); i++) {
                        alphabet.add(st.charAt(i) + "");
                        i++;
                    }
                } else if (count == 1) {
                    for (int i = 0; i < st.length(); i++) {
                        String stateName = "";
                        while (i < st.length() && st.charAt(i) != ' ') {
                            stateName += st.charAt(i);
                            i++;
                        }
                        StateNFA thisState = new StateNFA(stateName);
                        states.add(thisState);
                    }
                } else if (count == 2) {
                    for (int i = 0; i < states.size(); i++) {
                        if (st.equals(states.get(i).getName())) {
                            startingPoint = states.get(i);
                            break;
                        }
                    }
                } else if (count == 3) {
                    ArrayList names = new ArrayList();
                    for (int i = 0; i < st.length(); i++) {
                        String stateName = "";
                        while (i < st.length() && st.charAt(i) != ' ') {
                            stateName += st.charAt(i);
                            i++;
                        }
                        names.add(stateName);
                    }

                    for (int i = 0; i < names.size(); i++) {
                        for (int j = 0; j < states.size(); j++) {
                            if (states.get(j).getName().equals(names.get(i))) {
                                endPoints.add(states.get(j));
                            }
                        }
                    }
                } else {

                    int word = 0;
                    int start = 0, end = 0;
                    String mapAlphabet = null;
                    for (int i = 0; i < st.length(); i++) {
                        String mapping = "";
                        while (i < st.length() && st.charAt(i) != ' ') {
                            mapping += st.charAt(i);
                            i++;
                        }
                        switch (word) {
                            case 0:
                                for (int j = 0; j < states.size(); j++) {
                                    if (states.get(j).getName().equals(mapping)) {
                                        start = j;
                                    }
                                }
                                break;
                            case 1:
                                mapAlphabet = mapping;
                                break;
                            case 2:
                                for (int j = 0; j < states.size(); j++) {
                                    if (states.get(j).getName().equals(mapping)) {
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

    /**
     * This method is used to convert the NFA to a machine that does not contain lambda expression
     */

    public static void removeLambdaNFA() {

        if (startingPoint.ifLambda()) {
            endPoints.add(startingPoint);
        }
        for (int i = 0; i < states.size(); i++) {
            ArrayList<StateNFA> middleFromLambda = new ArrayList<>();
            if (states.get(i).getMappedStates("λ") != null) {
                for (int j = 0; j < states.get(i).getMappedStates("λ").size(); j++) {
                    middleFromLambda.add((StateNFA) states.get(i).getMappedStates("λ").get(j));
                }
            }

            if (middleFromLambda != null) {

                for (int m = 0; m < middleFromLambda.size(); m++) {
                    for (int j = 0; j < alphabet.size(); j++) {
                        if (middleFromLambda.get(m).getMappedStates((String) alphabet.get(j)) != null) {

                            for (int k = 0; k < middleFromLambda.get(m).getMappedStates((String) alphabet.get(j)).size(); k++) {
                                states.get(i).addMapping((String) alphabet.get(j),
                                        (StateNFA) middleFromLambda.get(m).getMappedStates((String) alphabet.get(j)).get(k));
                                if ( middleFromLambda.get(m).ifLambda()) {
                                    for (int s = 0; s < middleFromLambda.size(); s++) {
                                        boolean found=false;

                                        if (middleFromLambda.get(s).getName().equals(((StateNFA) middleFromLambda.get(m)
                                                .getMappedStates((String) alphabet.get(j)).get(k)).getName())) {
                                                found=true;
                                        }
                                        if(!found) {
                                            middleFromLambda.add((StateNFA) middleFromLambda.get(m).getMappedStates((String) alphabet.get(j)).get(k));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < alphabet.size(); j++) {
                ArrayList<StateNFA> middleToLambda = new ArrayList<>();
                if (states.get(i).getMappedStates((String) alphabet.get(j)) != null) {
                    for (int k = 0; k < states.get(i).getMappedStates((String) alphabet.get(j)).size(); k++) {
                        middleToLambda.add((StateNFA) states.get(i).getMappedStates((String) alphabet.get(j)).get(k));
                    }
                }
                if (middleToLambda != null) {
                    while (middleToLambda.size() > 0) {
                        ArrayList<StateNFA> lambdaMapped = new ArrayList<>();
                        if (middleToLambda.get(0).getMappedStates("λ") != null) {
                            for (int k = 0; k < middleToLambda.get(0).getMappedStates("λ").size(); k++) {
                                lambdaMapped.add((StateNFA) middleToLambda.get(0).getMappedStates("λ").get(k));
                            }
                        }
                        middleToLambda.remove(0);
                        if (lambdaMapped != null) {
                            for (int k = 0; k < lambdaMapped.size(); k++) {
                                states.get(i).addMapping((String) alphabet.get(j), lambdaMapped.get(k));

                                if (lambdaMapped.get(k).ifLambda()) {
                                    middleToLambda.add(lambdaMapped.get(k));
                                }
                            }
                        }
                    }
                }

            }
        }
        printStates();


    }

    /**
     * Printing the states and their connections is possible using this method
     */
    public static void printStates() {
        for (int i = 0; i < states.size(); i++) {
            for (int j = 0; j < alphabet.size(); j++) {
                if (states.get(i).getMappedStates((String) alphabet.get(j)) != null) {
                    for (int k = 0; k < states.get(i).getMappedStates((String) alphabet.get(j)).size(); k++) {
                        StateNFA temp = (StateNFA) states.get(i).getMappedStates((String) alphabet.get(j)).get(k);
                        System.out.println(states.get(i).getName() + ", " + alphabet.get(j) + " -> " + temp.getName());
                    }
                }

            }
        }
    }
}
