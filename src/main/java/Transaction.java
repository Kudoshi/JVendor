public class Transaction {

    private String transactionId;
    private String transactionItemCode;
    private String transactionDate;
    private float transactionPrice;

    public Transaction(String transactionId, String transactionItemCode, String transactionDate, float transactionPrice) {
        this.transactionId = transactionId;
        this.transactionItemCode = transactionItemCode;
        this.transactionDate = transactionDate;
        this.transactionPrice = transactionPrice;
    }
}
