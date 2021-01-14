package csci310;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
	public static String hashPassword(String passwordString) 
    { 
        try { 
            // initialize SHA-512 encryption
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 
  
            // computes hash from input
            byte[] hashedDigest = md.digest(passwordString.getBytes());
            BigInteger no = new BigInteger(1, hashedDigest); 
  
            // convert bigint to 32 bit hex string
            String hashString = no.toString(16); 
  
            // pad hashString so it is 32 bits long
            while (hashString.length() < 32) { 
                hashString = "0" + hashString; 
            } 
            
            return hashString; 
        } 
  
        // in case messagedigest does not recognize SHA-512 as input
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
}
