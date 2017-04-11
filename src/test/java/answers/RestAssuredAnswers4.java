package answers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredAnswers4 {
	
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
		
		oauthToken =
		
		given().
			auth().
			preemptive().
			basic("oauth","gimmeatoken").
		when().
			get("/v1/oauth2/token").
		then().
			extract().
			path("access_token");
	}
	
	/*******************************************************
	 * Request a list of payments for this account and check
	 * that the number of payments made equals 4.
	 * Use OAuth2 authentication with the previously retrieved
	 * authentication token.
	 * Use /v1/payments/payment/
	 * Value to be retrieved is in the paymentsCount field
	 ******************************************************/
	
	@Test
	public void checkNumberOfPayments() {
		
		given().
			auth().
			oauth2(oauthToken).
		when().
			get("/v1/payments/payment/").
		then().
			assertThat().
			body("paymentsCount",equalTo(4));
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
			get("/api/f1/2014/circuits.json").
		then().
			assertThat().
			time(lessThan(100L),TimeUnit.MILLISECONDS);
	}
}
