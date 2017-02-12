package org.cs362.dominion;

public class mainRandomness {

	   public  static void main(String args[]){
		   
				Randomness.reset(10);	   
				
				for(int i=0;i<=10;i++)
					System.out.print(" rand number is: "+ Randomness.nextRandomInt(10));
			
		      
			System.exit(0);  

	   }
	
	
}
