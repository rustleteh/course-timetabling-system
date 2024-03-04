package MyTimeTable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//	This class has a method that is used to hash the password entered by new users before storage in the SQLite database. 
//	It uses secure 'SHA-256' algorithm for encryption. The references for the code are written below.
	
public class PasswordHasher {
	
	public String hashPassword(String input) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger bI = new BigInteger(1, messageDigest);
		return bI.toString(16);
		
	}
}

//	Random code (30 April 2021) 'Java - Simple setup for hashing algorithms: MD5, SHA-1 and SHA-265' [video],
//	YouTube website, accessed 15 May 2021. https://youtu.be/ef3kenC4xa0

