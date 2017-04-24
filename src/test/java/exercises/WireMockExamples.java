package exercises;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockExamples {
		
	WireMockServer wms;
	
	@BeforeClass
	public void setupMockServer() {
				
		configureFor("localhost",9876);
		
		wms = new WireMockServer(com.github.tomakehurst.wiremock.core.WireMockConfiguration.options().port(9876));
		wms.start();
	}
		
	public void setupExampleStub() {

		stubFor(post(urlEqualTo("/pingpong"))
				.withRequestBody(matching("<input>PING</input>"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/xml")
						.withBody("<output>PONG</output>")));
	}
	
	public void setupStubURLMatching() {
		
		stubFor(get(urlEqualTo("/urlmatching"))
				.willReturn(aResponse()
						.withBody("URL matching")
				));
	}
	
	public void setupStubRequestBodyMatching() {
		
		stubFor(post(urlEqualTo("/requestbodymatching"))
				.withRequestBody(containing("RequestBody"))
				.willReturn(aResponse()
						.withBody("Request body matching")
				));
	}
	
	public void setupStubHeaderMatching() {
		
		stubFor(get(urlEqualTo("/headermatching"))
				.withHeader("Content-Type", containing("application/json"))
				.withHeader("DoesntExist", absent())
				.willReturn(aResponse()
						.withBody("Header matching")
				));
	}
	
	public void setupStubAuthorizationMatching() {
		
		stubFor(get(urlEqualTo("/authorizationmatching"))
				.withBasicAuth("username", "password")
				.willReturn(aResponse()
						.withBody("Authorization matching")
				));
	}
	
	public void setupStubReturningErrorCode() {
		
		stubFor(get(urlEqualTo("/errorcode"))
				.willReturn(aResponse()
						.withStatus(500)
				));
	}
	
	public void setupStubFixedDelay() {
		
		stubFor(get(urlEqualTo("/fixeddelay"))
				.willReturn(aResponse()
						.withFixedDelay(2000)
				));
	}
	
	public void setupStubBadResponse() {
		
		stubFor(get(urlEqualTo("/badresponse"))
				.willReturn(aResponse()
						.withFault(Fault.MALFORMED_RESPONSE_CHUNK)
				));
	}
	
	public void setupStubStateful() {
		
		stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs(Scenario.STARTED)
				.willReturn(aResponse()
						.withBody("Your shopping cart is empty")
				));
		
		stubFor(post(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs(Scenario.STARTED)
				.withRequestBody(equalTo("Ordering 1 item"))
				.willReturn(aResponse()
						.withBody("Item placed in shopping cart")
				)
				.willSetStateTo("ORDER_PLACED"));
		
		stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs("ORDER_PLACED")
				.willReturn(aResponse()
						.withBody("There is 1 item in your shopping cart")
				));
	}
	
	@Test
	public void testPingPongPositive() {
        
	    setupExampleStub();
	         
	    given().
	        body("<input>PING</input>").
	    when().
	        post("http://localhost:9876/pingpong").
	    then().
	        assertThat().
	        statusCode(200).
	        and().
	        body("output", org.hamcrest.Matchers.equalTo("PONG"));
	}
	
	@Test
	public void testURLMatching() {
		
		setupStubURLMatching();
		
		given().
		when().
			get("http://localhost:9876/urlmatching").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("URL matching"));
	}
	
	@Test
	public void testRequestBodyMatching() {
		
		setupStubRequestBodyMatching();
		
		given().
			body("TestRequestBodyMatching").
		when().
			post("http://localhost:9876/requestbodymatching").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Request body matching"));
	}
	
	@Test
	public void testHeaderMatching() {
		
		setupStubHeaderMatching();
		
		given().			
			contentType("application/json").
		when().
			get("http://localhost:9876/headermatching").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Header matching"));
	}
	
	@Test
	public void testAuthorizationMatching() {
		
		setupStubAuthorizationMatching();
		
		given().			
			auth().
			preemptive().
			basic("username", "password").
		when().
			get("http://localhost:9876/authorizationmatching").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Authorization matching"));
	}
	
	@Test
	public void testErrorCode() {
		
		setupStubReturningErrorCode();
		
		given().
		when().
			get("http://localhost:9876/errorcode").
		then().
			assertThat().
			statusCode(500);
	}
	
	@Test
	public void testFixedDelay() {
		
		setupStubFixedDelay();
		
		given().
		when().
			get("http://localhost:9876/fixeddelay").
		then().
			assertThat().
			time(greaterThan(2000L),TimeUnit.MILLISECONDS);
			
	}
	
	@Test
	public void testStatefulStub() {
		
		setupStubStateful();
		
		given().
		when().
			get("http://localhost:9876/order").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Your shopping cart is empty"));
		
		given().
			body("Ordering 1 item").
		when().
			post("http://localhost:9876/order").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Item placed in shopping cart"));
		
		given().
		when().
			get("http://localhost:9876/order").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("There is 1 item in your shopping cart"));
	}
}
