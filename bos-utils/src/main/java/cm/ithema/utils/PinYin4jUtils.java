package cm.ithema.utils;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYin4jUtils {
	/**
	 * 灏嗗瓧绗︿覆杞崲鎴愭嫾闊虫暟缁�
	 * 
	 * @param src
	 * @return
	 */
	public static String[] stringToPinyin(String src) {
		return stringToPinyin(src, false, null);
	}

	/**
	 * 灏嗗瓧绗︿覆杞崲鎴愭嫾闊虫暟缁�
	 * 
	 * @param src
	 * @return
	 */
	public static String[] stringToPinyin(String src, String separator) {

		return stringToPinyin(src, true, separator);
	}

	/**
	 * 灏嗗瓧绗︿覆杞崲鎴愭嫾闊虫暟缁�
	 * 
	 * @param src
	 * @param isPolyphone
	 *            鏄惁鏌ュ嚭澶氶煶瀛楃殑鎵�鏈夋嫾闊�
	 * @param separator
	 *            澶氶煶瀛楁嫾闊充箣闂寸殑鍒嗛殧绗�
	 * @return
	 */
	public static String[] stringToPinyin(String src, boolean isPolyphone,
			String separator) {
		// 鍒ゆ柇瀛楃涓叉槸鍚︿负绌�
		if ("".equals(src) || null == src) {
			return null;
		}
		char[] srcChar = src.toCharArray();
		int srcCount = srcChar.length;
		String[] srcStr = new String[srcCount];

		for (int i = 0; i < srcCount; i++) {
			srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
		}
		return srcStr;
	}

	/**
	 * 灏嗗崟涓瓧绗﹁浆鎹㈡垚鎷奸煶
	 * 
	 * @param src
	 * @return
	 */
	public static String charToPinyin(char src, boolean isPolyphone,
			String separator) {
		// 鍒涘缓姹夎鎷奸煶澶勭悊绫�
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 杈撳嚭璁剧疆锛屽ぇ灏忓啓锛岄煶鏍囨柟寮�
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		StringBuffer tempPinying = new StringBuffer();

		// 濡傛灉鏄腑鏂�
		if (src > 128) {
			try {
				// 杞崲寰楀嚭缁撴灉
				String[] strs = PinyinHelper.toHanyuPinyinStringArray(src,
						defaultFormat);

				// 鏄惁鏌ュ嚭澶氶煶瀛楋紝榛樿鏄煡鍑哄闊冲瓧鐨勭涓�涓瓧绗�
				if (isPolyphone && null != separator) {
					for (int i = 0; i < strs.length; i++) {
						tempPinying.append(strs[i]);
						if (strs.length != (i + 1)) {
							// 澶氶煶瀛椾箣闂寸敤鐗规畩绗﹀彿闂撮殧璧锋潵
							tempPinying.append(separator);
						}
					}
				} else {
					tempPinying.append(strs[0]);
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			tempPinying.append(src);
		}

		return tempPinying.toString();

	}

	public static String hanziToPinyin(String hanzi) {
		return hanziToPinyin(hanzi, " ");
	}

	/**
	 * 灏嗘眽瀛楄浆鎹㈡垚鎷奸煶
	 * 
	 * @param hanzi
	 * @param separator
	 * @return
	 */
	public static String hanziToPinyin(String hanzi, String separator) {

		// 鍒涘缓姹夎鎷奸煶澶勭悊绫�
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 杈撳嚭璁剧疆锛屽ぇ灏忓啓锛岄煶鏍囨柟寮�
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		String pinyingStr = "";
		try {
			pinyingStr = PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat,
					separator);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pinyingStr;
	}

	/**
	 * 灏嗗瓧绗︿覆鏁扮粍杞崲鎴愬瓧绗︿覆
	 * 
	 * @param str
	 * @param separator
	 *            鍚勪釜瀛楃涓蹭箣闂寸殑鍒嗛殧绗�
	 * @return
	 */
	public static String stringArrayToString(String[] str, String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i]);
			if (str.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 绠�鍗曠殑灏嗗悇涓瓧绗︽暟缁勪箣闂磋繛鎺ヨ捣鏉�
	 * 
	 * @param str
	 * @return
	 */
	public static String stringArrayToString(String[] str) {
		return stringArrayToString(str, "");
	}

	/**
	 * 灏嗗瓧绗︽暟缁勮浆鎹㈡垚瀛楃涓�
	 * 
	 * @param str
	 * @param separator
	 *            鍚勪釜瀛楃涓蹭箣闂寸殑鍒嗛殧绗�
	 * @return
	 */
	public static String charArrayToString(char[] ch, String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if (ch.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 灏嗗瓧绗︽暟缁勮浆鎹㈡垚瀛楃涓�
	 * 
	 * @param str
	 * @return
	 */
	public static String charArrayToString(char[] ch) {
		return charArrayToString(ch, " ");
	}

	/**
	 * 鍙栨眽瀛楃殑棣栧瓧姣�
	 * 
	 * @param src
	 * @param isCapital
	 *            鏄惁鏄ぇ鍐�
	 * @return
	 */
	public static char[] getHeadByChar(char src, boolean isCapital) {
		// 濡傛灉涓嶆槸姹夊瓧鐩存帴杩斿洖
		if (src <= 128) {
			return new char[] { src };
		}
		// 鑾峰彇鎵�鏈夌殑鎷奸煶
		String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);

		// 鍒涘缓杩斿洖瀵硅薄
		int polyphoneSize = pinyingStr.length;
		char[] headChars = new char[polyphoneSize];
		int i = 0;
		// 鎴彇棣栧瓧绗�
		for (String s : pinyingStr) {
			char headChar = s.charAt(0);
			// 棣栧瓧姣嶆槸鍚﹀ぇ鍐欙紝榛樿鏄皬鍐�
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}

		return headChars;
	}

	/**
	 * 鍙栨眽瀛楃殑棣栧瓧姣�(榛樿鏄ぇ鍐�)
	 * 
	 * @param src
	 * @return
	 */
	public static char[] getHeadByChar(char src) {
		return getHeadByChar(src, true);
	}

	/**
	 * 鏌ユ壘瀛楃涓查瀛楁瘝
	 * 
	 * @param src
	 * @return
	 */
	public static String[] getHeadByString(String src) {
		return getHeadByString(src, true);
	}

	/**
	 * 鏌ユ壘瀛楃涓查瀛楁瘝
	 * 
	 * @param src
	 * @param isCapital
	 *            鏄惁澶у啓
	 * @return
	 */
	public static String[] getHeadByString(String src, boolean isCapital) {
		return getHeadByString(src, isCapital, null);
	}

	/**
	 * 鏌ユ壘瀛楃涓查瀛楁瘝
	 * 
	 * @param src
	 * @param isCapital
	 *            鏄惁澶у啓
	 * @param separator
	 *            鍒嗛殧绗�
	 * @return
	 */
	public static String[] getHeadByString(String src, boolean isCapital,
			String separator) {
		char[] chars = src.toCharArray();
		String[] headString = new String[chars.length];
		int i = 0;
		for (char ch : chars) {

			char[] chs = getHeadByChar(ch, isCapital);
			StringBuffer sb = new StringBuffer();
			if (null != separator) {
				int j = 1;

				for (char ch1 : chs) {
					sb.append(ch1);
					if (j != chs.length) {
						sb.append(separator);
					}
					j++;
				}
			} else {
				sb.append(chs[0]);
			}
			headString[i] = sb.toString();
			i++;
		}
		return headString;
	}
	
	public static void main(String[] args) {
		// pin4j 绠�鐮� 鍜� 鍩庡競缂栫爜 
		String s1 = "涓崕浜烘皯鍏卞拰鍥�"; 
		String[] headArray = getHeadByString(s1); // 鑾峰緱姣忎釜姹夊瓧鎷奸煶棣栧瓧姣�
		System.out.println(Arrays.toString(headArray));
		
		String s2 ="闀垮煄" ; 
		System.out.println(Arrays.toString(stringToPinyin(s2,true,",")));
		
		String s3 ="闀�";
		System.out.println(Arrays.toString(stringToPinyin(s3,true,",")));
	}
}
