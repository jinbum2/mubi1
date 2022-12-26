package utils;

public class ConvertUtil {

	public static int convertString2Int(String target) {
		return target == null || "".equals(target) ? 0 : Integer.parseInt(target);
	}
}
