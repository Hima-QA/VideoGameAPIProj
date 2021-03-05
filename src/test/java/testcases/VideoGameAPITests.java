package testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;


public class VideoGameAPITests {
	
//	@Test
	public void test_getALLVideoGames() {
	
		//Response response = RestAssured.get("http://localhost:8080/app/videogames");
		Response response = get("http://localhost:8080/app/videogames");
		
		System.out.println(response.asString());
		System.out.println("Response Body:" +response.getBody().asString());
		System.out.println(response.getStatusCode());
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
	}

	@Test(priority=1)
	public void getALLVideoGames_tc001() {
		
		given()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200).log().all();
		
	}
	
	@Test(priority=2)
	public void addNewVideoGame_tc002() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",11);
		map.put("name","Riya");
		map.put("releaseDate","2021-03-04T14:18:03.733Z");
		map.put("reviewScore", 0);
		map.put("category","Shooter");
		map.put("rating","Universal");
		
		JSONObject request = new JSONObject(map);
		//System.out.println(request);
		System.out.println(request.toJSONString());

	}
	
	@Test(priority=3)
	public void addNewVideoGame_tc002a() {
		
		JSONObject request = new JSONObject();
		request.put("id",20);
		request.put("name","SuperMAN");
		request.put("releaseDate","2021-03-04T14:18:03.733Z");
		request.put("reviewScore", 0);
		request.put("category","Shooter");
		request.put("rating","Universal");
		System.out.println(request);
		System.out.println(request.toJSONString());
		
		Response resp = 
		given()
			.contentType("application/json")
			.body(request.toJSONString()).	
		when()
			.post("http://localhost:8080/app/videogames").
		then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString=resp.asString();//converting json to string 
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true); 
			
	}
	@Test(priority=4)
	public void getSingleVGame_tc004() {
		
		given()
		.when()
			.get("http://localhost:8080/app/videogames/20")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("20"));
		
	}
	@Test(priority=5)
	public void updateVideoGame_tc003() {
		
		JSONObject request = new JSONObject();
		request.put("id",20);
		request.put("name","Mario");
		request.put("releaseDate","2021-03-04T14:18:03.733Z");
		request.put("reviewScore", 0);
		request.put("category","Shooter");
		request.put("rating","Universal");
		
		
		given()
			.contentType("application/json")
			.body(request.toJSONString())	
		.when()
			.put("http://localhost:8080/app/videogames/20")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("20"))
			.body("videoGame.name", equalTo("Mario"));
	}
	
	@Test(priority=6)
	public void deleteVideoGame_tc005() throws InterruptedException {
		
		Response resp =
		given()
		.when()
			.delete("http://localhost:8080/app/videogames/20")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		Thread.sleep(3000);
		String jsonString=resp.asString();//converting json to string 
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true); 

	}
}
