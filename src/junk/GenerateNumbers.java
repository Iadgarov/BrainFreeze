package junk;

import java.util.Random;

public class GenerateNumbers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a [][] = new int [9][9];
		int b [][] = {	{ 3, 1, 0, 0, 0, 0, 0, 0, 0 },
						{ 1, 2, 1, 0, 0, 0, 0, 0, 0 },
						{ 0, 1, 2, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 1, 3, 0, 0, 0, 0, 0 }, 
						{ 0, 0, 0, 1, 4, 3, 1, 0, 0 }, 
	      				{ 0, 0, 0, 0, 0, 1, 2, 0, 0 }, 
	      				{ 0, 0, 0, 0, 0, 0, 1, 3, 1 }, 
	      				{ 0, 0, 0, 0, 0, 0, 0, 1, 2 }, 
	       				{ 0, 0, 0, 0, 0, 0, 0, 0, 1 } 	}; 
		
		int c [][] = {{},{}};
		
		Random r = new Random();
		
		for (int i = 0; i < 81; i++){
			if (r.nextInt(1	 - 1 + 1) == 1){
				a[i/9][i%9] ++;
			}
			
			
		}
		
		a[4][4]=4;
		for (int i = 0; i < 81; i++){
			System.out.print(b[i/9][i%9]);
			if (i != 80)
				System.out.print(";");
			
		}
		
		
	}

}
