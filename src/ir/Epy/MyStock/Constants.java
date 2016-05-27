package ir.Epy.MyStock;

/**
 * Created customer_id py4_ on 2/17/16.
 */
public class Constants {

    public class Names {
        public static final String stock = "سهام";
        public static final String quantity = "تعداد";
        public static final String base_price = "قیمت پایه";
    }
    public static final String CustomerAddedMessage = "کاربر جدید اضافه شد";
    public static final String CustomerExistsMessage = "کاربر وجود دارد";
    public static final String CustomerNotFoundMessage = "کاربر پیدا نشد";
    public static final String MismatchedParametersMessage = "Mismatched parameters";
    public static final String UnknownCommandMessage = "Unknown Command";
    public static final String SimpleSuccessMessage = "Successful";
    public static final String NotEnoughMoneyMessage = "عدم پول کافی";
    public static final String NotEnoughShareMessage = "عدم سهام کافی";
    public static final String SymbolNotFoundMessage = "سهام یافت نشد";
    public static final String SymbolAlreadyExistsMessage = "سهام وجود دارد";
    public static final String SymbolAddedMessage = "سهام ساخته شد";
    public static final String SymbolUpdatedMessage = "سهام به روز شد";
    public static final String InvalidTypeMessage = "Invalid type";
    public static final String OrderIsQueuedMessage = "درخواست وارد صف شد";
    public static final String OrderDeclinedMessage = "درخواست رد شد";
    public static final String CreditRequestAddedMessage = "درخواست وارد صف شد";
    public static final String InvalidCreditValueMessage = "مقدار نامعتبر برای درخواست";
    public static final String CreditRequestNotFoundMessage = "درخواست پیدا نشد";
    public static final String CreditRequestProcessedMessage = "درخواست پردازش شد";
    public static final String NotValidRoleMessage = "نقش نامعتبر";
    public static final String CSVLogFailedMessage = "نتونستم لاگ کنم";
    public static final String SQLExceptionMessage = "یک جا داخل کوئری‌ها گند زدیم";
    public static final String RoleUpdatedMessage = "نقش رو آپدیت کردیم";
    public static final String RoleNotFoundMessage = "نقش پیدا نشد";
    public static final String PendingRequestMessage = "درخواست شما به دلیل حجم تراکنش آن، نیازمند تایید است. لطفا صبوری کنید.";
    public static final String RequestAcceptedMessage = "درخواست با موفقیت تایید شد";
    public static final String RequestRejectedMessage = "درخواست با موفقیت لغو شد";
    public static final String limit = "محدودیت";
    public static final Integer PendingStatus = 0;
    public static final Integer AcceptStatus = 1;
    public static final Integer RejectStatus = 2;
    public static final Integer IOC_ID = -1;
    public static final Integer MPO_ID = -2;
    /*****************************************************************************/
    public static final int DB_EXIT_CODE = 1;
    /*****************************************************************************/

    public static final String ACCOUNTANT_ROLE = "accountant";
    public static final String OWNER_ROLE = "owner";
    public static final String ADMIN_ROLE = "admin";
    public static final String CUSTOMER_ROLE = "customer";

    public static String NotProvided(String str) {
        return str + " فراهم نشده";
    }
    public static String ShouldBeNumeric(String str) { return str + " باید عدد باشد";}
    public static String UpdatedMessage(String str) { return str + " به روز رسانی شد";}
    public static String RefundMessage(String username, Integer amount) { return "مقدار "+amount+" تومن به "+username+" اضافه شد."; }
    public static String ReshareMessage(String username, String symbol, Integer quantity) { return quantity+" تا سهم از "+symbol+" به "+username+" اضافه شد"; }


}
