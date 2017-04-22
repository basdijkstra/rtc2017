package answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

public class WireMockAnswers3 {
	
	WireMockServer wms;
	
	@BeforeClass
	public void setupMockServer() {
				
		configureFor("localhost",9876);
		
		wms = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(9876));
		wms.start();
	}
	
	public void setupStubExercise301() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise301
		 * and responds to all GET requests with HTTP
		 * status code 503 and a status message equal to
		 * 'Service unavailable'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise301"))
				.willReturn(aResponse()
						.withStatus(503)
						.withStatusMessage("Service unavailable")
				));
	}
	
	public void setupStubExercise302() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise302
		 * and responds to all GET requests with a fixed
		 * delay of 2000 milliseconds
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise302"))
				.willReturn(aResponse()
						.withFixedDelay(2000)
				));
	}
	
	public void setupStubExercise303() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise303
		 * and responds to all GET requests with garbage
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise303"))
				.willReturn(aResponse()
						.withFault(Fault.RANDOM_DATA_THEN_CLOSE)
				));
	}
	
	@Test
	public void testExercise301() {
        
	    setupStubExercise301();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise301").
	    then().
	    	assertThat().
	    	statusCode(503).
	    and().
	    	statusLine(containsString("Service unavailable"));	    	
	}
	
	@Test
	public void testExercise302() {
        
	    setupStubExercise302();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise302").
	    then().
	        assertThat().
	        time(greaterThan(2000L));
	}
	
	@Test(expectedExceptions = ClientProtocolException.class)
	public void testExercise303() {
        
	    setupStubExercise303();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise303").
	    then();
	}
}
