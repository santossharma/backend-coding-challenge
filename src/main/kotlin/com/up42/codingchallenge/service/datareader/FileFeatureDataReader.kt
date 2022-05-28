package com.up42.codingchallenge.service.datareader

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.exception.FeatureNotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

/**
 * Created by santoshsharma on 28 May, 2022
 */

@Service
@Qualifier("fileFeatureDataReader")
class FileFeatureDataReader: FeatureDataReader {
    override fun readFeatureData(): List<FeatureCollection.Feature> {
        return ClassPathResource(FeatureConstants.SOURCE_DATA_JSON).file
            .readText()
            .let { jsonString -> jacksonObjectMapper().readValue<List<FeatureCollection>>(jsonString) }
            .flatMap { it.features }
            .map {
                it.apply {
                    id = properties?.id
                    timestamp = properties?.timestamp
                    beginViewingDate = properties?.acquisition?.beginViewingDate
                    endViewingDate = properties?.acquisition?.endViewingDate
                    missionName = properties?.acquisition?.missionName
                }
            }
            .ifEmpty {
                throw FeatureNotFoundException(FeatureConstants.NO_FEATURE_FOUND)
            }
    }
}