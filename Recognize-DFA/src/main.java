import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the main class and contains the main function
 * @author mahvash
 * @version 1.0
 */
public class main {

    /**
     * This in the main function in which the main program flow starts and runs
     * @param args
     */
    public static void main(String[] args) {

        DFA.makeDFA("DFA_Input_1.txt");
        State startingPoint= DFA.getStartingPoint();
        ArrayList<State> endPoints = DFA.getEndPoints();
        ArrayList<String> alphabet = DFA.getAlphabet();
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        ArrayList inputString = new ArrayList();
        for (int i = 0; i < in.length(); i++) {
            inputString.add(in.charAt(i)+"");
        }
        State currentState = startingPoint;
        boolean flag = false;

        //A loop that gets every alphabet in the input string and goes to the
        //relevant state
        for (int i = 0; i <inputString.size() ; i++) {
            boolean found = false;
            for (int j = 0; j <alphabet.size() ; j++) {
                if(inputString.get(i).toString().equals(alphabet.get(j))){
                    found=true;
                }
            }
            if(!found){
                flag=true;
            }
            if(!flag){
                currentState = currentState.getMappedState((String) inputString.get(i));
            }
        }
        boolean found=false;
        for (int i = 0; i <endPoints.size() ; i++) {
            if (currentState.equals(endPoints.get(i))){
                found = true;
            }
        }
        if(!found){
            flag=true;
        }
        if (!flag){
            System.out.println("yes! Got a DFA :)");
        }else{
            System.out.println("No! :(");
        }
    }
}
