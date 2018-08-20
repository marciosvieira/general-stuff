package tests;

public class Tests2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = "ABCBBCBA";
		
		Boolean condition = true;
		
		while (condition) {
			
			if (str.indexOf("AA") != -1) {
				str = str.replace("AA", "");
			}
			
			if (str.indexOf("BB") != -1) {
				str = str.replace("BB", "");
			}
			
			if (str.indexOf("CC") != -1) {
				str = str.replace("CC", "");
			}
			
			condition = str.indexOf("AA") != -1 || str.indexOf("BB") != -1 || str.indexOf("CC") != -1;
			
		}
		
		System.out.println(str);

	}

}
