package data.remote.dto

import domain.model.TravelDetail

import domain.model.TravelDetail.Companion.VIEW_TYPE_COVER
import domain.model.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.TravelDetail.Companion.VIEW_TYPE_VIDEO
import util.EntityMapper
import javax.inject.Inject

class TravelDetailMapper @Inject constructor() : EntityMapper<TravelDetailDto, TravelDetail> {


    override fun toDomainModel(dto: TravelDetailDto): TravelDetail {
        return when (dto.type) {
            VIEW_TYPE_COVER       -> TravelDetail.Cover("", "")
            VIEW_TYPE_IMAGE       -> {
                val data = dto.data as ImageDto
                TravelDetail.Image(data.title, data.image)
            }
            VIEW_TYPE_DESCRIPTION -> {
                val data = dto.data as DescriptionDto
                TravelDetail.Description(data.title, data.description)
            }
            VIEW_TYPE_LINK        -> {
                val data = dto.data as LinkDto
                TravelDetail.Link(data.url)
            }
            VIEW_TYPE_VIDEO       -> {
                val data = dto.data as VideoDto
                TravelDetail.Video(data.title, data.video, data.frame)
            }
            else                  -> {
                TravelDetail.Cover("", "")
            }
        }
    }

    override fun toEntity(model: TravelDetail): TravelDetailDto {
        return when (model) {
            is TravelDetail.Cover       -> {
                TravelDetailDto(model.viewType, CoverDto(model.title, model.cover))
            }
            is TravelDetail.Description -> TravelDetailDto(model.viewType, DescriptionDto(model.title, model.description))
            is TravelDetail.Image       -> TravelDetailDto(model.viewType, ImageDto(model.title, model.imageUrl))
            is TravelDetail.Link        -> TravelDetailDto(model.viewType, LinkDto(model.url))
            is TravelDetail.Video       -> TravelDetailDto(model.viewType, VideoDto(model.title, model.video, model.image))
            else                        -> TravelDetailDto(model.viewType, CoverDto("", ""))
        }
    }


}




