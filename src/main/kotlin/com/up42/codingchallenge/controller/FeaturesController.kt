package com.up42.codingchallenge

import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.service.FeatureModelAssembler
import com.up42.codingchallenge.service.FeaturesService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.hateoas.EntityModel
import java.util.*

@RestController
@RequestMapping("/features")
class FeaturesController(@Autowired var featureService: FeaturesService,
var featureModelAssembler: FeatureModelAssembler) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getFeatures(): ResponseEntity<List<FeatureCollection.Feature>> {
        if(logger.isInfoEnabled) {
            logger.info("Inside getFeatures()")
        }
        return ResponseEntity(featureService.getAllFeatures(), HttpStatus.OK)
    }

    @GetMapping("/{featureId}")
    fun getFeatureById(@PathVariable featureId: UUID): EntityModel<FeatureCollection.Feature> {
        if(logger.isInfoEnabled) {
            logger.info("Searching Feature by id {}",featureId)
        }
        return featureModelAssembler.toModel(featureService.getFeatureById(featureId))
    }

    @GetMapping("/{featureId}/quicklook")
    fun getFeatureQuickLookByFeatureId(@PathVariable featureId: UUID): ResponseEntity<Any> {
        if(logger.isInfoEnabled) {
            logger.info("Searching Image by feature id {}",featureId)
        }

        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG

        return ResponseEntity(
            featureService.getFeatureQuickLookByFeatureId(featureId),
            headers,
            HttpStatus.OK
        )
    }

}
