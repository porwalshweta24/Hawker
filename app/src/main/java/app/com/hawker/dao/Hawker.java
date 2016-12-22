package app.com.hawker.dao;

public class Hawker {

	private Long hawkerId;
	private String name;
	private String hawkerCode;
	private String mobileNum;
	private String agencyName;
	private String activeFlag;
	private Double fee;
	private String oldHouseNum;
	private String newHouseNum;
	private String addrLine1;
	private String addrLine2;
	private String locality;
	private String city;
	private String state;
	private String customerAccess;
	private String billingAccess;
	private String lineInfoAccess;
	private String lineDistAccess;
	private String pausedCustAccess;
	private String productAccess;
	private String reportsAccess;
	private Double totalDue;
	private String profile1;
	private String profile2;
	private String profile3;
	private String password;
	private String initials;
	private String employment;
	private String comments;
	private String pointName;
	private String buildingStreet;
	private String bankAcNo;
	private String bankName;
	private String ifscCode;
	private String stopHistoryAccess;

	public Hawker(Long hawkerId, String name, String hawkerCode, String moblieNum, String agencyName,
			String activeFlag, Double fee, String oldHouseNum, String newHouseNum, String addrLine1, String addrLine2,
			String locality, String city, String state, String customerAccess, String billingAccess,
			String lineInfoAccess, String lineDistAccess, String pausedCustAccess, String productAccess,
			String reportsAccess, Double totalDue, String profile1, String profile2, String profile3, String initials, String password,
			String employment, String comments, String pointName, String buildingStreet, String bankAcNo,
			String bankName, String ifscCode, String stopHistoryAccess) {
		super();
		setHawkerId(hawkerId);
		setName(name);
		setHawkerCode(hawkerCode);
		setMobileNum(moblieNum);
		setAgencyName(("" + agencyName));
		setActiveFlag(activeFlag);
		setFee(fee);
		setOldHouseNum(oldHouseNum);
		setNewHouseNum(newHouseNum);
		setAddrLine1(addrLine1);
		setAddrLine2(addrLine2);
		setLocality(locality);
		setCity(city);
		setState(state);
		setCustomerAccess(customerAccess);
		setBillingAccess(billingAccess);
		setLineInfoAccess(lineInfoAccess);
		setLineDistAccess(lineDistAccess);
		setPausedCustAccess(pausedCustAccess);
		setProductAccess(productAccess);
		setReportsAccess(reportsAccess);
		setTotalDue(totalDue);
		setProfile1(profile1);
		setProfile2(profile2);
		setProfile3(profile3);
		setInitials(initials);
		setPassword(password);
		setEmployment(employment);
		setComments(comments);
		setPointName(pointName);
		setBuildingStreet(buildingStreet);
		setBankAcNo(bankAcNo);
		setBankName(bankName);
		setIfscCode(ifscCode);
		setStopHistoryAccess(stopHistoryAccess);
	}

	public Hawker(Hawker hawkerRow) {
		setHawkerId(hawkerRow.getHawkerId());
		setName(hawkerRow.getName());
		setMobileNum(hawkerRow.getMobileNum());
		setHawkerCode(hawkerRow.getHawkerCode());
		setOldHouseNum(hawkerRow.getOldHouseNum());
		setNewHouseNum(hawkerRow.getNewHouseNum());
		setAddrLine1(hawkerRow.getAddrLine1());
		setAddrLine2(hawkerRow.getAddrLine2());
		setLocality(hawkerRow.getLocality());
		setCity(hawkerRow.getCity());
		setState(hawkerRow.getState());
		setActiveFlag(hawkerRow.getActiveFlag());
		setFee(hawkerRow.getFee());
		setAgencyName(hawkerRow.getAgencyName());
		setCustomerAccess(hawkerRow.getCustomerAccess());
		setBillingAccess(hawkerRow.getBillingAccess());
		setLineInfoAccess(hawkerRow.getLineInfoAccess());
		setLineDistAccess(hawkerRow.getLineDistAccess());
		setPausedCustAccess(hawkerRow.getPausedCustAccess());
		setProductAccess(hawkerRow.getProductAccess());
		setReportsAccess(hawkerRow.getReportsAccess());
		setStopHistoryAccess(hawkerRow.getStopHistoryAccess());
		setInitials(hawkerRow.getInitials());
		setTotalDue(hawkerRow.getTotalDue());
		setProfile1(hawkerRow.getProfile1());
		setProfile2(hawkerRow.getProfile2());
		setProfile3(hawkerRow.getProfile3());
		setPassword(hawkerRow.getPassword());
		setEmployment(hawkerRow.getEmployment());
		setComments(hawkerRow.getComments());
		setPointName(hawkerRow.getPointName());
		setBuildingStreet(hawkerRow.getBuildingStreet());

		setBankAcNo(hawkerRow.getBankAcNo());
		setBankName(hawkerRow.getBankName());
		setIfscCode(hawkerRow.getIfscCode());
	}

