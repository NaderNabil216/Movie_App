package com.youxel.core.data.restful

object Config {
    const val BASE_URL = "http://40.114.214.132:82/api/"

    const val ID = "id"
    const val PATH_ID = "{$ID}"
    const val REQUEST_ID = "requestId"
    const val STORAGE_TYPE = "storageType"
    const val PARENT_SERVICE_ID = "parentServiceId"
    const val IS_PUBLIC = "isPublic"
    const val DATA_SOURCE_URL = "dataSourceUrl"
    const val PARENT_CATEGORY_ID = "parentCategoryId"
    const val KEYWORD = "keyword"
    const val EMAIL = "email"
    const val REGISTRATION_ID = "RegistrationId"
    const val DEVICE_ID = "DeviceId"
    const val PNS = "pns"
    const val PAGE_NUMBER = "PageNumber"
    const val PAGE_NUM = "PageNum"
    const val PAGE_SIZE = "PageSize"

    const val PATH_REQUEST_ID = "{$REQUEST_ID}"
    const val PATH_STORAGE_TYPE = "{$STORAGE_TYPE}"
    const val PATH_PARENT_SERVICE_ID = "{$PARENT_SERVICE_ID}"
    const val PATH_LIST_OF_VALUES_DATA_SOURCE_URL = "{$DATA_SOURCE_URL}"


    const val NEWS = "News/Portal/Filter"
    const val PINNED_NEWS = "News/FeaturedNews"
    const val BOOKMARKED_NEWS = "News/GetBookmarkedNews"
    const val NEWS_DETAILS = "News/"
    const val CHANGE_FAVORITE_NEWS_STATUS = "News/BookmarkNews/"

    const val ANNOUNCEMENT = "Announcement/Filter"
    const val ANNOUNCEMENT_DETAILS = "Announcement/"
    const val BOOKMARKED_ANNOUNCEMENT = "Announcement/GetBookmarkedAnnouncment"
    const val CHANGE_FAVORITE_ANNOUNCEMENT_STATUS = "Announcement/BookmarkAnnouncement/"

    const val MEDIA_LIST = "Studio/Portal"
    const val MEDIA_DETAILS = "Studio/"

    const val INTERNAL_NEWS = "InternalNews"
    const val BOOKMARKED_INTERNAL_NEWS = "InternalNews/GetBookmarkedNews"
    const val INTERNAL_NEWS_DETAILS = "InternalNews".plus("/").plus(PATH_ID)
    const val CHANGE_INTERNAL_FAVORITE_NEWS_STATUS = "InternalNews/BookmarkNews/".plus(PATH_ID)

    const val EVENT_LIST = "Event/Search"
    const val EVENT_DETAILS = "Event/Get/"
    const val EVENT_ADD_TO_CALENDAR = "Event/addtocalendar/"

    // global search
    const val GLOBAL_SEARCH = "Engine/SearchWithCount"

    const val OFFERS_LIST = "Offers/HomeOffersList"
    const val OFFER_DETAILS = "Offers/{id}"
    const val BOOKMARK_OFFER = "Offers/BookmarkOffer/"
    const val OFFERS_CATEGORIES = "Offers/HomeOfferCategories"
    const val OFFERS_FILTER_CATEGORIES = "Lookups/Categories/offers"
    const val USER_PROFILE = "UserProfile/User/me"
    const val PAYROLL_INFO = "UserProfileDetails/GetPayrollInfo"
    const val CONTACT_ADDRESS = "UserProfileDetails/GetContactAndAddress"
    const val EMERGENCY_CONTACT = "UserProfileDetails/GetEmergencyContact"
    const val CERTIFICATES = "UserProfileDetails/GetCertificates"
    const val DEPENDANTS = "UserProfileDetails/GetDependents"
    const val EDUCATION = "UserProfileDetails/GetEducationHistory"
    const val WORK_EXPERIENCE = "UserProfileDetails/GetWorkExperience"
    const val EMPLOYMENT_DETAILS = "UserProfileDetails/EmploymentDetails"

    const val LOGIN = "login"
    const val OTP_TOKEN = "token"
    const val REFRESH_TOKEN = "token/refresh"
    const val LOGIN_CERQEL = "Token/Login"

