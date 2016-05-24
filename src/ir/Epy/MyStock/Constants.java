package ir.Epy.MyStock;

/**
 * Created customer_id py4_ on 2/17/16.
 */
public class Constants {
    public static final String CustomerAddedMessage = "New user is added";
    public static final String CustomerExistsMessage = "Repeated id";
    public static final String CustomerNotFoundMessage = "Unknown user id";
    public static final String MismatchedParametersMessage = "Mismatched parameters";
    public static final String UnknownCommandMessage = "Unknown Command";
    public static final String SimpleSuccessMessage = "Successful";
    public static final String NotEnoughMoneyMessage = "Not enough money";
    public static final String NotEnoughShareMessage = "Not enough share";
    public static final String SymbolNotFoundMessage = "Invalid symbol id";
    public static final String InvalidTypeMessage = "Invalid type";
    public static final String OrderIsQueuedMessage = "Order is queued";
    public static final String OrderDeclinedMessage = "Order is declined";
    public static final String CreditRequestAddedMessage = "Credit Request queued";
    public static final String InvalidCreditValueMessage = "Invalid value for credit";
    public static final String CreditRequestNotFoundMessage = "Unknown credit request";
    public static final String CreditRequestProcessedMessage = "Credit request processed";
    public static final String CSVLogFailedMessage = "Failed to log to csv file";
    public static final String SQLExceptionMessage = "We messed up somewhere with the SQL...";
    public static final Integer PendingStatus = 0;
    public static final Integer AcceptStatus = 1;
    public static final Integer RejectStatus = 2;
    public static final Integer IOC_ID = -1;
    public static final Integer MPO_ID = -2;
    /*****************************************************************************/
    public static final int DB_EXIT_CODE = 1;
    /*****************************************************************************/
    public static final String USER_ROLE_ADMIN = "admin";
    public static final String USER_ROLE_CUSTOMER = "customer";
}
