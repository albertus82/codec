package it.albertus.codec.engine;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.mina.proxy.utils.MD4Provider;

import it.albertus.codec.resources.Messages;
import it.albertus.util.CRC16;

public class CodecEngine {

	private static final MD4Provider MD4_PROVIDER = new MD4Provider();

	private CodecAlgorithm algorithm;
	private CodecMode mode = CodecMode.ENCODE;
	private Charset charset = Charset.defaultCharset();

	public String run(final String input) {
		if (algorithm == null) {
			throw new IllegalStateException(Messages.get("msg.missing.algorithm"));
		}
		if (input == null || input.isEmpty()) {
			throw new IllegalStateException(Messages.get("msg.missing.input"));
		}
		switch (mode) {
		case DECODE:
			return decode(input);
		case ENCODE:
			return encode(input);
		default:
			throw new UnsupportedOperationException(Messages.get("err.invalid.mode", mode));
		}
	}

	private String encode(final String input) {
		String value = null;
		try {
			switch (algorithm) {
			case BASE16:
				value = Base16.encode(input.getBytes(charset));
				break;
			case BASE32:
				value = new Base32().encodeAsString(input.getBytes(charset));
				break;
			case BASE64:
				value = Base64.encodeBase64String(input.getBytes(charset));
				break;
			case ASCII85:
				value = Ascii85.encode(input.getBytes(charset));
				break;
			case BASE91:
				value = Base91.encode(input.getBytes(charset));
				break;
			case CRC16:
				final CRC16 crc16 = new CRC16();
				crc16.update(input.getBytes(charset));
				value = String.format("%04x", crc16.getValue());
				break;
			case CRC32:
				final CRC32 crc32 = new CRC32();
				crc32.update(input.getBytes(charset));
				value = String.format("%08x", crc32.getValue());
				break;
			case MD2:
				value = DigestUtils.md2Hex(input.getBytes(charset));
				break;
			case MD4:
				value = Hex.encodeHexString(MessageDigest.getInstance(CodecAlgorithm.MD4.name(), MD4_PROVIDER).digest(input.getBytes(charset)));
				break;
			case MD5:
				value = DigestUtils.md5Hex(input.getBytes(charset));
				break;
			case SHA1:
				value = DigestUtils.sha1Hex(input.getBytes(charset));
				break;
			case SHA256:
				value = DigestUtils.sha256Hex(input.getBytes(charset));
				break;
			case SHA384:
				value = DigestUtils.sha384Hex(input.getBytes(charset));
				break;
			case SHA512:
				value = DigestUtils.sha512Hex(input.getBytes(charset));
				break;
			default:
				throw new UnsupportedOperationException(Messages.get("err.invalid.algorithm", algorithm.getName()));
			}
		}
		catch (final Exception e) {
			throw new IllegalStateException(Messages.get("err.cannot.encode", algorithm.getName()), e);
		}
		return value;
	}

	private String decode(final String input) {
		String value = null;
		try {
			switch (algorithm) {
			case BASE16:
				value = new String(Base16.decode(input), charset);
				break;
			case BASE32:
				value = new String(new Base32().decode(input), charset);
				break;
			case BASE64:
				value = new String(Base64.decodeBase64(input), charset);
				break;
			case ASCII85:
				value = new String(Ascii85.decode(input), charset);
				break;
			case BASE91:
				value = new String(Base91.decode(input), charset);
				break;
			default:
				throw new UnsupportedOperationException(Messages.get("err.invalid.algorithm", algorithm.getName()));
			}
		}
		catch (final Exception e) {
			throw new IllegalStateException(Messages.get("err.cannot.decode", algorithm.getName()), e);
		}
		return value;
	}

	public CodecAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(final CodecAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public CodecMode getMode() {
		return mode;
	}

	public void setMode(final CodecMode mode) {
		this.mode = mode;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(final Charset charset) {
		this.charset = charset;
	}

	public static MD4Provider getMd4Provider() {
		return MD4_PROVIDER;
	}

}
