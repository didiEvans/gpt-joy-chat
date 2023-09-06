package cloud.cstream.chat.core.wrappers;

import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RepeatableHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] body;

    public RepeatableHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        if (request.getContentLength() > 0) {
            try (InputStream inputStream = request.getInputStream()) {
                body = IoUtil.readBytes(inputStream);
            }
        } else {
            body = new byte[0];
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RepeatableServletInputStream(body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    private static class RepeatableServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RepeatableServletInputStream(byte[] body) {
            inputStream = new ByteArrayInputStream(body);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }
    }
}
