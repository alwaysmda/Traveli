package data.remote.dto

import com.xodus.traveli.R
import domain.model.Contact
import domain.model.ContactItem
import main.ApplicationClass
import util.Constant
import util.EntityMapper
import javax.inject.Inject

class ContactMapper @Inject constructor(val app: ApplicationClass) : EntityMapper<ContactDto, Contact> {
    override fun toDomainModel(dto: ContactDto): Contact {
        return Contact(
            ContactItem(app.m.phoneNumber, dto.phone, Constant.ContactType.Phone, R.drawable.ic_phone, R.color.md_green_400),
            ContactItem(app.m.emailAddress, dto.email, Constant.ContactType.Email, R.drawable.ic_email, R.color.md_orange_700),
            ContactItem(app.m.twitterUsername, dto.twitter, Constant.ContactType.Twitter, R.drawable.ic_twitter, R.color.md_light_blue_400),
            ContactItem(app.m.instagramUsername, dto.instagram, Constant.ContactType.Instagram, R.drawable.ic_instagram, R.color.md_red_400),
            ContactItem(app.m.websiteUrl, dto.website, Constant.ContactType.Website, R.drawable.ic_website, R.color.md_brown_400),
        )
    }

    override fun toEntity(model: Contact): ContactDto =
        ContactDto(
            model.phone.value,
            model.email.value,
            model.twitter.value,
            model.instagram.value,
            model.website.value,
        )

    fun fromEntityList(dtoList: ArrayList<ContactDto>): ArrayList<Contact> =
        ArrayList(dtoList.map { toDomainModel(it) })
}