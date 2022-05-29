package com.up42.codingchallenge.service

import com.up42.codingchallenge.FeaturesController
import com.up42.codingchallenge.domain.FeatureCollection
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by santoshsharma on 29 May, 2022
 */

@Component
class FeatureModelAssembler: RepresentationModelAssembler<FeatureCollection.Feature, EntityModel<FeatureCollection.Feature>> {
    override fun toModel(feature: FeatureCollection.Feature): EntityModel<FeatureCollection.Feature> {
        return EntityModel.of(feature,
            linkTo(methodOn(FeaturesController::class.java).getFeatureById(UUID.fromString(feature.id.toString()))).withSelfRel(),
            linkTo(methodOn(FeaturesController::class.java).getFeatureQuickLookByFeatureId(UUID.fromString(feature.id.toString()))).withRel("quicklook"),
        )
    }
}