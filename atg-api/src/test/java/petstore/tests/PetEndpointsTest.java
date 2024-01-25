package petstore.tests;

import controllers.PetController;
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
        //Arrange
        File file = File.createTempFile("test", ".png");
        BigInteger petId = petController.postPet(fullDataPet).as(Pet.class).getId();
        //Act
        ApiResponse apiResponse = petController.postPetUploadImage(petId.toString(), file).as(ApiResponse.class);
        //Assert
        assertAll(
                "Upload Image Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(apiResponse.getMessage()).contains(MessageFormat.format("File uploaded to ./{0}", file.getName()))
        );
    }

    @Tag("create")
    @DisplayName("Add a new pet to the store")
    @Test
    public void createAPet() {
        //Arrange & Act
        Response response = petController.postPet(fullDataPet);
        Pet pet = response.as(Pet.class);
        //Assert
        assertAll(
                "Create Pet Assertion",
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(pet.getName()).isNotEmpty(),
                () -> assertThat(pet.getStatus()).isEqualTo(Status.available)
        );
    }

    @Tag("update")
    @DisplayName("Updates an existing pet in the store")
    @Test
    public void updateAPet() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(11223345));
        Response response = petController.postPet(fullDataPet);
        Pet pet = response.as(Pet.class);
        fullDataPet.setName("Changed Name");
        //Act
        Pet pet2 = petController.putPet(fullDataPet).as(Pet.class);
        //Assert
        assertAll(
                "Update Pet Assertion",
                () -> assertThat(pet2.getName()).isNotEqualTo(pet.getName()),
                () -> assertThat(pet2.getId()).isEqualTo(pet.getId())
        );
    }

    @Tag("get")
    @DisplayName("Find pets by status")
    @Test
    public void getPetsByStatus() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(15205802));
        fullDataPet.setStatus(Status.pending);
        Pet petOriginal = petController.postPet(fullDataPet).as(Pet.class);
        //Act
        Response response = petController.getPetByStatus(Status.pending);
        List<Pet> pets = Arrays.asList(response.as(Pet[].class));
        Optional<Pet> foundPet = pets.stream().filter(pet -> pet.getId().equals(petOriginal.getId())).findFirst();
        assert foundPet.orElse(null) != null;
        //Assert
        assertAll(
                "Find By Status Assertion",
                () -> assertThat(foundPet.get().getId()).isEqualTo(petOriginal.getId()),
                () -> assertThat(foundPet.get().getName()).isEqualTo((petOriginal.getName()))
        );

    }

    @Tag("get")
    @DisplayName("Find a pet by id")
    @Test
    public void getPetById() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(932852));
        Pet petOriginal = petController.postPet(fullDataPet).as(Pet.class);
        //Act
        Pet petRetrieved = petController.getPetById(petOriginal.getId().toString()).as(Pet.class);
        //Assert
        assertAll(
                "Find By Id Assertion",
                () -> assertThat(petOriginal.getId()).isEqualTo(petRetrieved.getId()),
                () -> assertThat(petOriginal.getName()).isEqualTo(petRetrieved.getName())
        );
    }

    @Tag("update")
    @DisplayName("Updates a pet with form data")
    @Test
    public void updatePetByIdWithFormData() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(125496136));
        Pet petOriginal = petController.postPet(fullDataPet).as(Pet.class);
        //Act
        Response response = petController.postWithFormData(petOriginal.getId().toString(), "New name", Status.pending);
        ApiResponse apiResponse = response.as(ApiResponse.class);
        Pet petUpdated = petController.getPetById(fullDataPet.getId().toString()).as(Pet.class);
        //Assert
        assertAll(
                "Update Pet Form Data Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(apiResponse.getMessage()).isEqualTo(fullDataPet.getId().toString()),
                () -> assertThat(petOriginal.getName()).isNotEqualTo(petUpdated.getName())
        );
    }

    @Tag("delete")
    @DisplayName("Delete a pet by id")
    @Test
    public void deletePetById() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(666));
        Pet petOriginal = petController.postPet(fullDataPet).as(Pet.class);
        //Act
        Response response = petController.deletePet(petOriginal.getId().toString());
        ApiResponse apiResponse = response.as(ApiResponse.class);
        ApiResponse petRetrievedError = petController.getPetById(petOriginal.getId().toString()).as(ApiResponse.class);
        //Assert
        assertAll(
                "Delete Pet Assertion",
                () -> assertThat(apiResponse.getCode()).isEqualTo(HttpStatus.SC_OK),
                () -> assertThat(petRetrievedError.getCode()).isEqualTo(1),
                () -> assertThat(petRetrievedError.getType()).isEqualTo("error"),
                () -> assertThat(petRetrievedError.getMessage()).isEqualTo("Pet not found")
        );
    }

    @Tag("delete")
    @Tag("me")
    @DisplayName("Fails to delete a pet by id on second attempt")
    @Test
    public void deletePetByIdFails() {
        //Arrange
        fullDataPet.setId(BigInteger.valueOf(737));
        Pet petOriginal = petController.postPet(fullDataPet).as(Pet.class);
        petController.deletePet(petOriginal.getId().toString());
        //Act
        Response response = petController.deletePet(petOriginal.getId().toString());
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }
}
