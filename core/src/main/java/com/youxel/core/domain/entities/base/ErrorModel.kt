package  com.youxel.core.domain.entities.base

/**
 * Default error model that comes from server if something goes wrong with a repository call
 */
data class ErrorModel(
    val message: String?,
    val errorCode: Int?,
    @Transient var errorStatus: ErrorStatus
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus)
    constructor(errorStatus: ErrorStatus, errorCode: Int) : this(null, errorCode, errorStatus)
}