package answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockAnswers1 {

	WireMockServer wms;
	
	@BeforeClass
	public void setupMockServer() {
				
		configureFor("localhost",9876);
		
		wms = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(9876));
		wms.start();
	}
	
	public void setupStubExercise101() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise101
		 * and responds to all GET requests with HTTP status code 200
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise101"))
				.willReturn(aResponse()
						.withStatus(200)
				));
	}
	
	public void setupStubExercise102() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise102
		 * and responds to all GET requests with a response
		 * with Content-Type text/plain
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise102"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "text/plain")
				));
	}
	
	public void setupStubExercise103() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise103
		 * and responds to all GET requests with a response
		 * with Content-Type text/plain and with body 'Exercise 103'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise103"))
				.willReturn(aResponse()
						.withBody("Exercise 103")
				));
	}
	
	@Test
	public void testExercise101() {
        
	    setupStubExercise101();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise101").
	    then().
	        assertThat().
	        statusCode(200);
	}
	
	@Test
	public void testExercise102() {
        
	    setupStubExercise102();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise102").
	    then().
	        assertThat().
	        contentType(ContentType.TEXT);
	}
	
	@Test
	public void testExercise103() {
        
	    setupStubExercise103();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise103").
	    then().
	        assertThat().
	        body(org.hamcrest.Matchers.equalTo("Exercise 103"));
	}
	
	@AfterClass
	public void destroyMockServer() {
		
		wms.stop();
	}
}
