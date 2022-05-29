package com.up42.codingchallenge.service

import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.exception.FeatureNotFoundException
import com.up42.codingchallenge.service.datareader.FeatureDataReader
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.util.*

/**
 * Created by santoshsharma on 28 May, 2022
 */


/*
* This service class handles Feature requests.
* */
@Service
class FeaturesService (@Autowired @Qualifier("fileFeatureDataReader") var featureDataReader: FeatureDataReader) {

    /*
    * Loads all available features from [featureDataReader].
    *
    * @return Collection of Feature domain */
    fun getAllFeatures(): List<FeatureCollection.Feature> {
        val features: List<FeatureCollection.Feature>

        try {
            features = featureDataReader.readFeatureData()

        } catch (ex: FileNotFoundException) {
            throw FeatureNotFoundException(FeatureConstants.FEATURE_DATA_FILE_NOT_FOUND)
        } catch (ex: Exception) {
            throw ex
        }

        return features;
    }

    /*
    * Searches and loads Feature.
    * 
    * @param featureId - feature id to search in Feature
    * @return Feature containing [featureId, timestamp, beginViewingDate, endViewingDate and missionName]
    * @throw FeatureNotFoundException
    * */
    fun getFeatureById(featureId: UUID): FeatureCollection.Feature {
        return featureDataReader.readFeatureData()
            .filter { it.properties?.id == featureId }
            .ifEmpty { throw FeatureNotFoundException("Feature Id $featureId not found") }
            .first()
    }

    /*
    * Gets the image for feature id
    * @param featureId - feature id to get the image
    * @return base64 image read from base64 encoded string under quicklook field
    * */
    fun getFeatureQuickLookByFeatureId(featureId: UUID): ByteArray {
        val quickLookByteArray = featureDataReader.readFeatureData()
            .filter { it.properties?.id == featureId }
            .map { it.properties?.quicklook }
            .first().orEmpty()

        return Base64.decodeBase64(quickLookByteArray);
    }

}