package app.com.hawker.dao;

public class LineInfo {
	private String lineId;
	private String lineNum;
	private String hawkerId;
	private String lineNumDist;

	public LineInfo(String lineId, String lineNum, String hawkerId, String lineNumDist) {
		setLineId(lineId);
		setLineNum(lineNum);
		setHawkerId(hawkerId);
		setLineNumDist(lineNumDist);
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public String getHawkerId() {
		return hawkerId;
	}

	public void setHawkerId(String hawkerId) {
		this.hawkerId = hawkerId;
	}

	public String getLineNumDist() {
		return lineNumDist;
	}

	public void setLineNumDist(String lineNumDist) {
		this.lineNumDist = lineNumDist;
	}

	public String toString() {
		return getLineNum() + "";
	}
}
