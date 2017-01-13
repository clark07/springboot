package com.cs.lombok;

import lombok.Cleanup;

import java.io.*;

/**
 * Created by admin on //.
 */

/**
 * lombok.cleanup.flagUsage = [warning | error] (default: not set)
 Lombok will flag any usage of @Cleanup as a warning or error if configured.
 */
public class LombokCleanup {


	public static void main(String[] args) throws IOException {
		@Cleanup InputStream in = new FileInputStream("d:/土地殿字段.txt");
		@Cleanup OutputStream out = new FileOutputStream("d:/xxxxx.txt"); byte[] b = new byte[0]; while (true) {
			int r = in.read(b); if (r == -1)
				break; out.write(b, 0, r);
		}
	}

}
