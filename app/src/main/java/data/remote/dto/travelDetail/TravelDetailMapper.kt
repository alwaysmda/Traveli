package data.remote.dto.travelDetail

import domain.model.travel.TravelDetail

import domain.model.travel.TravelDetail.Companion.VIEW_TYPE_COVER
import domain.model.travel.TravelDetail.Companion.VIEW_TYPE_DESCRIPTION
import domain.model.travel.TravelDetail.Companion.VIEW_TYPE_IMAGE
import domain.model.travel.TravelDetail.Companion.VIEW_TYPE_LINK
import domain.model.travel.TravelDetail.Companion.VIEW_TYPE_VIDEO
import util.EntityMapper
import javax.inject.Inject

class TravelDetailMapper @Inject constructor() : EntityMapper<TravelDetailDto, TravelDetail> {


    override fun toDomainModel(dto: TravelDetailDto): TravelDetail {
        return when (dto.type) {
            VIEW_TYPE_COVER       -> TravelDetail.Cover(dto.title, dto.url)
            VIEW_TYPE_IMAGE       -> TravelDetail.Image(dto.url)
            VIEW_TYPE_DESCRIPTION -> TravelDetail.Description(dto.title)
            VIEW_TYPE_LINK        -> TravelDetail.Link(dto.url)
            VIEW_TYPE_VIDEO       -> TravelDetail.Video(dto.url)
            else                  -> TravelDetail.Cover(dto.title, dto.url)
        }
    }

    override fun toEntity(model: TravelDetail): TravelDetailDto {
        return when (model) {
            TravelDetail.BookMark       -> TravelDetailDto(model.viewType, "", "")
            is TravelDetail.Cover       -> TravelDetailDto(model.viewType, model.title, model.cover)
            is TravelDetail.Description -> TravelDetailDto(model.viewType, model.description, "")
            is TravelDetail.Image       -> TravelDetailDto(model.viewType, "", model.imageUrl)
            is TravelDetail.Link        -> TravelDetailDto(model.viewType, "", model.url)
            is TravelDetail.Video       -> TravelDetailDto(model.viewType, "", model.video)
        }
    }


}




