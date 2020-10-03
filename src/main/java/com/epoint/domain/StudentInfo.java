package com.epoint.domain;

import java.util.Date;
/**
 * 
 * @author 冯金星
 *
 */
public class StudentInfo {

	/**
	 * 	学号
	 */
	private String stuid;
	/**
	 * 	姓名
	 */
	private String stuname;
	/**
	 * 	专业
	 */
	private String profession;
	/**
	 * 	入学日期
	 */
	private Date adddate;
	/**
	 * 	性别
	 */
	private Integer sex;
	/**
	 * 	身份证号
	 */
	private String idnum;
	/**
	 * 	总学分
	 */
	private int totalscore;
	/**
	 * 	其他说明
	 */
	private String notes;

	@Override
	public String toString() {
		return "StudentInfo [stuid=" + stuid + ", stuname=" + stuname + ", profession=" + profession + ", adddate="
				+ adddate + ", sex=" + sex + ", idnum=" + idnum + ", totalscore=" + totalscore + ", notes=" + notes
				+ "]";
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
