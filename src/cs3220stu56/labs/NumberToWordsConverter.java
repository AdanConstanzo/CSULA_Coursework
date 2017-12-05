package cs3220stu56.labs;

public class NumberToWordsConverter {

	public static final String[] units = {"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
	public static final String[] tens = {"","","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};

	public static String changeNumber(final int n) {
		if (n < 0) {
			return "Minus " + changeNumber(-n);
		}
		if (n < 20) {
			return units[n];
		}
		if (n < 100) {
			return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
		}
		if (n < 1000) {
			return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + changeNumber(n % 100);
		}
		if (n < 100000) {
			return changeNumber(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + changeNumber(n % 1000);
		}
		if (n < 10000000) {
			return changeNumber(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + changeNumber(n % 100000);
		}
		return changeNumber(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + changeNumber(n % 10000000);
	}
	String number;
	public NumberToWordsConverter(int a){
		this.number = changeNumber(a);
	}
	public String getStringValue(){
		return number;
	}
}
