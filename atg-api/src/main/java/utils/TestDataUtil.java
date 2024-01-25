package utils;

import models.Category;
import models.Pet;
import models.Status;
import models.Tag;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class TestDataUtil {

    public static Pet generateFullDataPet() {
        return Pet.builder()
                .id(getRandomBigInt())
                .name(getRandomString())
                .photoUrls(Arrays.asList("https://"+getRandomString(), "https://"+getRandomString()))
                .category(Category.builder().id(getRandomBigInt()).name(getRandomString()).build())
                .tags(Arrays.asList(Tag.builder().id(getRandomBigInt()).name(getRandomString()).build(),
                        Tag.builder().id(getRandomBigInt()).name(getRandomString()).build()))
                .status(Status.available)
                .build();
    }

    private static BigInteger getRandomBigInt() {
        return new BigInteger(0, new SecureRandom());
    }

    private static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(7);
    }
}