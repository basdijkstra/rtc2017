package answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockAnswers4 {
	
	WireMockServer wms;
	
	@BeforeClass
	public void setupMockServer() {
				
		configureFor("localhost",9876);
		
		wms = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(9876));
		wms.start();
	}
	
	public void setupStubExercise401() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise401 and exerts the following behavior:
		 * - The scenario is called 'Light bulb'
		 * - All responses have HTTP status 200
		 * - 1. A GET returns a body 'No light bulb found'
		 * - 2. A POST with body 'Insert light bulb' returns a body 'Light bulb inserted'
		 * 		and causes a transition to state 'LIGHT_OFF'
		 * - 3. A 2nd GET returns a body 'Light is OFF'
		 * - 4. A 2nd POST with body 'Switch light ON' returns a body 'Light has been turned ON'
		 * 		and causes a transition to state 'LIGHT_ON'
		 * - 5. A 3rd GET returns a body 'Light is ON'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs(Scenario.STARTED)
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("No light bulb found")
				));
		
		stubFor(post(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs(Scenario.STARTED)
				.withRequestBody(equalTo("Insert light bulb"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light bulb inserted")
				)
				.willSetStateTo("LIGHT_OFF"));
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_OFF")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light is OFF")
				));
		
		stubFor(post(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_OFF")
				.withRequestBody(equalTo("Switch light ON"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light has been turned ON")
				)
				.willSetStateTo("LIGHT_ON"));
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_ON")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light is ON")
				));				
	}
	
	@Test
	public void testExercise401() {
		
		String url = "http://localhost:9876/exercise401";
        
	    setupStubExercise401();
	         
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(org.hamcrest.Matchers.equalTo("No light bulb found"));
	    
	    given().
	    	body("Insert light bulb").
	    when().
	        post(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(org.hamcrest.Matchers.equalTo("Light bulb inserted"));
	    
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(org.hamcrest.Matchers.equalTo("Light is OFF"));
	    
	    given().
	    	body("Switch light ON").
	    when().
	        post(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(org.hamcrest.Matchers.equalTo("Light has been turned ON"));
	    
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(org.hamcrest.Matchers.equalTo("Light is ON"));
	}
}
