package net.ebaolife.husqvarna.framework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangfeng
 * 
 *         www.jhopesoft.com
 * 
 *         jfok1972@qq.com
 * 
 *         2017-06-01
 * 
 */
public class DataFetchResponseInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer startRow;
	private Integer endRow;
	private int totalRows;
	private List<?> matchingObjects;

	@Override
	public String toString() {
		return "DataFetchResponseInfo [startRow=" + startRow + ", endRow=" + endRow + ", totalRows=" + totalRows
				+ ", matchingObjects=" + matchingObjects + "]";
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<?> getMatchingObjects() {
		return matchingObjects;
	}

	public void setMatchingObjects(List<?> matchingObjects) {
		this.matchingObjects = matchingObjects;
	}

}
