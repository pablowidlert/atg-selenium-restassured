package petstore.tests;

import controllers.PetController;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.ApiResponse;
import models.Pet;
import models.Status;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.TestDataUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Tag("pet")
@DisplayName("Pet Endpoint Tests")
@Feature("Pet Endpoints for Swagger Petstore")
public class PetEndpointsTest extends BaseTest {
    private final Pet fullDataPet = TestDataUtil.generateFullDataPet();
    PetController petController = new PetController();

    @Tag("upload")
    @DisplayName("Upload an image for a pet")
    @Test
    public void uploadImageToPet() throws IOException {
        File file = File.createTempFile("test", ".png");
        BigInteger petId = Allure.step("Create a pet for the image upload", () ->
                petController.postPet(fullDataPet).as(Pet.class).getId());
        ApiResponse apiResponse = Allure.step("Upload an image for the pet", () ->
                petController.postPetUploadImage(petId.toString(), file).as(ApiResponse.class));
        Allure.step("Verify the image upload response", () -> assertAll(
                "Upload Image Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(apiResponse.getMessage()).contains(MessageFormat.format("File uploaded to ./{0}", file.getName()))
        ));
    }

    @Tag("create")
    @DisplayName("Add a new pet to the store")
    @Test
    public void createAPet() {
        Response response = Allure.step("Create a pet", () -> petController.postPet(fullDataPet));
        Pet pet = response.as(Pet.class);
        Allure.step("Verify the created pet", () -> assertAll(
                "Create Pet Assertion",
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(pet.getName()).isNotEmpty(),
                () -> assertThat(pet.getStatus()).isEqualTo(Status.available)
        ));
    }

    @Tag("update")
    @DisplayName("Updates an existing pet in the store")
    @Test
    public void updateAPet() {
        Allure.step("Prepare the pet for update", () -> fullDataPet.setId(BigInteger.valueOf(11223345)));
        Response response = Allure.step("Create the original pet", () -> petController.postPet(fullDataPet));
        Pet pet = response.as(Pet.class);
        Allure.step("Change the pet name", () -> fullDataPet.setName("Changed Name"));
        Pet pet2 = Allure.step("Update the pet", () -> petController.putPet(fullDataPet).as(Pet.class));
        Allure.step("Verify the pet was updated", () -> assertAll(
                "Update Pet Assertion",
                () -> assertThat(pet2.getName()).isNotEqualTo(pet.getName()),
                () -> assertThat(pet2.getId()).isEqualTo(pet.getId())
        ));
    }

    @Tag("get")
    @DisplayName("Find pets by status")
    @Test
    public void getPetsByStatus() {
        Allure.step("Prepare a pending pet", () -> {
            fullDataPet.setId(BigInteger.valueOf(15205802));
            fullDataPet.setStatus(Status.pending);
        });
        Pet petOriginal = Allure.step("Create the pending pet", () ->
                petController.postPet(fullDataPet).as(Pet.class));
        Response response = Allure.step("Find pets with pending status", () ->
                petController.getPetByStatus(Status.pending));
        List<Pet> pets = Arrays.asList(response.as(Pet[].class));
        Optional<Pet> foundPet = pets.stream().filter(pet -> pet.getId().equals(petOriginal.getId())).findFirst();
        Allure.step("Verify the pet appears in the results", () -> {
            assertThat(foundPet).isPresent();
            assertAll(
                "Find By Status Assertion",
                () -> assertThat(foundPet.get().getId()).isEqualTo(petOriginal.getId()),
                () -> assertThat(foundPet.get().getName()).isEqualTo((petOriginal.getName()))
            );
        });
    }

    @Tag("get")
    @DisplayName("Find a pet by id")
    @Test
    public void getPetById() {
        Allure.step("Prepare a pet with a known ID", () -> fullDataPet.setId(BigInteger.valueOf(932852)));
        Pet petOriginal = Allure.step("Create the pet", () ->
                petController.postPet(fullDataPet).as(Pet.class));
        Pet petRetrieved = Allure.step("Retrieve the pet by ID", () ->
                petController.getPetById(petOriginal.getId().toString()).as(Pet.class));
        Allure.step("Verify the retrieved pet matches", () -> assertAll(
                "Find By Id Assertion",
                () -> assertThat(petOriginal.getId()).isEqualTo(petRetrieved.getId()),
                () -> assertThat(petOriginal.getName()).isEqualTo(petRetrieved.getName())
        ));
    }

    @Tag("update")
    @DisplayName("Updates a pet with form data")
    @Test
    public void updatePetByIdWithFormData() {
        Allure.step("Prepare a pet for form-data update", () -> fullDataPet.setId(BigInteger.valueOf(125496136)));
        Pet petOriginal = Allure.step("Create the original pet", () ->
                petController.postPet(fullDataPet).as(Pet.class));
        Response response = Allure.step("Update the pet using form data", () ->
                petController.postWithFormData(petOriginal.getId().toString(), "New name", Status.pending));
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Pet petUpdated = Allure.step("Retrieve the updated pet", () ->
                petController.getPetById(fullDataPet.getId().toString()).as(Pet.class));
        Allure.step("Verify the form-data update", () -> assertAll(
                "Update Pet Form Data Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(apiResponse.getMessage()).isEqualTo(fullDataPet.getId().toString()),
                () -> assertThat(petOriginal.getName()).isNotEqualTo(petUpdated.getName())
        ));
    }

    @Tag("delete")
    @DisplayName("Delete a pet by id")
    @Test
    public void deletePetById() {
        Allure.step("Prepare a pet for deletion", () -> fullDataPet.setId(BigInteger.valueOf(666)));
        Pet petOriginal = Allure.step("Create the pet", () ->
                petController.postPet(fullDataPet).as(Pet.class));
        Response response = Allure.step("Delete the pet", () ->
                petController.deletePet(petOriginal.getId().toString()));
        ApiResponse apiResponse = response.as(ApiResponse.class);
        ApiResponse petRetrievedError = Allure.step("Confirm the deleted pet cannot be retrieved", () ->
                petController.getPetById(petOriginal.getId().toString()).as(ApiResponse.class));
        Allure.step("Verify the delete and not-found responses", () -> assertAll(
                "Delete Pet Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(petRetrievedError.getCode()).isEqualTo(1),
                () -> assertThat(petRetrievedError.getType()).isEqualTo("error"),
                () -> assertThat(petRetrievedError.getMessage()).isEqualTo("Pet not found")
        ));
    }

    @Tag("delete")
    @Tag("me")
    @DisplayName("Fails to delete a pet by id on second attempt")
    @Test
    public void deletePetByIdFails() {
        Allure.step("Prepare a pet for deletion", () -> fullDataPet.setId(BigInteger.valueOf(737)));
        Pet petOriginal = Allure.step("Create and delete the pet", () -> {
            Pet pet = petController.postPet(fullDataPet).as(Pet.class);
            petController.deletePet(pet.getId().toString());
            return pet;
        });
        Response response = Allure.step("Attempt to delete the pet again", () ->
                petController.deletePet(petOriginal.getId().toString()));
        Allure.step("Verify the second deletion returns not found", () ->
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND));
    }
}