    const val SERVICE_CATEGORIES = "Categories/GetAll"
    const val SERVICE_SUBCATEGORIES = "Categories/GetBySubCategory"
    const val SUBSERVICES =
        "SelfServices/GetSubServicesByServiceId".plus("/").plus(PATH_PARENT_SERVICE_ID)
    const val GET_ALL_SERVICES = "SelfServices/GetAll"
    const val FAVORITE_SERVICES = "SelfServices/GetUserFavoriteServices"
    const val ADD_FAVORITE_SERVICE = "UserServices/Add"

    const val SERVICES_SEARCH = "SelfServices/Search"
    const val EMPLOYEE_SEARCH = "Users/GetUsers"

    const val EMPLOYEE_PROFILE_VIEW = "UserProfile/User/GetUserProfileFromAd/{email}"

    const val MY_TASKS = "CamundaTasks/GetAllPaged"
    const val TASK_DETAILS = "CamundaTasks/GetById".plus("/").plus(PATH_ID)
    const val EXECUTE_ACTION = "CamundaTasks/ExecuteAction"

    const val MY_REQUESTS = "CamundaRequest/GetAllPaged"
    const val REQUEST_SERVICE = "SelfServices/RequestService".plus("/").plus(PATH_ID)
    const val INITIATE_REQUEST = "CamundaRequest/InitiateRequest".plus("/").plus(PATH_ID)
    const val REOPEN_REQUEST = "CamundaRequest/ReopenRequest"
    const val GET_REQUEST_BY_ID_STATUS = "CamundaRequest/GetByStatusId".plus("/").plus(PATH_ID)
    const val GET_TASK_BY_ID_STATUS = "CamundaTasks/GetUserTasksAsync".plus("/").plus(PATH_ID)
    const val MY_REQUESTS_STATISTICS = "CamundaRequest/MyRequestsStatistics"
    const val MY_TASKS_STATISTICS = "CamundaTasks/MyTasksStatistics"

    const val REQUEST_DETAILS = "CamundaRequest/GetById".plus("/").plus(PATH_ID)
    const val REQUEST_COMMENTS = "RequestComments/GetByRequestId".plus("/").plus(PATH_REQUEST_ID)
    const val ADD_MESSAGE = "RequestComments/Add"
    const val WITHDRAW_REQUEST = "CamundaRequest/WithdrawRequest"

    const val GET_NOTIFICATIONS_LIST = "InAppNotification/GetNotificationList"
    const val SET_NOTIFICATION_AS_OPEN =
        "InAppNotification/SetNotificationAsOpen".plus("/").plus(PATH_ID)
    const val UnOpendNotificationCount = "InAppNotification/UnOpendNotificationCount"

    const val UPLOAD_FILE = "FileManager/UploadFiles".plus("/").plus(PATH_STORAGE_TYPE)

    // FAQs
    const val FAQS = "FAQ/Portal/GetAll"
    const val FAQS_CATEGORIES = "Lookups/Categories/FAQ"

    // Knowledge Base
    const val KNOWLEDGE_BASE = "KnowledgeBase/Portal/GetAll"
    const val KNOWLEDGE_BASE_CATEGORIES = "Lookups/Categories/KnowledgeBase"

    // register Unregister device

    const val REGISTER_DEVICE = "NotificationRegister/register-device"
    const val UNREGISTER_DEVICE = "NotificationRegister/unregister-device"

    const val MY_APPLICATION = "Applications/Portal"
    const val MY_APPLICATION_RECENT_USED = "Applications/RecentlyUsed"
    const val MY_APPLICATION_ADD_TO_HISTORY = "Applications/AddToHistory"

    const val CALENDAR_UPCOMING = "Calendar/UpComing"
    const val CALENDAR = "Calendar"
    const val GET_EVENT_BY_ID_CALENDAR = "Calendar/{id}"
    const val DELETE_CALENDAR = "Calendar/DeleteEvent"
    const val ADD_CALENDAR = "Calendar/Add"
    const val EDIT_CALENDAR = "Calendar/Edit"
    const val GET_USERS_TO_INVITE_CALENDAR = "Calendar/GetUsersToInvite"
    const val DECLINE_EVENT_CALENDAR = "Calendar/DeclineEvent/{id}"
    const val SEARCH_KEYWORD = "searchKeyword"

    // THEME
    const val APP_THEME = "Themes/GetAppliedTheme"

}