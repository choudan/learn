/**
 * 
 */
package httpresult.bean;

/**
 * @author Administrator
 * 
 */
public class Result {
	private int statusCode;
	private int valid;
	private int dataType;
	private String data;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [statusCode=" + statusCode + ", valid=" + valid
				+ ", dataType=" + dataType + ", data=" + data + "]";
	}
}
