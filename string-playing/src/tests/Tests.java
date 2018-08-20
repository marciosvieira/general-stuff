package tests;

import java.util.HashMap;

public abstract class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String S = "John Doe, Peter Parker, Mary Jane Watson-Parker, James Doe, John Elvis Doe, Jane Doe, Penny Parker";
		String C = "Example";
		
		
		StringBuilder adressList = new StringBuilder();
		
		String [] names = S.trim().split("\\s*,\\s*");
		
		String [] splited = null;
		
		StringBuilder email = null;
		
		StringBuilder fullAddress = null;
		
		HashMap<String, Integer> mailCount = new HashMap<>();
		
		Integer count = 0;

		for (int i = 0; i < names.length; i++) {
			
			fullAddress = new StringBuilder();
			fullAddress.append(names[i]);
			fullAddress.append(" <");
			
			splited = names[i].split(" ");
			
			email = new StringBuilder();
			
			email.append(splited[splited.length-1].replaceAll("-", "").toLowerCase());
			email.append(".");
			
			for (int j = 0; j < splited.length-1 ; j++) {
				
				email.append(splited[j].substring(0, 1).toLowerCase());

				if (j != splited.length-2) {
					email.append(".");
				}
				
			}
			
			fullAddress.append(email);
			
			count = mailCount.get(email.toString());
			
			if (count == null) {
				count = 0;
			}
			
			if (count > 0) {
				fullAddress.append(count+1);
			}
			
			mailCount.put(email.toString(), count+1);
			
			fullAddress.append("@");
			
			fullAddress.append(C.toLowerCase());
			
			fullAddress.append(".com>");
			
			adressList.append(fullAddress);
			
			if (i != names.length-1) {
				adressList.append(", ");
			}
		}
		
		System.out.println(adressList.toString());

	}

}
