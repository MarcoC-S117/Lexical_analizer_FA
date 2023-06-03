package Lexical;


import java.util.ArrayList;
import java.util.List;


public class FA_11 {
    private int state;
    private List<Transition> transitions;
    //initial state
    public FA_11() {
        this.state = 0;
        this.transitions = new ArrayList<>();
    }
    //dfa
    public boolean process(String input) {
        this.state = 0;
        boolean endstate = true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            transitions.add(new Transition(state, c));
            switch (state) {
                case 0:
                    if (c == '0') {
                        state = 3;
                        
                    } else if (c == '1') {
                        state = 1;
                        
                    } else {
                        endstate = false;
                    }
                    break;
                case 1:
                    if (c == '1') {
                        state = 2;
                        
                    } else if (c == '0') {
                        state = 3;
                        
                    } else {
                        endstate = false;
                    }
                    break;
                case 2:
                    if (c == '0') {                   
                        state = 3;
                        
                    } else if (c == '1') {
                        state = 3;
                        
                    } else {
                        endstate = true;
                    }
                    break;
                case 3:
                    if (c == '0') {
                        state = 3;
                        
                    } else if (c == '1') {
                        state = 3;
                        
                    } else {
                        endstate = false;
                    }
                    break;
            }
            if (!endstate)
                break;
        }
        return endstate && (state == 2);
    }
	
    public int estado() {
        return state;
    }
    //getter of transition convert to string
    public String getTransitions() {
    	StringBuilder builder = new StringBuilder();  
    	  
        // iterate charList and append each char to builder one by one  
        for (Transition ch : transitions) {  
            builder.append(ch);  
        }  
          // convert StringBuilder object into string  
        String str = builder.toString();  
        return str;
    }
    
    //class use to save the transition states
    public class Transition {
        private int state;
        private char input;

        public Transition(int state, char input) {
            this.state = state;
            this.input = input;
        }

        public int getState() {
            return state;
        }

        public char getInput() {
            return input;
        }

        public String toString() {
            return "{" + "q" + state + ", " + input + "}";
        }
    }
}