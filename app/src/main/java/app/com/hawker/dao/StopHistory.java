package app.com.hawker.dao;

public class StopHistory {

	private Long stopHistoryId;
	private String customerName;
	private Long customerCode;
	private String mobileNum;
	private String hawkerCode;
	private Integer lineNum;
	private Long subscriptionId;
	private Integer houseSeq;
	private String productName;
	private String productCode;
	private String billCategory;
	private String subscriptionType;
	private String subscriptionFreq;
	private String subscriptionDOW;
	private String stopDate;
	private String resumeDate;
	private Double amount;

	public StopHistory(Long stopHistoryId, String customerName, Long customerCode, String mobileNum, String hawkerCode,
			Integer lineNum, Long subscriptionId, Integer houseSeq, String productName, String productCode,
			String billCategory, String stopDate, String resumeDate, String subscriptionType,
			String subscriptionFreq, String subscriptionDOW, Double amount) {
		setStopHistoryId(stopHistoryId);
		setCustomerName(customerName);
		setCustomerCode(customerCode);
		setMobileNum(mobileNum);
		setHawkerCode(hawkerCode);
		setLineNum(lineNum);
		setSubscriptionId(subscriptionId);
		setHouseSeq(houseSeq);
		setProductName(productName);
		setProductCode(productCode);
		setBillCategory(billCategory);
		setStopDate(stopDate);
		setResumeDate(resumeDate);
		setSubscriptionType(subscriptionType);
		setSubscriptionFreq(subscriptionFreq);
		setSubscriptionDOW(subscriptionDOW);
		setAmount(amount);
	}

	public Long getStopHistoryId() {
		return stopHistoryId;
	}

	private void setStopHistoryId(Long stopHistoryId) {
		this.stopHistoryId = stopHistoryId;

	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(Long customerCode) {
		this.customerCode = customerCode;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getHawkerCode() {
		return hawkerCode;
	}

	public void setHawkerCode(String hawkerCode) {
		this.hawkerCode = hawkerCode;
	}

	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getHouseSeq() {
		return houseSeq;
	}

	public void setHouseSeq(Integer houseSeq) {
		this.houseSeq = houseSeq;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setBillCategory(String city) {
		this.billCategory = city;

	}

	public String getBillCategory() {
		return billCategory;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;

	}

	public String getResumeDate() {
		return resumeDate;
	}

	public void setResumeDate(String resumeDate) {
		this.resumeDate = resumeDate;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getSubscriptionFreq() {
		return subscriptionFreq;
	}

	public void setSubscriptionFreq(String subscriptionFreq) {
		this.subscriptionFreq = subscriptionFreq;
	}

	public String getSubscriptionDOW() {
		return subscriptionDOW;
	}

	public void setSubscriptionDOW(String subscriptionDOW) {
		this.subscriptionDOW = subscriptionDOW;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount() {

		return this.amount;
	}
}