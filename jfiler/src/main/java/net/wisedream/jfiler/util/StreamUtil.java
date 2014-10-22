package net.wisedream.jfiler.util;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import net.wisedream.jfiler.Const;

public class StreamUtil {
	static final int GAP = 500;

	public static String readUTF(DataInputStream in) throws Exception {
		StringBuilder sb = new StringBuilder();
		while (in.available() > 0) {
			sb.append(in.readUTF());
		}
		return sb.toString();
	}

	/**
	 * Waits until date available or time out
	 * 
	 * @param in
	 * @param timeOut
	 *            seconds
	 * @return
	 */
	public static String readUTF(DataInputStream in, int timeOut)
			throws Exception {
		if (waitForData(in, timeOut))
			return readUTF(in);
		throw new Exception("read time out");
	}

	public static boolean waitForData(DataInputStream in, int timeOut)
			throws Exception {
		int ts = timeOut * 1000 / GAP;
		for (int i = 0; i < ts; i++) {
			if (in.available() > 0)
				return true;
			else
				try {
					Thread.sleep(GAP);
				} catch (Exception e) {
				}
		}
		return false;
	}

	/**
	 * writes data read from src to dest
	 * 
	 * @param src
	 * @param dest
	 * @param bufferSize
	 * @param total
	 * @throws Exception
	 */
	public static void writeData(InputStream src, OutputStream dest,
			int bufferSize, long total) throws Exception {
		byte[] buffer = new byte[bufferSize];
		int rc = 0;
		long read = 0;
		while ((rc = src.read(buffer)) > 0) {
			dest.write(buffer, 0, rc);
			read += rc;
			if (read >= total)
				break;
		}
		dest.flush();
	}

	public static void closeStream(Closeable stream) {
		try {
			if (stream != null)
				stream.close();
		} catch (Exception e) {
		}
	}

	public static byte[] calcMD5(File file) throws Exception {
		FileInputStream in = new FileInputStream(file);
		MessageDigest md5 = MessageDigest.getInstance("md5");
		byte[] buffer = new byte[Const.BUFFER_SIZE];
		int count = 0;
		while ((count = in.read(buffer)) > 0)
			md5.update(buffer, 0, count);
		closeStream(in);
		return md5.digest();
	}

	public static String convertToHexString(byte[] data) {
		final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuilder sb = new StringBuilder(32);
		for (byte tmp : data) {
			sb.append(digits[tmp >>> 4 & 0xf]);
			sb.append(digits[tmp & 0xf]);
		}
		return sb.toString();
	}

	public static byte[] calcMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("md5");
		md5.update(data);
		return md5.digest();
	}
}
