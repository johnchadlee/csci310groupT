package csci310;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class PasswordHasherTest {

    @Test
    public void testEmptyPassword() throws IOException {
    	String actualHash = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";
    	String samplePass = "";
    	String hashedPass = PasswordHasher.hashPassword(samplePass);
        Assert.assertEquals(actualHash, hashedPass);
    }
    
    @Test
    public void testValidPass1() throws IOException {
    	String actualHash = "9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca72323c3d99ba5c11d7c7acc6e14b8c5da0c4663475c2e5c3adef46f73bcdec043";
    	String samplePass = "hello";
    	String hashedPass = PasswordHasher.hashPassword(samplePass);
        Assert.assertEquals(actualHash, hashedPass);
    }
    
    @Test
    public void testValidPass2() throws IOException {
    	String actualHash = "de2c0320cdff37271049dfa8cb835ffd54200216253a1dfbad75a1ae51bd30bb499e14e37fe993ba2ea57b863fc56304de94073d880c9c18eb0a469cde211d02";
    	String samplePass = "goodbye";
    	String hashedPass = PasswordHasher.hashPassword(samplePass);
        Assert.assertEquals(actualHash, hashedPass);
    }    
}
