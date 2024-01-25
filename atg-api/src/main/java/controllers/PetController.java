package controllers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Status;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetController {

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .addHeader("api_key", "12345")
            .build();


    public Response postPetUploadImage(String petId, File file) {
        return given(requestSpecification)
                .when()
                .contentType("multipart/form-data")
                .multiPart("file", file)
                .multiPart("additionalMetadata", "doge")
                .post(petId + "/uploadImage");
    }
    public Response postPet(Object pet) {
        return given(requestSpecification)
                .when()
                .body(pet)
                .post();
    }

    public Response putPet(Object pet) {
        return given(requestSpecification)
                .when()
                .body(pet)
                .put();
    }

    public Response getPetByStatus(Status status) {
        return given(requestSpecification)
                .queryParam("status", status)
                .when()
                .get("/findByStatus");
    }

    public Response getPetById(String petId) {
        return given(requestSpecification)
                .when()
                .get(petId);
    }

    public Response postWithFormData(String petId, String name, Status status) {
        return given(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(petId);
    }

    public Response deletePet(String petId) {
        return given(requestSpecification)
                .when()
                .delete(petId);
    }
}