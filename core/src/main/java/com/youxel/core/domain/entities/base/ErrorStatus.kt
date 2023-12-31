package  com.youxel.core.domain.entities.base

/**
 * various error status to know what happened if something goes wrong with a repository call
 */
enum class ErrorStatus {

    /**
     * error in connecting to repository (Server or Database)
     */
    NO_CONNECTION,
    /**
     * error in getting value (Json Error, Server Error, etc)
     */
    BAD_RESPONSE,
    /**
     * Time out  error
     */
    TIMEOUT,
    /**
     * no data available in repository
     */
    EMPTY_RESPONSE,
    /**
     * an unexpected error
     */
    NOT_DEFINED,
    /**
     * bad credential
     */
    UNAUTHORIZED,

    /**
     * server returned 500
     */
    INTERNAL_SERVER_ERROR,

    /**
     * unknown host or unreachable
     */
    UNKNOWN_HOST,

    /**
     * something went wrong
     */
    FORRBIDEN,

    /**
     * something went wrong
     */
    NOT_FOUND,

    /**
     *  No Data
     */

    NO_DATA

}