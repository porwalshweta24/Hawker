package app.com.hawker.dao;

public class BillingLine {
	private String billLineId;
	private String billInvoiceNum;
	private String lineNum;
	private String product;
	private String amount;
	private String teaExpenses;

	public BillingLine(String billLineId, String billInvoiceNum, String lineNum, String product, String amount,
			String teaExpenses) {
		setBillLineId(billLineId);
		setBillInvoiceNum(billInvoiceNum);
		setLineNum(lineNum);
		setAmount(amount);
		setProduct(product);
		setTeaExpenses(teaExpenses);
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setBillLineId(String billLineId) {
		this.billLineId = billLineId;
	}

	public void setBillInvoiceNum(String billInvoiceNum) {
		this.billInvoiceNum = billInvoiceNum;
	}

	public String getBillInvoiceNum() {
		return this.billInvoiceNum;
	}

	public String getBillLineId() {
		return this.billLineId;
	}

	public String getProduct() {
		return this.product;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount() {

		return this.amount;
	}

	public void setTeaExpenses(String teaExpenses) {
		this.teaExpenses = teaExpenses;
	}

	public String getTeaExpenses() {

		return this.teaExpenses;
	}

	public String getLineNum() {

		return this.lineNum;
	}

}