package com.up42.codingchallenge.service

import com.up42.codingchallenge.byteArrayString
import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.service.datareader.FeatureDataReader
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

/**
 * Created by santoshsharma on 28 May, 2022
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class FeaturesServiceTest {

    var featuresService: FeaturesService = mockk(relaxed = true)
    var featureDataReader: FeatureDataReader = mockk()

    @BeforeAll
    fun setup() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun getAllFeatures() {

        val allFeatures = every { featuresService.getAllFeatures() } returns listOf(
            mockk {
                every { properties } returns mockk {
                    every { id } returns UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                    every { timestamp } returns 1554831167697
                    every { beginViewingDate } returns 1554831167697
                    every { endViewingDate } returns 1554831202043
                    every { missionName } returns "Sentinel-1B"
                }
            },
            mockk {
                every { properties } returns mockk {
                    every { id } returns UUID.fromString("0b598c52-7bf2-4df0-9d09-94229cdfbc0b")
                    every { timestamp } returns 1560661222337
                    every { beginViewingDate } returns 1560661222337
                    every { endViewingDate } returns 1560661247336
                    every { missionName } returns "Sentinel-1A"
                }
            }
        )

        assertEquals(2, featuresService.getAllFeatures().size)
    }

    @Test
    fun getFeatureById() {

        val feature = mockk<FeatureCollection.Feature> {
            every { properties } returns mockk {
                every { id } returns UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                every { timestamp } returns 1554831167697
                every { beginViewingDate } returns 1554831167697
                every { endViewingDate } returns 1554831202043
                every { missionName } returns "Sentinel-1B"
            }
        }

        every { featuresService.getFeatureById(UUID.randomUUID()) } returns feature

        assertEquals(UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea"), feature.properties?.id)
    }

    @Test
    fun getFeatureQuickLookByFeatureId() {
        val feature = mockk<FeatureCollection.Feature> {
            every { properties } returns mockk {
                every { id } returns UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                every { quicklook } returns byteArrayString
            }
        }

        assertEquals(byteArrayString, feature.properties?.quicklook)
        assertInstanceOf(ByteArray::class.java, featuresService.getFeatureQuickLookByFeatureId(UUID.fromString(feature.properties?.id.toString())))
    }
}