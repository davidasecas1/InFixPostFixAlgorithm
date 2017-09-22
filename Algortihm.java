/**
 * @author David Espejo AntiÃ±olo
 */
package main;

import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Main {

	public static void main(String[] args){
		Scanner s=new Scanner(System.in); //I declare the Scanner to take the input from the user
		Fix f=new Fix(s.nextLine()); //I create a Fix object in which is the algorithm
		s.close();
		double TInicio, TFin, tiempo; //I declare time variables
		TInicio = System.nanoTime(); //I store the current CPU time in the init time variable in nanoseconds
		System.out.println(f.toResult(f.toPostFix()));//I execute the algorithm, giving as argument the infix to postfix result
		TFin = System.nanoTime();//I store the current CPU time in the final time variable
		tiempo = TFin - TInicio; //I calculate the difference between the final time and the init one, so i get the total execution time of the algorithm
		System.out.println("Tiempo de ejecuciÃ³n en milisegundos: " + tiempo/1000000);//I print the time value in milliseconds with decimals
	}

}
class Fix{
	String input;
	/**
	 * Class constructor of the algorithm object specifying the users input
	 * @param input users input
	 */
	public Fix(String input){
		this.input=input;
	}
	/**
	 * Returns the Reverse Polish notation so then i can deal with it in the toResult() function.
	 * This conversion is made with a Stack called operators which stores the operators in order of priority
	 * and it pops when the priority is lower.
	 * Doesn't have any argument.
	 * @return	a Vector String in which is the operation in Reverse Polish notation, the numbers and the signs are separated
	 * 			in the elements of the Vector.
	 */
	public Vector<String> toPostFix(){
		Vector<String> postFix=new Vector<String>();
		Stack<Character> operators=new Stack<Character>();
		for(int i=0;i<input.length();i++){
			char elm=input.charAt(i);
			if(checkOp(elm) && elm!=0){
				if(!operators.empty()){
					int p=priority((char)operators.peek(),elm);
					if(p==2){
						operators.push(elm);
					}else if(p==1){
						while(!operators.empty()){
							postFix.add(""+operators.pop());
						}
						operators.push(elm);
					}else if(p==0){
						postFix.add(""+operators.pop());
						operators.push(elm);
					}
					if(elm=='(')operators.push(elm);
					if((char)operators.peek()=='('){
						operators.push(elm);
					}
					if(elm==')'){
						while(operators.peek()!='('){
							postFix.add(""+operators.pop());
						}
					}
				}else{
					operators.push(elm);
				}
			}else{
				postFix.add(""+elm);
				if(!checkOp(postFix.lastElement()) && i<input.length()-1 && checkOp(input.charAt(i+1))){
					postFix.add("");
				}
				
				
			}
		}
		 while (!operators.empty()){
			postFix.add(""+operators.pop());
		 }
		 while(postFix.contains("(")){
			 postFix.remove("(");
		 }
		 while(postFix.contains(")")){
			 postFix.remove(")");
		 }
		 int i=-1;
		 while(i<postFix.size()){
			 i++;
			 if(i<postFix.size()-1&& !checkOp(postFix.elementAt(i)) && !checkOp(postFix.elementAt(i+1)) && postFix.elementAt(i+1)!=""){
				 postFix.set(i, postFix.get(i)+postFix.get(i+1));
				 postFix.remove(i+1);
				 i--;
			 }
		 }
		 while(postFix.contains("")){
			 postFix.remove("");
		 }
		return postFix;
	}
	/**
	 * Returns the final result of the operation. It traverses the postFix vector and each time it finds a number stores it in the nums Stack. When it finds
	 * an operation it pop the last to numbers makes the operation and pushes the result back to the Stack and this is made over and over until the postFix
	 * Vector is completed and all operation are made. Finally it returns the final result by poping the on eand only number in the Stack.
	 * @param postFix a Vector String in which is the operation in Reverse Polish notation, in this case you can get this in the toPostFix() function.
	 * @see #toPostFix()
	 * @return the final result of the operation.
	 */
	public float toResult(Vector<String> postFix){
		float res=0;
		Stack<Float> nums=new Stack<Float>();
		String elm="";
		for(int i=0;i<postFix.size();i++){
			elm=postFix.elementAt(i);
			System.out.print(elm);
			if(checkOp(elm)){
				float num1=nums.pop();
				float num2;
				if(!nums.empty()) { //Negative numbers
					num2=nums.pop();
				}else {
					num2=0;
				}
				float resOp=0;
				switch(elm){
					case "+":
						resOp=num2+num1;
						break;
					case "-":
						resOp=num2-num1;
						break;
					case "*":
						resOp=num2*num1;
						break;
					case "/":
						resOp=num2/num1;
						break;
					case "^":
						resOp=(float) Math.pow(num2, num1);
						break;
				}
				nums.push(resOp);
			}else{
				nums.push(toFloat(elm));
			}
		}
		System.out.println();
		res=nums.pop();
		return res;
	}
	/**
	 * Returns true if elm is an operator or a bracket
	 * @param elm The element you want to check, in this case whit a character
	 * @return true if elm is an operator or a bracket and false if it isn't
	 */
	public boolean checkOp(char elm){
		return (elm=='+' || elm=='-' || elm=='*' || elm=='/' || elm=='(' || elm==')' || elm=='^');
	}
	/**
	 * Returns true if elm is an operator or a bracket
	 * @param elm The element you want to check, in this case whit a String
	 * @return true if elm is an operator or a bracket and false if it isn't
	 */
	public boolean checkOp(String elm){
		return (elm.equals("+") || elm.equals("-") || elm.equals("*") || elm.equals("/") || elm.equals("(") || elm.equals(")") || elm.equals("^"));
	}
	/**
	 * Returns the level of the priority between operators, it compares op1 and op2 characters.
	 * @param op1 First operation you want to compare.
	 * @param op2 Second operation you want to compare.
	 * @return the level of priority. The higher number, the higher the priority CAMBIAR
	 */
	public int priority(char op1,char op2){
		int res=-1;
		if((op1=='+' || op1=='-' || op1=='*' || op1=='/') && op2=='^'){
			res=2;
		}
		if(op1=='^' &&(op2=='+' || op2=='-' || op2=='*' || op2=='/') ){
			res=1;
		}
		if(op1=='^' && op2=='^'){
			res=0;
		}
		if((op1=='+' || op1=='-')&&(op2=='*' || op2=='/')){
			res=2;
		}
		if((op1=='*' || op1=='/')&&(op2=='+' || op2=='-')){
			res=1;
		}
		if((op1=='*' || op1=='/')&&(op2=='*' || op2=='/')){
			res=0;
		}
		if((op1=='+' || op1=='-')&&(op2=='+' || op2=='-')){
			res=0;
		}
		return res;
	}
	/**
	 * Conversion from String to float.
	 * @param str the String you want to be float
	 * @return the float parse of the String
	 */
	public float toFloat(String str){
		float res=Float.parseFloat(str);
		return res;
	}	
	/**
	 * Conversion from float to String.
	 * @param str the float you want to be String
	 * @return the String parse of the float
	 */
	public String toString(float num){
		String res=Float.toString(num);
		return res;
	}
}
