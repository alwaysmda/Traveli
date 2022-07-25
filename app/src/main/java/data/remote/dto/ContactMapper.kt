package data.remote.dto

import domain.model.Contact
import main.ApplicationClass
import util.EntityMapper
import javax.inject.Inject

class ContactMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<ContactDto, Contact> {
    override fun toDomainModel(dto: ContactDto): Contact {
        return Contact(
            dto.phone,
            dto.email,
            dto.twitter,
            dto.instagram,
            dto.website,
        )
    }

    override fun toEntity(model: Contact): ContactDto =
        ContactDto(
            model.phone,
            model.email,
            model.twitter,
            model.instagram,
            model.website,
        )

    fun fromEntityList(dtoList: ArrayList<ContactDto>): ArrayList<Contact> =
        ArrayList(dtoList.map { toDomainModel(it) })
}