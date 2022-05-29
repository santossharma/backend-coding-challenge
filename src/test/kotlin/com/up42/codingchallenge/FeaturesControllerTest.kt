package com.up42.codingchallenge

import com.up42.codingchallenge.service.FeaturesService
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeaturesControllerTest() {

    @Autowired
    lateinit var featuresService: FeaturesService

    @LocalServerPort
    var port: Int = 0

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
    }


    @Test
    fun `should return all features`() {
        given()
            .get("/features")
            .then()
            .statusCode(200)
            .also { validatableResponse ->
                expectedFeatures.forEach { feature ->
                    validatableResponse.body("id[${feature.key}]", equalTo(feature.value.id))
                        .body("timestamp[${feature.key}]", equalTo(feature.value.timestamp))
                        .body("beginViewingDate[${feature.key}]", equalTo(feature.value.beginViewingDate))
                        .body("endViewingDate[${feature.key}]", equalTo(feature.value.endViewingDate))
                        .body("missionName[${feature.key}]", equalTo(feature.value.missionName))
                }
            }
    }

    @Test
    fun `should get feature by given featureId`() {
        val featureId = featuresService.getFeatureById(UUID.fromString(expectedFeatures.get(0)?.id)).id

        assertEquals(UUID.fromString(expectedFeatures.get(0)?.id), featureId)
    }

    @Test
    fun `should return feature`() {
        given()
            .get("/features")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .also { validatableResponse ->
                expectedFeatures.forEach { feature ->
                    validatableResponse.body("id[${feature.key}]", equalTo(feature.value.id))
                        .body("timestamp[${feature.key}]", equalTo(feature.value.timestamp))
                        .body("beginViewingDate[${feature.key}]", equalTo(feature.value.beginViewingDate))
                        .body("endViewingDate[${feature.key}]", equalTo(feature.value.endViewingDate))
                        .body("missionName[${feature.key}]", equalTo(feature.value.missionName))
                }
            }

    }

    @Test
    fun `should get image for quick look by given featureId`() {
        given()
            .get("/features/7f23a853-76a8-4787-a2ba-fdfe93e74165/quicklook")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.IMAGE_PNG_VALUE)
            .body(anything())
    }

    @Test
    fun `should not get image for quick look by given featureId`() {
        given()
            .get("/features/7f23a853-76a8-4787-a2ba-fdfe93e74166/quicklook")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .contentType(anything())
            .body(anything())
    }

}
