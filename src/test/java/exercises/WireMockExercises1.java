package exercises;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockExercises1 {

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
		
	}
	
	public void setupStubExercise102() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise102
		 * and responds to all GET requests with a response
		 * with Content-Type text/plain
		 ************************************************/

	}
	
	public void setupStubExercise103() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise103
		 * and responds to all GET requests with a response
		 * with Content-Type text/plain and with body 'Exercise 103'
		 ************************************************/

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
