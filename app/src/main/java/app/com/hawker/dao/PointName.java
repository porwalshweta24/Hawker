package app.com.hawker.dao;

import java.sql.Connection;

/**
 * Created by shweta on 11/5/2016.
 */
public class PointName {
    private String pointName = new String();
    private String city = new String();
    private Long pointId=0L;
    private String billCategory = new String();
    private Double fee=0.0;
//    public static Connection con=ConnectionUtils.getConnection();
    public PointName(Long pointId, String pointName, String city, String billCategory,
                     double fee) {
        setPointId(pointId);
        setPointName(pointName);
        setCity(city);
        setBillCategory(billCategory);
        setFee(fee);
    }
    public void setPointId(Long pointId) {
        this.pointId=pointId;
    }
    public void setPointName(String pointName) {
        this.pointName=pointName;
    }
    public void setCity(String city) {
        this.city=city;
    }
    public Long getPointId() {
        return this.pointId;
    }
    public String getPointName() {
        return this.pointName;
    }
    public String getCity() {
        return this.city;
    }
    public void setBillCategory(String billCategory) {
        this.billCategory=billCategory;
    }
    public String getBillCategory() {
        return this.billCategory;
    }
    public double getFee() {
        return this.fee;
    }
    public void setFee(double fee) {
        this.fee=fee;
    }
}