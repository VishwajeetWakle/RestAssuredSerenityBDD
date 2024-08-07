package day5;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ParsingXMLRes {
	
	
	@Test
	void testXMLRes() {
		
		// Approach 1
		
		given()
		.when()
			.get("http://restapi.adequateshop.com/api/Traveler?page=2")
		.then()
			.statusCode(200)
			.header("Content-Type", "application/xml")
			.body("TravelerinformationResponse.page", equalTo("1"))
			.body("TravelerinformationResponse.travelers.Travelerinformation[0].name", equalTo("Vijay Bharath Reddy"));
		
		
		// Approach 2
		
		Response res =
				given()
				.when()
					.get("http://restapi.adequateshop.com/api/Traveler?page=2");
		
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[0].name"), "Vijay Bharath Reddy");
	
	
	
	}
	
	
	@Test
	void testXMLResBody() {
		
		Response res =
				given()
				.when()
					.get("http://restapi.adequateshop.com/api/Traveler?page=2");
		
		
		XmlPath xmlObj = new XmlPath(res.asString());
		
		List<String> travellers = xmlObj.getList("TravelerinformationResponse.travelers.Travelerinformation");
		
		Assert.assertEquals(travellers.size(), 10);
		
		
		
	}

}
