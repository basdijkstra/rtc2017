package exercises;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredExercises4 {
	
	@BeforeClass
	public void initPath() {
		
		RestAssured.baseURI = "http://localhost:9876";
	}
		
	/*******************************************************
	 * Request an authentication token API and write the
	 * response to the console. Use preemptive Basic authentication:
	 * username = oauth
	 * password = gimmeatoken
	 * Use /v1/oauth2/token
	 ******************************************************/
	
	String oauthToken;
	
	@BeforeClass
	public void retrieveOAuthToken() {
		
		given().
		when().
		then();
	}
	
	/*******************************************************
	 * Request a list of payments for this account and check
	 * that the number of payments made equals 4.
	 * Use OAuth2 authentication with the previously retrieved
	 * authentication token.
	 * Use /v1/payments/payment/
	 ******************************************************/
	
	@Test
	public void checkNumberOfPayments() {
		
		given().
		when().
		then();
	}
	
	/*******************************************************
	 * Request the list of all circuits that hosted a
	 * Formula 1 race in 2014 and check that this request is
	 * answered within 100 ms
	 * Use /api/f1/2014/circuits.json
	 ******************************************************/
	
	@Test
	public void checkResponseTimeFor2014CircuitList() {
		
		given().
		when().
		then();
	}
}
