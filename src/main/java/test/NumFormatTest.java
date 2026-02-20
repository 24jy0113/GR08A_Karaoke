package test;

public class NumFormatTest {

	public static void main(String[] args) {
		String num = "10000001";
		System.out.println(String.format("%06d", Integer.parseInt(num.trim())));
	}

}
