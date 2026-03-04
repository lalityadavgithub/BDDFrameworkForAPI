package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinition extends Utils {
	
	RequestSpecification res;
    Response response;
    ResponseSpecification resspec;
    RequestSpecification req;
    TestDataBuild data=new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		

	 res=given().spec(requestSpecification())
	.body(data.addPlacePayload(name, language, address));
	 
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resouces, String method) {
		
		//Write code here that turns the phrase above into concrete actions
		//constructor will be called with value of resouces which you pass in feature file
		
		APIResources resourceAPI=APIResources.valueOf(resouces);
		
		System.out.println(resourceAPI.getResource());
		 resspec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		 if(method.equalsIgnoreCase("POST")) 
		 response=res.when().post(resourceAPI.getResource());
		 
		 else if(method.equalsIgnoreCase("GET")) 
			 response=res.when().get(resourceAPI.getResource());
		 
		 
				 
				

	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Then("{string} in response body is {string}")
	public void the_response_body_is(String keyValue , String Expectedvalue) {
		String resp=response.asString();
		JsonPath js=new JsonPath(resp);
		assertEquals(js.get(keyValue).toString(), Expectedvalue);
		
		
		
			}
	
	

}
