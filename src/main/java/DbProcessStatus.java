/**
 * An generic object that contains information about the db process status, message and data.
 * Allows to return back any type of data
 *
 * Contains:
 * boolean statusSuccess- Indicating the success of the operation
 * String statusMessage - The message of the operation such as what part of the db process failed. Can be used to
 * display to the user
 * T data - A generic data container that allows any data to be contained. Typically used to return data from operation
 * @param <T>
 */
public class DbProcessStatus<T> {

    private boolean statusSuccess;
    private String statusMessage;
    private T data;

    private DbProcessStatus(boolean statusSuccess, String statusMessage, T data) {
        this.statusSuccess = statusSuccess;
        this.statusMessage = statusMessage;
        this.data = data;
    }
//    public DbProcessStatus()
//    {
//        statusSuccess = false;
//        statusMessage = "Error encountered";
//        data = null;
//    };

    /**
     * Used to raise error. To set status success to false
     * @param errorMessage
     */
    public static DbProcessStatus RaiseError(String errorMessage)
    {
        DbProcessStatus status = new DbProcessStatus(false, errorMessage, null);
        return status;
    }
    public static <T> DbProcessStatus RaiseSuccess(String successMessage, T data)
    {
        DbProcessStatus status = new DbProcessStatus(true, successMessage, data);
        return status;
    }

    public boolean isStatusSuccess() {
        return statusSuccess;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public T getData() {
        return data;
    }
}
