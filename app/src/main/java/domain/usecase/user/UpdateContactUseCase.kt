package domain.usecase.user

import data.remote.DataState
import domain.model.ContactItem
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.Constant
import util.PrefManager
import util.extension.authorize

class UpdateContactUseCase(private val app: ApplicationClass, private val repo: UserRepository, private val prefManager: PrefManager) {
    operator fun invoke(contactItem: ContactItem) = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            //TODO real
            //emit(repo.updateContact(contactItem))
            //
            //TODO fake
            repo.updateContact(contactItem)
            app.user?.let {
                app.user = when (contactItem.type) {
                    Constant.ContactType.Phone     -> it.copy(contact = it.contact.copy(phone = contactItem))
                    Constant.ContactType.Email     -> it.copy(contact = it.contact.copy(email = contactItem))
                    Constant.ContactType.Twitter   -> it.copy(contact = it.contact.copy(twitter = contactItem))
                    Constant.ContactType.Instagram -> it.copy(contact = it.contact.copy(instagram = contactItem))
                    Constant.ContactType.Website   -> it.copy(contact = it.contact.copy(website = contactItem))
                }
            }
            emit(DataState.Success(app.user!!))
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}