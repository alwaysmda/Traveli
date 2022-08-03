  
=============================================================================================  
========================================= S T O R Y =========================================  
=============================================================================================  
Traveli  
Share and earn with your travel experience.  
Travelers and also natives can share their experiences about a country.  
Users can create free or purchasable packages and each package is assigned to a single country.  
In each package they can add ultimate videos, images, external links and texts in any order.  
Each package also has a title, cover, cities and tags.  
If user is going to sell the package, they should add an overview for their package.  
The overviews' structure is similar to the package itself.  
  
  
  
MVP version:  
•Homepage:  
-Search  
Navigates to search screen.  
  
-Banners  
Top packages, chosen by app admins  
  
-Trending  
Sublist of top packages.  
  
-New  
Sublist of recently published packages.  
  
Tapping on each one of them navigates to travel content.  
It depends if the travel if free or not.  
If it's free, then user can view package content, if not, user can view the package overview and price.  
  
-Countries  
Tapping on each item navigates to search screen and searches the selected country.  
  
••Lists:  
-Contains two lists.  
Bookmarked packages and owned packages.  
  
•••Profile:  
-User information  
Cover, avatar, name, hometown and bio.  
  
-Balance  
It is only visible to current user.  
Tapping on it navigates to transaction list screen.  
User can charge or checkout in transaction screen.  
  
-Travels  
A sublist of user's travels.  
An add button and draft travels is only visible to current user.  
  
-Stats  
A few items that summarises some stats about user.  
It can contain number of countries and cities visited.  
  
-Contact  
Phone, Email, Twitter, Instagram, Website.  
  
  
=============================================================================================  
===================================== S T R U C T U R E =====================================  
=============================================================================================  
MVVM Clean architecture with Actions, Events, Domain layer and UseCases  
  
-Actions  
An interface that listens on the users input and delivers it to view models  
  
-Events  
An interface that orders the view to do something  
  
-Domain layer  
DTOs (Data Transfer Object) are used to retrieve and convert API's response to objects.  
Mappers are used to convert the DTOs to Domain models in repository.  
Domain models are being used through the app.  
Domain models are meant to be in the most efficient state to be used in views.  
  
-UseCases  
Each flow of actions or data is a use case.  
It prevents duplicate code and makes ViewModels simpler.  
Each UseCase has one and only one public method and can have none, one or multiple private methods.   
Sorting, filtering, fetching and validating data is done in use cases.  
UseCases are categorized by usage.  
  
Using:  
-Retrofit2  
Different apis and repositories are separated  
  
-Coil  
There is binding extensions in BindingAdapterUtils  
  
-XML  
  
-Hilt  
  
-DataStore  
PrefManager is responsible to save and load data using DataStore  
  
-Flow  
Used for connection between UseCases and ViewModels and XMLs  
  
-CPP  
Used to hide sensitive strings like base API and tokens  
  
-LanguageManager  
Custom classes that handle the language and font changing function.  
All strings should be referenced from LanguageManager's interface.  
LanguageManager's interface, m, is only available in ApplicationClass, so ApplicationClass should be injected into every class and XML that uses strings.  
  
-ContentWrapper  
A custom view wrapper to handle different view states.  
It has three states.  
Loading that shows a progress bar.  
Failure that shows an error and a retry button.  
Success that shows the content.  
Error message and retry click listener and ApplicationClass should be added through XML attributes.  
It can be developed to view an empty state.  
  
-Data-Binding  
All screens only contain ViewModel variable.  
List items (rows) also contain the model for the specific item.  
Fonts and constant strings are referenced by vm.app.m.someString  
Click listeners are referenced by () -> vm.action.someAction()  
Fonts and image urls should be set using BindingAdapter extension functions.  
Dynamic strings are referenced to a MutableStateFlow in ViewModels using vm.someString  
  
-BottomSheets  
It is being used instead of dialogs.  
There is currently two general types.  
Confirm and Edit sheet.  
  
-Navigation component  
BottomNavigationView with navigation component is used to manage screens.  
For each tab on bottom navigation there is an empty fragment that navigates to the main fragment.  
These tabs are used to provide us the re-usability of the fragments that are being used in tabs.  
For example we can navigate to the settings fragment in home fragment without changing the tab.  
  
-Extensions  
There are multiple useful extension files available.  
AppExtensions is the app specific extensions but the other ones can be used in any project.  
Search before adding a snippet.  
  
App creation flow:  
-Idea  
-Sketch  
-Prototype Version + Api  
-Design + Backend Development  
-Production Version  
  
Best practices:  
-RecyclerViews  
Three types of item is predicted to be used.  
Failure, Loading and Normal view.  
BaseActivity and needed actions is passed to adapters.  
Lists are updated using .submitList.  
Lists should be cloned before passing to fragment.  
Loading or failure state is added to the cloned list in ViewModel using the Add and Fail ids.  
Checkout transaction list implementation for more info.  
  
-ContentWrapper  
It can be wrapped around a list or a content that loads lazily.  
It needs 3 events.  
SetXxxLoading to show loading state.  
SetXxxFailure with a message to show the failure state.  
UpdateXxx with the needed data to update the view to normal state and view data.  
It needs 1 action.  
onRetryXxxClick that passes the retry button click to ViewModel.  
Checkout profile implementation for more info.  
  
-Themes  
All changing colors should be referenced with ?attr/someColor.  
Different styles is defined using colors.xml, styles.xml and attrs.xml.  
Changing theme can be done using ThemeManager.  
  
-Styles  
Use the Button, TextView and ImageView styles defined in styles.xml.  
  
-Logging  
Only use the log extension function available in  LogExt.kt file.  
Make sure to remove the log afterwards.  
Put explicit information in every log.  
  
-Values  
Use pre-defined values for text size, paddings and corner radius in dimen.xml.  
  
-Naming convention  
Except domain models, all other classes should have the type in the end of their names.  
Icons start with ic_ and shapes start with shape_ and large images start with bg_.  
As for other components' views, they start with type name.  
UserAdapter, UserDto, UserApi, UserRepository, UserUseCase, UserUseCases, UserMapper, UserFragment, UserViewModel.  
ic_user, bg_user, shape_user, fragment_user, activity_user, row_user, sheet_user, toolbar_user.  
  
-Bonus  
My own Android Studio settings are exported to project as android_studio_settings.zip.  
Code style and shortcuts are configured.  
Share the same setting in team and make sure to check Reformat Code, Rearrange Code and Optimize Import before commit.  
  
Some useful shortcuts are:  
Command+O -> Close other tabs  
Option+A -> Clone caret above   
Option+Z -> Clone caret below  
Double Option+(hold or tap)Up -> Clone caret above  
Double Option+(hold or tap)Down -> Clone caret below  
Control+C -> Stop application  
Control+X -> Clean  
Control+Z -> Run  
Control+Option+O -> Optimize imports  
Command+Option+L -> Format code  
Command+K -> Commit  
Command+Shift+K -> Push  
Command+Shift+F -> Search in codes  
Double Shift -> Search in files  
Option+Up -> Select more  
Option+Down -> Select less  
Shift+Enter -> Goto next line  
Command+D -> Duplicate line/selected  
Control+Tab -> Change tab  
Control+Command+G -> Find all occurrences in page  
Command+Q -> Magic!  
  
  
  
Happy Coding!  
  