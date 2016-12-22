package app.com.hawker.dao;

/**
 * Created by admin on 8/12/2016.
 */
public class Billing {

    private String billInvoiceNum;
    private String customerId;
    private String invoiceDate;
    private String pdfURL;
    private String due;
    private String month;

    public Billing(String billInvoiceNum, String customerId, String invoiceDate, String pdfURL, String due, String month) {
        setBillInvoiceNum(billInvoiceNum);
        setCustomerId(customerId);
        setInvoiceDate(invoiceDate);
        setPdfURL(pdfURL);
        setDue(due);
        setMonth(month);
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setBillInvoiceNum(String billInvoiceNum) {
        this.billInvoiceNum = billInvoiceNum;
    }

    public String getBillInvoiceNum() {
        return this.billInvoiceNum;
    }

    public String getPdfURL() {
        return this.pdfURL;
    }
    public String getMonth() {
        return this.month;
    }

    public String getInvoiceDate() {
        return this.invoiceDate;
    }

    public String getDue() {
        return this.due;
    }

    public void setDue(String due) {
        this.due = due;
    }

	public String getCustomerId() {
		return customerId;
	}

}