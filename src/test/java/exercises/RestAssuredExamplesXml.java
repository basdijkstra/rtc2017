package exercises;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredExamplesXml {
	
	@Test
	public void checkCountryForFirstCar() {
						
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car[0].country", equalTo("Italy"));
	}	
	
	@Test
	public void checkYearForLastCar() {
						
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car[-1].year", equalTo("2012"));
	}
	
	@Test
	public void checkModelForSecondCar() {
		
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car[1].@model", equalTo("DB11"));
	}
	
	@Test
	public void checkThereIsOneJapaneseCar() {
		
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car.findAll{it.country=='Japan'}.size()", equalTo(1));
	}
	
	@Test
	public void checkThereAreTwoCarsThatAreMadeEitherInItalyOrInTheUK() {
			
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car.findAll{it.country in ['Italy','UK']}.size()", equalTo(2));
	}
	
	@Test
	public void checkThereAreTwoCarsWhoseMakeStartsWithAnA() {
		
		given().
		when().
			get("http://localhost:9876/xml/cars").
		then().
			assertThat().
			body("cars.car.@make.grep(~/A.*/).size()", equalTo(2));
	}
}