	public Long getHawkerId() {
		return hawkerId;
	}

	public String getName() {
		return name;
	}

	public String getHawkerCode() {
		return hawkerCode;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public Double getFee() {
		return fee;
	}

	public String getOldHouseNum() {
		return oldHouseNum;
	}

	public String getNewHouseNum() {
		return newHouseNum;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public String getLocality() {
		return locality;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPassword() {
		return password;
	}

	public void setHawkerId(Long hawkerId) {
		this.hawkerId = hawkerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHawkerCode(String hawkerCode) {
		this.hawkerCode = hawkerCode;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public void setOldHouseNum(String oldHouseNum) {
		this.oldHouseNum = oldHouseNum;
	}

	public void setNewHouseNum(String newHouseNum) {
		this.newHouseNum = newHouseNum;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustomerAccess() {
		return customerAccess;
	}

	public void setCustomerAccess(String customerAccess) {
		this.customerAccess = customerAccess;
	}

	public String getBillingAccess() {
		return billingAccess;
	}

	public void setBillingAccess(String billingAccess) {
		this.billingAccess = billingAccess;
	}

	public String getLineInfoAccess() {
		return lineInfoAccess;
	}

	public void setLineInfoAccess(String lineInfoAccess) {
		this.lineInfoAccess = lineInfoAccess;
	}

	public String getLineDistAccess() {
		return lineDistAccess;
	}

	public void setLineDistAccess(String lineDistAccess) {
		this.lineDistAccess = lineDistAccess;
	}

	public String getPausedCustAccess() {
		return pausedCustAccess;
	}

	public void setPausedCustAccess(String pausedCustAccess) {
		this.pausedCustAccess = pausedCustAccess;
	}

	public String getProductAccess() {
		return productAccess;
	}

	public void setProductAccess(String productAccess) {
		this.productAccess = productAccess;
	}

	public String getReportsAccess() {
		return reportsAccess;
	}

	public void setReportsAccess(String reportsAccess) {
		this.reportsAccess = reportsAccess;
	}

	public Double getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(Double totalDue) {
		this.totalDue = totalDue;
	}

	public String getProfile1() {
		return profile1;
	}

	public void setProfile1(String profile1) {
		this.profile1 = profile1;
	}

	public String getProfile2() {
		return profile2;
	}

	public void setProfile2(String profile2) {
		this.profile2 = profile2;
	}

	public String getProfile3() {
		return profile3;
	}

	public void setProfile3(String profile3) {
		this.profile3 = profile3;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public void setBuildingStreet(String buildingStreet) {
		this.buildingStreet = buildingStreet;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public void setComments(String comments) {
		this.comments = comments;

	}

	public String getEmployment() {
		return this.employment;
	}

	public String getBuildingStreet() {
		return this.buildingStreet;
	}

	public String getPointName() {
		return this.pointName;
	}

	public String getComments() {
		return this.comments;

	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String toString() {
		return getHawkerCode();
	}

	public String isActive() {
		return activeFlag;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;

	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;

	}

	public String getIfscCode() {

		return this.ifscCode;
	}

	public String getBankName() {

		return this.bankName;
	}

	public String getBankAcNo() {

		return this.bankAcNo;
	}

	public String getStopHistoryAccess() {
		return stopHistoryAccess;
	}

	public void setStopHistoryAccess(String stopHistoryAccess) {
		this.stopHistoryAccess = stopHistoryAccess;
	}


}
