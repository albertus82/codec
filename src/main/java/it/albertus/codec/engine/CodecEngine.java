package it.albertus.codec.engine;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CodecEngine {

	private CodecType codec;
	private CodecMode mode = CodecMode.ENCODE;

	public String run(String input) {
		if (codec != null) {
			switch (mode) {
			case DECODE:
				return decode(input);
			case ENCODE:
				return encode(input);
			default:
				throw new IllegalStateException("Invalid mode");
			}
		}
		else {
			return null;
		}
	}

	private String decode(String input) {
		switch (codec) {
		case BASE64:
			return new String(Base64.decodeBase64(input));
		default:
			break;
		}
		throw new IllegalStateException("Invalid codec");
	}

	private String encode(String input) {
		switch (codec) {
		case BASE64:
			return Base64.encodeBase64String(input.getBytes());
		case MD2:
			return DigestUtils.md2Hex(input);
		case MD5:
			return DigestUtils.md5Hex(input);
		case SHA1:
			return DigestUtils.sha1Hex(input);
		case SHA256:
			return DigestUtils.sha256Hex(input);
		case SHA384:
			return DigestUtils.sha384Hex(input);
		case SHA512:
			return DigestUtils.sha512Hex(input);
		default:
			break;
		}
		throw new IllegalStateException("Invalid codec");
	}

	public CodecType getCodec() {
		return codec;
	}

	public void setCodec(CodecType codec) {
		this.codec = codec;
	}

	public CodecMode getMode() {
		return mode;
	}

	public void setMode(CodecMode mode) {
		this.mode = mode;
	}

}
