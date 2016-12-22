package app.com.hawker.dao;

import java.io.Serializable;

public class Customer implements Serializable {

	private String customerId;
	private String name;
	private String customerCode;
	private String mobileNum;
	private String hawkerCode;
	private String lineNum;
	private String houseSeq;
	private String oldHouseNum;
	private String newHouseNum;
	private String addrLine1;
	private String addrLine2;
	private String locality;
	private String city;
	private String state;
	private String profile1;
	private String profile2;
	private String profile3;
	private String initials;
	private String employment;
	private String comments;
	private String buildingStreet;
	private String totalDue;
	private String hawkerId;
	private String lineId;
	public Customer()
	{

	}
	public Customer(String customerId, String customerCode, String name, String mobileNum, String hawkerCode, String lineNum,
			String houseSeq, String oldHouseNum, String newHouseNum, String addrLine1, String addrLine2, String locality,
			String city, String state, String profile1, String profile2, String profile3, String initials,
			String employment, String comments, String buildingStreet, String totalDue,String hawkerId, String lineId) {
		setCustomerCode(customerCode);
		setCustomerId(customerId);
		setName(name.toLowerCase());
		setMobileNum(mobileNum);
		setHawkerCode(hawkerCode);
		setLineNum(lineNum);
		setHouseSeq(houseSeq);
		setOldHouseNum(oldHouseNum);
		setNewHouseNum(newHouseNum);
		setAddrLine1(addrLine1);
		setAddrLine2(addrLine2);
		setLocality(locality);
		setCity(city);
		setState(state);
		setProfile1(profile1);
		setProfile2(profile2);
		setProfile3(profile3);
		setInitials(initials);
		setEmployment(employment);
		setComments(comments);
		setBuildingStreet(buildingStreet);
		setTotalDue(totalDue);
		setHawkerId(hawkerId);
		setLineId(lineId);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
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

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public String getHouseSeq() {
		return houseSeq;
	}

	public void setHouseSeq(String houseSeq) {
		this.houseSeq = houseSeq;
	}

	public String getOldHouseNum() {
		return oldHouseNum;
	}

	public void setOldHouseNum(String oldHouseNum) {
		this.oldHouseNum = oldHouseNum;
	}

	public String getNewHouseNum() {
		return newHouseNum;
	}

	public void setNewHouseNum(String newHouseNum) {
		this.newHouseNum = newHouseNum;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public void setComments(String comments) {
		this.comments = comments;

	}

	public String getEmployment() {
		return this.employment;
	}

	public String getBuildingStreet() {
		return this.buildingStreet;
	}

	public String getComments() {
		return this.comments;

	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}

	public String getTotalDue() {

		return this.totalDue;
	}
	

	public String getHawkerId() {
		return hawkerId;
	}

	public void setHawkerId(String hawkerId) {
		this.hawkerId = hawkerId;
	}
	

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
