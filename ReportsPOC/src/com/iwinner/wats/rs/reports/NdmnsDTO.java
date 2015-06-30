package com.iwinner.wats.rs.reports;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class NdmnsDTO {

	private Integer spotifyID;
	private String msisdn;
	private Date processDate;
	private Timestamp processTime;
	private String opId;
	private String methodId;
	private String returnMessage;
	private String returnCode;
	private String clientId;
	private String entireLine;
	private Date updateDate;
	private Timestamp updateTime;

	public Integer getSpotifyID() {
		return spotifyID;
	}

	public void setSpotifyID(Integer spotifyID) {
		this.spotifyID = spotifyID;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@XmlJavaTypeAdapter(TimestampAdapter.class)
	public Timestamp getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Timestamp processTime) {
		this.processTime = processTime;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEntireLine() {
		return entireLine;
	}

	public void setEntireLine(String entireLine) {
		this.entireLine = entireLine;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@XmlJavaTypeAdapter(TimestampAdapter.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "NdmnsDTO [spotifyID=" + spotifyID + ", msisdn=" + msisdn
				+ ", processDate=" + processDate + ", processTime="
				+ processTime + ", opId=" + opId + ", methodId=" + methodId
				+ ", returnMessage=" + returnMessage + ", returnCode="
				+ returnCode + ", clientId=" + clientId + ", entireLine="
				+ entireLine + ", updateDate=" + updateDate + ", updateTime="
				+ updateTime + "]";
	}

}
