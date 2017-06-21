package main;

import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		Fix f=new Fix(s.nextLine());
		System.out.println(f.toResult(f.toPostFix()));
	}

}
class Fix{
	String input;
	public Fix(String input){
		this.input=input;
	}
	public Vector<String> toPostFix(){
		Vector<String> postFix=new Vector<String>();
		Stack<Character> operators=new Stack<Character>();
		for(int i=0;i<input.length();i++){
			char elm=input.charAt(i);
			if(checkOp(elm)){
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
						operators.push(elm);
					}
				}else{
					operators.push(elm);
				}
			}else{
				postFix.add(""+elm);
			}
		}
		 while (!operators.empty()){
			postFix.add(""+operators.pop());
		 }
		return postFix;
	}
	public boolean checkOp(char elm){
		return (elm=='+' || elm=='-' || elm=='*' || elm=='/');
	}
	public boolean checkOp(String elm){
		return (elm.equals("+") || elm.equals("-") || elm.equals("*") || elm.equals("/"));
	}
	public int priority(char op1,char op2){
		int res=0;
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
	
	public float toResult(Vector<String> postFix){
		float res=0;
		Stack<Float> nums=new Stack<Float>();
		String elm="";
		for(int i=0;i<postFix.size();i++){
			elm=postFix.elementAt(i);
			System.out.print(elm);
			if(checkOp(elm)){
				float num1=nums.pop();
				float num2=nums.pop();
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
	
	
	public float toFloat(String str){
		float res=Float.parseFloat(str);
		return res;
	}	
}
