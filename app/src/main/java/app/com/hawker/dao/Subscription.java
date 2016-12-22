package app.com.hawker.dao;

public class Subscription {
	Long subscriptionId;
	Long customerId;
	Long productId;
	String productName;
	String productType;
	String paymentType;
	Double cost;
	Double serviceCharge;
	String frequency;
	String subscriptionType;
	String dow;
	String status;
	String startDate;
	String pausedDate;
	String productCode;
	String stopDate;
	Integer offerMonths;
	String duration;
	String subNumber;
	String resumeDate;
	String pausedProp;
	String activeProp;
	Double addToBill;

	public Subscription(Long subscriptionId, Long customerId, Long productId, String productName, String productType,
			String paymentType, Double cost, Double serviceCharge, String frequency, String subscriptionType,
			String dow, String status, String startDate, String pausedDate, String productCode,
			String stopDate, String duration, Integer offerMonths, String subNumber, String resumeDate, Double addToBill) {
		setSubscriptionId(subscriptionId);
		setCustomerId(customerId);
		setProductId(productId);
		setProductName(productName);
		setProductType(productType);
		setPaymentType(paymentType);
		setCost(cost);
		setServiceCharge(serviceCharge);
		setFrequency(frequency);
		setSubscriptionType(subscriptionType);
		setDow(dow);
		setStatus(status);
		setStartDate(startDate);
		setPausedDate(pausedDate);
		setProductCode(productCode);
		setStopDate(stopDate);
		setDuration(duration);
		setOfferMonths(offerMonths);
		setSubNumber(subNumber);
		setResumeDate(resumeDate);
		setAddToBill(addToBill);
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		this.pausedProp = Boolean.toString(status.equalsIgnoreCase("Stopped"));
		this.activeProp = Boolean.toString(!status.equalsIgnoreCase("Stopped"));
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPausedDate() {
		return pausedDate;
	}

	public void setPausedDate(String pausedDate) {
		this.pausedDate = pausedDate;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setOfferMonths(Integer offerMonths) {
		this.offerMonths = offerMonths;

	}

	public void setDuration(String duration) {
		this.duration = duration;

	}

	public String getDuration() {
		return duration;
	}

	public String getSubNumber() {
		return subNumber;
	}

	public void setSubNumber(String subNumber) {
		this.subNumber = subNumber;

	}

	public String getStopDate() {
		return stopDate;
	}

	public Integer getOfferMonths() {
		return offerMonths;
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
	

	public Double getAddToBill() {
		return addToBill;
	}

	public void setAddToBill(Double addToBill) {
		this.addToBill = addToBill;
	}

}