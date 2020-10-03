package com.epoint.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 冯金星
 *
 */
public class StumarkInfo {
	/**
	 * 	成绩编号
	 */
	private String markid;
	/**
	 * 	学号
	 */
	private String stuid;
	/**
	 * 	课程名称
	 */
	private String coursename;
	/**
	 * 	平时成绩
	 */
	private BigDecimal basescore;
	/**
	 * 	考核成绩
	 */
	private BigDecimal testscore;
	/**
	 * 	最终成绩
	 */
	private BigDecimal finalscore;
	/**
	 * 	添加日期
	 */
	private Date adddate;
	/**
	 * 	备注
	 */
	private String remarks;
	/**
	 * 	姓名
	 */
	private String stuname;
	
	@Override
	public String toString() {
		return "StumarkInfo [markid=" + markid + ", stuid=" + stuid + ", coursename=" + coursename + ", basescore="
				+ basescore + ", testscore=" + testscore + ", finalscore=" + finalscore + ", adddate=" + adddate
				+ ", remarks=" + remarks + ", stuname=" + stuname + "]";
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getMarkid() {
		return markid;
	}
	public void setMarkid(String markid) {
		this.markid = markid;
	}
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public BigDecimal getBasescore() {
		return basescore;
	}
	public void setBasescore(BigDecimal basescore) {
		this.basescore = basescore;
	}
	public BigDecimal getTestscore() {
		return testscore;
	}
	public void setTestscore(BigDecimal testscore) {
		this.testscore = testscore;
	}
	public BigDecimal getFinalscore() {
		return finalscore;
	}
	public void setFinalscore(BigDecimal finalscore) {
		this.finalscore = finalscore;
	}
	public Date getAdddate() {
		return adddate;
	}
	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
