import javax.crypto.Mac;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;	
import java.security.NoSuchAlgorithmException;


class SHA1_class {
	private static String convToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
	    for (int i = 0; i < data.length; i++) {
	    	int halfbyte = (data[i] >>> 4) & 0x0F;
	        int two_halfs = 0;
	        do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	            	buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
	        } while(two_halfs++ < 1);
		}
	    return buf.toString();
	}

	public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convToHex(sha1hash);
	    }
}

public class Commitment {
	// public static void main(String[] args) {
	// 	String[][] m = getRandMatrix(9,9);
	// 	for (int i = 0; i < m.length; i++){
	// 		for (int j = 0; j < m.length; j++)
	// 			System.out.print(m[i][j]);
	// 		System.out.println();
	// 	}
	// }
	
	public static String[][] bitCommit_HASH_SHA2_list(String[][] list1, String[][] list2, String[][] bitList){
		String[][] commitment = new String[bitList.length][bitList.length];
		for (int i = 0; i < bitList.length; i++){
			for (int j = 0; j < bitList.length; j++){
				StringBuilder sb = new StringBuilder();
				sb.append(list1[i][j]);
				sb.append(list2[i][j]);
				sb.append(bitList[i][j]);
				try{
				commitment[i][j] = SHA1_class.SHA1(sb.toString());
				}catch (Exception e){
				};
			}
		}
		return commitment;
	}
	
	public static String[][] getRandMatrix(int size, int maxRandValue){
		Random random = new Random();
		String[][] matrix = new String[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				matrix[i][j] = Integer.toString(random.nextInt(maxRandValue));
			}
		}
		return matrix;
	}
}

