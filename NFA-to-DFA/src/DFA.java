import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class converts and stores all the relevant data of a DFA machine.
 *
 * @author mahvash
 * @version 1.0
 */
public class DFA {
    private static ArrayList alphabet = new ArrayList();
    private static ArrayList<StateDFA> states = new ArrayList<>();
    private static ArrayList<StateDFA> endPoints = new ArrayList<>();
    private static StateDFA startingPoint = null;

    /**
     * This is a method for converting a NFA to a DFA when the lambdas are all solved in advanced
     * @param fileName in the name of the output file
     */

    public static void convertToDFA(String fileName) {
        StateNFA nfaStart = NFA.getStartingPoint();
        StateDFA dfaStart = new StateDFA();
        dfaStart.addStateName(nfaStart.getName());
        alphabet = NFA.getAlphabet();
        StateDFA currentDFA = dfaStart;
        StateDFA nextState = new StateDFA();
        ArrayList<StateNFA> currentNFA = new ArrayList<>();
        startingPoint = dfaStart;
        currentNFA.add(nfaStart);
        ArrayList<StateNFA> nextNFA = new ArrayList<>();
        ArrayList<StateDFA> DFAStatesToSolve = new ArrayList<>();
        ArrayList<ArrayList<StateNFA>> NFAStatesToSolve = new ArrayList<>();
        DFAStatesToSolve.add(currentDFA);
        NFAStatesToSolve.add(currentNFA);
        states.add(currentDFA);

        //DFAStatesToSolve works as a queue to store unresolved states
        while (DFAStatesToSolve.size() > 0) {
            currentDFA = DFAStatesToSolve.get(0);
            currentNFA = NFAStatesToSolve.get(0);
            DFAStatesToSolve.remove(0);
            NFAStatesToSolve.remove(0);
            for (int i = 0; i < alphabet.size(); i++) {
                for (int j = 0; j < currentNFA.size(); j++) {
                    ArrayList<StateNFA> temp = currentNFA.get(j).getMappedStates(alphabet.get(i) + "");
                    if (temp != null) {
                        for (int k = 0; k < temp.size(); k++) {
                            boolean found = false;
                            for (int m = 0; m < nextState.getNames().size(); m++) {
                                if (nextState.getNames().get(m).equals(temp.get(k).getName())) {
                                    found = true;
                                }
                            }
                            if (!found) {
                                nextState.addStateName(temp.get(k).getName());
                                nextNFA.add(temp.get(k));
                            }
                        }
                    }
                }
                if (nextState.getNames().size() == 0) {
                    nextState.addStateName("NULL");
                }

                currentDFA.addMapping((String) alphabet.get(i), nextState);
                boolean found = false;
                int countFound = 0;
                for (int j = 0; j < states.size(); j++) {
                    countFound = 0;
                    for (int k = 0; k < states.get(j).getNames().size(); k++) {
                        if (nextState.getNames().size() == states.get(j).getNames().size()) {
                            for (int m = 0; m < nextState.getNames().size(); m++) {
                                if (nextState.getNames().get(m).equals(states.get(j).getNames().get(k))) {
                                    countFound++;
                                }
                            }
                        }
                    }
                    if (countFound == nextState.getNames().size()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    DFAStatesToSolve.add(nextState);
                    NFAStatesToSolve.add(nextNFA);
                    states.add(nextState);
                }
                nextState = new StateDFA();
                nextNFA = new ArrayList<>();
            }
        }

        //set new ends points for the DFA
        for (int i = 0; i < NFA.getEndPoints().size(); i++) {
            for (int j = 0; j < states.size(); j++) {
                for (int k = 0; k < states.get(j).getNames().size(); k++) {
                    if (states.get(j).getNames().get(k).equals(NFA.getEndPoints().get(i).getName())) {
                        boolean found = false;
                        for (int m = 0; m < endPoints.size(); m++) {
                            if (endPoints.get(m).getNames().equals(states.get(j).getNames())) {
                                found = true;
                            }
                        }
                        if (!found) {
                            endPoints.add(states.get(j));
                            break;
                        }
                    }
                }
            }
        }

        writeToFile(fileName);


    }

    /**
     * The data and all the states' information of the DFA machine can be written into a file using this method
     * @param fileName is a string that specifies the name if the file we wanna write into
     */
    public static void writeToFile(String fileName){
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (int i = 0; i < alphabet.size(); i++) {
                fileWriter.write(alphabet.get(i) + " ");
            }
            fileWriter.write('\n');
            for (int i = 0; i < states.size(); i++) {
                for (int j = 0; j < states.get(i).getNames().size(); j++) {
                    fileWriter.write(states.get(i).getNames().get(j));
                }
                fileWriter.write(" ");
            }
            fileWriter.write('\n');
            for (int i = 0; i < startingPoint.getNames().size(); i++) {
                fileWriter.write(startingPoint.getNames().get(i));
            }
            fileWriter.write('\n');
            for (int i = 0; i < endPoints.size(); i++) {
                for (int j = 0; j < endPoints.get(i).getNames().size(); j++) {
                    fileWriter.write(endPoints.get(i).getNames().get(j));
                }
                fileWriter.write(" ");
            }
            fileWriter.write('\n');
            for (int i = 0; i < states.size(); i++) {
                for (int j = 0; j < alphabet.size(); j++) {
                    for (int k = 0; k < states.get(i).getNames().size(); k++) {
                        fileWriter.write(states.get(i).getNames().get(k));
                    }
                    fileWriter.write(" " + alphabet.get(j) + " ");
                    StateDFA mapped = states.get(i).getMappedState((String) alphabet.get(j));
                    for (int k = 0; k < mapped.getNames().size(); k++) {
                        fileWriter.write(mapped.getNames().get(k));
                    }
                    fileWriter.write('\n');
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
