public class main {
    /**
     * This is the main class of the project that calls the methods
     * @param args
     */
    public static void main(String[] args) {
        //you can enter the input file name as the parameter of this function{sample_NFA.txt, NFA_Input_2.txt, ...}
        NFA.makeNFA("NFA_Input_2.txt");
        NFA.removeLambdaNFA();
        //you can enter the output file name as the parameter of this function
        DFA.convertToDFA("DFA_Output_2.txt");
    }
}
