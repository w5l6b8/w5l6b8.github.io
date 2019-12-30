package net.ebaolife.husqvarna.framework.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AttachmentUtils {


  public static void writeStreamToResponse(InputStream stream, HttpServletResponse response) throws IOException {
    OutputStream out = response.getOutputStream();
    try {
      byte[] buffer = new byte[1024 * 10];
      int len = 0;
      while ((len = stream.read(buffer)) > 0) {
        out.write(buffer, 0, len);
        out.flush();
      }
    } catch (Exception e) {} finally {
      stream.close();
     
    }
  }
  
}
