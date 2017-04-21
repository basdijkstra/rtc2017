package exercises;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredSmokeTest {

	@Test
	public void testIfProjectImportWasSuccessful() {

		given().
			params("text", "testcaseOne").
		when().
			get("http://md5.jsontest.com").
		then().
			body("md5",equalTo("4ff1c9b1d1f23c6def53f957b1ed827f"));
	}
}
