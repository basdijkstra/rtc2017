package exercises;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredExamples {
	
	@Test
	public void validateCountryForZipCode() {
						
		given().
		when().
			get("http://localhost:9876/us/90210").
		then().
			assertThat().
			body("country", equalTo("United States"));
	}
	
	@Test
	public void useQueryParametersSingleTestcase() {

		given().
			params("text", "testcaseOne").
		when().
			get("http://md5.jsontest.com").
		then().
			body("md5",equalTo("4ff1c9b1d1f23c6def53f957b1ed827f"));
	}

	@Test
	public void useMultipleQueryParameters() {

		given().
			params("q", "Kopenhagen", "mode", "xml").
		when().
			get("http://api.openweathermap.org/data/2.5/weather").
		then().
			body("current.city.country",equalTo("Denmark"));	
	}
	
	@Test
	public void useSinglePathParameter() {

		given().
			pathParam("driverName", "max_verstappen").
		when().
			get("http://ergast.com/api/f1/drivers/{driverName}.json").
		then()
			.body("MRData.DriverTable.Drivers.permanentNumber[0]",equalTo("33"));			
	}
	
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
	
	@Test
	public void useResponseSpecBuilder() {
		
		ResponseSpecBuilder rsBuilder = new ResponseSpecBuilder();
		
		rsBuilder.
			expectStatusCode(200).
				expectContentType(ContentType.JSON);
		
		ResponseSpecification respSpec = rsBuilder.build();
		
		given().
		when().
			get("http://localhost:9876/us/90210").
		then().
			spec(respSpec).
			and().
			assertThat().
			body("country", equalTo("United States"));
	}
}
