package combination_calculator;

import java.util.Arrays;

public class combination_calculator {

	public static void calculate(int[] list, int num, String s){
		if(num==1) {
			for(int i=0;i<list.length;i++) {
				System.out.print(s);
				System.out.print(list[i]);
				System.out.println();
			}
		}
		else {
			for(int i= 0;i<=list.length-num;i++) {
				
				String temp_s=s+list[i];
				calculate(Arrays.copyOfRange(list, i+1, list.length), num-1,temp_s);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,2,3,4,5};
		new combination_calculator().calculate(a, 3, " ");
	}

}