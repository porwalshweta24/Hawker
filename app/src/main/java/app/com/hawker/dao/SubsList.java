package app.com.hawker.dao;

/**
 * Created by shweta on 11/5/2016.
 */
public class SubsList {
    Integer lineNum = 0;
    Long custCode = 0L;
    String custName;
    Integer seq = 0;
    String addr;
    String prodCode;
    String prodName;
    String paymentType;
    String subType;
    String frequency;
    String dow;
    String prd;
    String hawkerCode;
    public SubsList(Integer lineNum, Long custCode, String custName, Integer seq,
                    String addr, String prodCode, String prodName, String paymentType, String subType, String
                            frequency, String dow, String prd, String hawkerCode) {
        this.lineNum = lineNum;
        this.custCode = custCode;
        this.custName = custName;
        this.seq = seq;
        this.addr = addr;
        this.prodCode = prodCode;
        this.prodName = prodName;
        this.paymentType = paymentType;
        this.subType = subType;
        this.frequency = frequency;
        this.dow = dow;
        this.prd = prd;
        this.hawkerCode = hawkerCode;
    }
    public Integer getLineNum() {
        return lineNum;
    }
    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }
    public Long getCustCode() {
        return custCode;
    }
    public void setCustCode(Long custCode) {
        this.custCode = custCode;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getProdCode() {
        return prodCode;
    }
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getSubType() {
        return subType;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getDow() {
        return dow;
    }
    public void setDow(String dow) {
        this.dow = dow;
    }
    public String getPrd() {
        return prd;
    }
    public void setPrd(String prd) {
        this.prd = prd;
    }
    public String getHawkerCode() {
        return hawkerCode;
    }
    public void setHawkerCode(String hawkerCode) {
        this.hawkerCode = hawkerCode;
    }
}