package net.ebaolife.husqvarna.framework.bean;

import java.io.Serializable;
import java.util.List;

public class PageInfo<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int start = 1;
	private int limit = 20;
	private int total;

	private long spendtime;
	private List<E> data;
	private String spare1;
	private String spare2;
	private String spare3;
	private Object spare4;

	public PageInfo() {
	}

	public PageInfo(int start, int limit) {
		setStart(start);
		setLimit(limit);
	}

	public PageInfo(int start, int limit, int total, List<E> data) {
		setStart(start);
		setLimit(limit);
		setTotal(total);
		setData(data);
	}

	public int getCurpage() {
		return start / limit;
	}

	public int getTotalpage() {
		return (total + limit - 1) / limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public String getSpare1() {
		return spare1;
	}

	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}

	public String getSpare2() {
		return spare2;
	}

	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}

	public String getSpare3() {
		return spare3;
	}

	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}

	public Object getSpare4() {
		return spare4;
	}

	public void setSpare4(Object spare4) {
		this.spare4 = spare4;
	}

	public long getSpendtime() {
		return spendtime;
	}

	public void setSpendtime(long spendtime) {
		this.spendtime = spendtime;
	}
}
