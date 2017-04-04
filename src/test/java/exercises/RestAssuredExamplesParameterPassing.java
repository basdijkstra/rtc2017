package exercises;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredExamplesParameterPassing {
	
	private String accessToken;
	
	@BeforeClass
	public void useBasicAuthentication() {
		
		accessToken =
		
		given().
			params("grant_type","client_credentials").
			auth().
			preemptive().
			basic("AUyqLmmlHyX4Th7BdXpIN-sKu5rARNpWLNtQZabRneRp5eDrKEU5pdiNIOMgc-4OiNu4jX8VJwfwWr1a","ECFXJmz2yW0WDf0itUE13jgaBhLkF5kEV9pyzt8iK9vvWgoSBRQ0HCywNIqYftSwXmB6EH_KOGq0nO39").
		when().
			post("https://api.sandbox.paypal.com/v1/oauth2/token").
		then().
			extract().
			path("access_token");
	}
	
	@Test
	public void useOAuth2Authentication() {
		
		given().
			auth().
			oauth2(accessToken).
		when().
			get("https://api.sandbox.paypal.com/v1/identity/openidconnect/userinfo/?schema=openid").
		then().
			assertThat().
			body("",hasKey("user_id"));
	}
}