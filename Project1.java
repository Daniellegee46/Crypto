//Corey Poole and Danielle Grieco
import java.util.ArrayList;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
//part1: ct = [E5, 0E, A6, 1E, 50, A3, D2, 69]
//part 2 pt = CPSC 428
//part 3 key: [0x90, 0x4E, 0xF2, 0xCC, 0x86, 0x02, 0x4A, 0x16]
//part 4 key: [BA, 54, 68, 08, 12, D4, C6, 9E]
//part4 plain text: Good Job

public class Project1 {
	public static void printByteArray(byte [] array)
    {
	System.out.print("[");
	for(int i = 0; i < array.length-1; i++)
	{
	    System.out.print(Integer.toHexString((array[i]>>4)&0x0F).toUpperCase());
	    System.out.print(Integer.toHexString(array[i]&0x0F).toUpperCase() + ", ");
	}
	System.out.print(Integer.toHexString(array[array.length-1]>>4&0x0F).toUpperCase());
	System.out.println(Integer.toHexString(array[array.length-1]&0x0F).toUpperCase() + "]");
    } 
	public static void Encryption() {
		Cipher cipher;
		SecretKeySpec key;
		byte[] keyBytes;
		byte[] pt;
		byte[] ct;
		keyBytes = new byte[] {(byte) 0x7A, (byte) 0x90, (byte) 0xC8, (byte) 0x36, (byte) 0x44, (byte) 0x0E, (byte) 0x18, (byte) 0x76};
		String ptString = new String("Dee Bugg");
		pt = ptString.getBytes();
		System.out.println("Plaintext: " + ptString);
		key = new SecretKeySpec(keyBytes, "DES");
		
		try {
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			ct = cipher.doFinal(pt);
			System.out.println("CipherText bytes:");
			printByteArray(ct);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void Decryption() {
		Cipher cipher;
		SecretKeySpec key;
		byte[] keyBytes;
		byte[] pt;
		byte[] ct;
		keyBytes = new byte[] {(byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92, (byte) 0xD2};		
		ct = new byte[] {(byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
		key = new SecretKeySpec(keyBytes, "DES");
		try {
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			pt = cipher.doFinal(ct);
			System.out.println("PlainText bytes:");
			printByteArray(pt);
			String ptString = new String(pt, "UTF-8");
			System.out.println("PlainText string: " + ptString);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static void part3() {
		Cipher cipher;
		SecretKeySpec key;		
		String ptString = "Captains";
		byte[] ct = new byte[] {(byte) 0xA5, (byte) 0x99, (byte) 0x04, (byte) 0x72, (byte) 0x39, (byte) 0x95, (byte) 0x41, (byte) 0xEC };
		byte[] k = new byte[] {(byte) 0x90, (byte) 0x4E, (byte) 0xF2, (byte) 0xCC, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte)
		 0x00};
		byte[] pt = ptString.getBytes();
		byte[] temp = new byte[8];
		while(!arraysEqual(pt, temp)) {
			increment(k);
			printByteArray(k);
			key = new SecretKeySpec(k, "DES");
			try {
				cipher = Cipher.getInstance("DES/ECB/NoPadding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				temp = cipher.doFinal(ct);				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		printByteArray(k);
		try {
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			key = new SecretKeySpec(k, "DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			temp = cipher.doFinal(ct);	
			String result = new String(temp, "UTF-8");
			System.out.println(result);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void part4() {
		Cipher cipher;
		SecretKeySpec key;	
		ArrayList<String> list = new ArrayList<String>();
		byte[] ct = new byte[] {(byte) 0xB1, (byte) 0x80, (byte) 0xE8, (byte) 0x05, (byte) 0x4E, (byte) 0x7D, (byte) 0xD6, (byte) 0x4C };
		byte[] k = new byte[] {(byte) 0xBA, (byte) 0x54, (byte) 0x68, (byte) 0x08, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte)
		 0x00};
		byte[] pt = new byte[8];
		String ptString = "???";
		int count = -1;
		while(!ptString.equals("Good Job")) {
			increment(k);
			key = new SecretKeySpec(k, "DES");
			try {
				cipher = Cipher.getInstance("DES/ECB/NoPadding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				pt = cipher.doFinal(ct);
				//System.out.print("Key: ");
				//printByteArray(k);
				ptString = new String(pt, "UTF-8");
				count = 0;
				for(int i = 0; i < ptString.length(); i++) {
					if(((Character.isLetter(ptString.charAt(i)) || ptString.charAt(i) == ' '))) {
			              ++count;
			          }
				}
				if(count == 8) {
					list.add(ptString);
				}
				printByteArray(k);
				System.out.print("pt: " + ptString);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		printByteArray(k);
		
	}
	public static boolean isAlpha(String x) {
		for(int i = 0; i < x.length(); i++) {
			if((Character.isLetter(x.charAt(i)) == false) && (x.charAt(i) != ' ')) {
	              return false;
	          }
		}
		return true;
	}
	public static void increment(byte [] b) {    
		// starting from back, add one and check if carry over
		  // if no carry over, stop and return
		  // if carry over, increment next byte and check again
		  for(int i = b.length-1; i>=0  ; i--){ 
			  b[i]++;
		  if(b[i] != 0x00){
		    return;
		    }
		     }   
		}
	public static boolean arraysEqual(byte[] a, byte[] b) {
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		//Encryption();
		//Decryption();
		//part3();
		part4();
	}
}
