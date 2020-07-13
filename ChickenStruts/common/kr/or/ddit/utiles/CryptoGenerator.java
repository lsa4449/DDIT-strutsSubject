package kr.or.ddit.utiles;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;

public class CryptoGenerator {
	// 암, 복호화 : 공개키 + 비밀키(생성시 동반 생성. 1회 활용하고 폐기)
	// 반환값 : 공개키(가수부, 지수부 구분 저장 후 반환)
	// 파라미터 : 비밀키를 세션에 저장
	// RSA 공개키 DES 
	public static Map<String, String> generatePairKey(HttpSession session) {
		// 공개키 + 비밀키 생성
		KeyPairGenerator keyGenerator = null;
		// 생성된 공개키 + 비밀키
		KeyPair keyPair = null;
		// 공개키
		PublicKey publicKey = null;
		// 비밀키
		PrivateKey privateKey = null;

		// 공개키 = 가수부 + 지수부
		KeyFactory keyFactory = null;

		Map<String, String> publicKeyMap = new HashMap<String, String>();

		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			// 공개키, 비밀키 생성시 사이즈 설정 : byte 단위
			// 짝수 설정
			keyGenerator.initialize(2048);
			// 생성된 공개키, 비밀키 취득
			keyPair = keyGenerator.generateKeyPair();
			// 공개키 취득
			publicKey = keyPair.getPublic();
			// 비밀키 취득
			privateKey = keyPair.getPrivate();

			session.setAttribute("privateKey", privateKey);

			// 공개키(Double Type) : 가수부 + 지수부 => 클라이언트에 제공
			// -143.12344556
			// float(32bit 단정도 소수) : 부호비트 1bit(양수 0 | 음수 1) + 지수 8bit(소수점 자릿수) +
			// 가수 23bit(실수 표현)
			// double(64bit 배정도 소수) : 부호비트 1bit(양수 0 | 음수 1) + 지수 11bit(소수점 자릿수)
			// + 가수 52bit(실수 표현)
			keyFactory = KeyFactory.getInstance("RSA");
			
			RSAPublicKeySpec publicKeySpec = (RSAPublicKeySpec) keyFactory
					.getKeySpec(publicKey, RSAPublicKeySpec.class);
			// 공개키 가수부
			String publicModuls = publicKeySpec.getModulus().toString(16);
			// 공개키 지수부
			String publicExponent = publicKeySpec.getPublicExponent().toString(16);

			publicKeyMap.put("publicModuls", publicModuls);
			publicKeyMap.put("publicExponent", publicExponent);

		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e2) {
			e2.printStackTrace();
		}
		return publicKeyMap;
	}

	// 암호문을 평문으로 복호화
	public static String decryptRSA(HttpSession session, String secureValue) {
		String returnValue = "";
		PrivateKey privateKey = (PrivateKey)session.getAttribute("privateKey");

		try {
			// Cipher는 byte Array 형태로 넘겨진 데이터를 암호화 혹은 복호화하는데 사용된다.
			Cipher cipher = Cipher.getInstance("RSA");
			// 비밀키 암호 초기화
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			// 암호문은 짝수 단위로 바이너리 코드 존재
			byte[] targetByte = hextoByteArray(secureValue);
			
			// 암호화키를 비밀키로 복호화하는 과정
			byte[] beforeString = cipher.doFinal(targetByte);
			returnValue = new String(beforeString, "UTF-8");

	   
	   /* 암호화 수행 발생Exception 
	    * IllegalStateException 
	    *  	– 암호가 잘못한 상태에있는 경우 (초기화되어 있지 않은 등)
	    * IllegalBlockSizeException 
	    *  	– 입력 데이터를 처리 할 수없는 경우
	    *  BadPaddingException, AEADBadTagException 등 발생
	    */
		} catch (NoSuchAlgorithmException 
				| NoSuchPaddingException
				| InvalidKeyException 
				| IllegalBlockSizeException
				| BadPaddingException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	// 암호문이 널이거나 짝수가 아니면 암호문이 암호화 에러
	// 헥사문자열 -> 바이트문자열 변경 
	private static byte[] hextoByteArray(String secureValue) {
		if (secureValue == null || secureValue.length() % 2 != 0) {
			return new byte[] {};
		}
		byte[] bytes = new byte[secureValue.length() / 2];

		for (int i = 0; i < secureValue.length(); i += 2) {
				byte value = (byte)Integer.parseInt(secureValue.substring(i, i+2), 16);
				bytes[(int)Math.floor(i/2)] = value;
		}
		return bytes;
	}

}
