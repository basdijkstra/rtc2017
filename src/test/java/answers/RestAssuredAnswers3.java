package answers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class RestAssuredAnswers3 {
	
	@BeforeSuite
	public void initPath() {
		
		RestAssured.baseURI = "http://localhost:9876";
	}
		
	/*******************************************************
	 * Create a ResponseSpecification that checks whether:
	 * - the response has statusCode 200
	 * - the response contentType is JSON
	 * - the value of MRData.CircuitTable.Circuits.circuitName[0]
	 *   is equal to 'Albert Park Grand Prix Circuit'
	 ******************************************************/
	
	ResponseSpecification respSpec;
	
	@BeforeClass
	public void createResponseSpecification() {
		
		respSpec = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectContentType(ContentType.JSON).
				expectBody("MRData.CircuitTable.Circuits.circuitName[0]", equalTo("Albert Park Grand Prix Circuit")).
				build();		
	}
	
	/*******************************************************
	 * Retrieve the list of 2016 Formula 1 drivers and store
	 * the driverId for the ninth mentioned driver in a
	 * String variable
	 * Use /api/f1/2016/drivers.json
	 ******************************************************/
	
	String ninthDriverId;
	
	@BeforeClass
	public void getNinthDriverId() {
		
		ninthDriverId = 
				
		given().
		when().
			get("/api/f1/2016/drivers.json").
		then().
			extract().
			path("MRData.DriverTable.Drivers[8].driverId");
	}
	
	/*******************************************************
	 * Retrieve the circuit data for the first race in 2014
	 * Use the previously created ResponseSpecification to
	 * execute the specified checks
	 * Use /api/f1/2014/1/circuits.json
	 * Additionally, check that the circuit is located in Melbourne
	 ******************************************************/
	
	@Test
	public void useResponseSpecification() {
		
		given().
		when().
			get("/api/f1/2014/1/circuits.json").
		then().
			spec(respSpec).
			and().
			body("MRData.CircuitTable.Circuits.Location[0].locality",equalTo("Melbourne"));
	}
	
	/*******************************************************
	 * Retrieve the driver data for the ninth mentioned driver
	 * Use the previously extracted driverId to do this
	 * Use it as a path parameter to /api/f1/drivers/<driverId>.json
	 * Check that the driver is German
	 ******************************************************/
	
	@Test
	public void useExtractedDriverId() {
		
		given().
			pathParam("driverId",ninthDriverId).
		when().
			get("/api/f1/drivers/{driverId}.json").
		then().
			assertThat().
			body("MRData.DriverTable.Drivers[0].nationality", equalTo("German"));
	}
}