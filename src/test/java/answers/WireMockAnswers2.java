package answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockAnswers2 {
	
	WireMockServer wms;
	
	@BeforeClass
	public void setupMockServer() {
				
		configureFor("localhost",9876);
		
		wms = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(9876));
		wms.start();
	}
	
	public void setupStubExercise201() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise201
		 * and responds only to GET requests where the path
		 * is equal to /exercise 201
		 * Response body should equal 'Exercise 201 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise201"))
				.willReturn(aResponse()
						.withBody("Exercise 201 passed")
				));
	}
	
	public void setupStubExercise202() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise202
		 * and responds only to GET requests that contain
		 * the text 'Exercise202Body' in the body of the request
		 * Response body should equal 'Exercise 202 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise202"))
				.withRequestBody(containing("Exercise202Body"))
				.willReturn(aResponse()
						.withBody("Exercise 202 passed")
				));
	}
	
	public void setupStubExercise203() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise203
		 * and responds only to GET requests that have a header element
		 * 'MyHeader' with value 'MyHeaderValue' and that do NOT have
		 * a header element 'NoSuchElement' 
		 * Response body should equal 'Exercise 203 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise203"))
				.withHeader("MyHeader",equalTo("MyHeaderValue"))
				.withHeader("NoSuchElement", absent())
				.willReturn(aResponse()
						.withBody("Exercise 203 passed")
				));
	}
	
	public void setupStubExercise204() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise204
		 * and responds only to GET requests with Basic
		 * authentication set to
		 * username: wiremock
		 * password: workshop
		 * Response body should equal 'Exercise 204 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise204"))
				.withBasicAuth("wiremock", "workshop")
				.willReturn(aResponse()
						.withBody("Exercise 204 passed")
				));
	}
	
	public void setupStubExercise205() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise205
		 * and responds only to GET requests that contain
		 * a cookie 'MyCookie' with value 'ChocolateChip'
		 * Response body should equal 'Exercise 205 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise205"))
				.withCookie("MyCookie",equalTo("ChocolateChip"))
				.willReturn(aResponse()
						.withBody("Exercise 205 passed")
				));
	}
	
	@Test
	public void testExercise201() {
		
		setupStubExercise201();
        
	    given().
	    when().
	        get("http://localhost:9876/exercise201").
	    then().
	    	assertThat().
	    	body(org.hamcrest.Matchers.equalTo("Exercise 201 passed"));
	}
	
	@Test
	public void testExercise202() {
		
		setupStubExercise202();
        
	    given().
	    	body("DefineExercise202BodyLikeThis").
	    when().
	        get("http://localhost:9876/exercise202").
	    then().
	        assertThat().
	        body(org.hamcrest.Matchers.equalTo("Exercise 202 passed"));
	}
	
	@Test
	public void testExercise203() {
		
		setupStubExercise203();
        
	    given().
	    	header("MyHeader","MyHeaderValue").
	    when().
	        get("http://localhost:9876/exercise203").
	    then().
	        assertThat().
	        body(org.hamcrest.Matchers.equalTo("Exercise 203 passed"));
	}
	
	@Test
	public void testExercise204() {
		
		setupStubExercise204();
        
	    given().
	    	auth().
	    	preemptive().
	    	basic("wiremock","workshop").
	    when().
	        get("http://localhost:9876/exercise204").
	    then().
	        assertThat().
	        body(org.hamcrest.Matchers.equalTo("Exercise 204 passed"));
	}
	
	@Test
	public void testExercise205() {
		
		setupStubExercise205();
        
	    given().
	    	cookie("MyCookie","ChocolateChip").
	    when().
	        get("http://localhost:9876/exercise205").
	    then().
	        assertThat().
	        body(org.hamcrest.Matchers.equalTo("Exercise 205 passed"));
	}
}