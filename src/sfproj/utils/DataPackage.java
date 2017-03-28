package sfproj.utils;

public class DataPackage {

	private Object[] data;

	public DataPackage(Object... data) {
		this.data = data;
	}

	Object getData(int index) {
		return data[index];
	}
}
