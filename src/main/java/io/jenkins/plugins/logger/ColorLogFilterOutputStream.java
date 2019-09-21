package io.jenkins.plugins.logger;

import hudson.console.LineTransformationOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * This class deals with the actual filtering of the console using the configured regexes. Bear in mind that for now
 * only one line expressions are possible.
 */
public class ColorLogFilterOutputStream extends LineTransformationOutputStream {
    private static final Logger LOGGER = Logger.getLogger(ColorLogFilterOutputStream.class.getName());

    //Logging
    private final OutputStream logger;
    private final Charset charset;


    public ColorLogFilterOutputStream(OutputStream out, Charset charset) {
        this.logger = out;
        this.charset = charset;
    }

    @Override
    public void close() throws IOException {
        try {
            // need to send the final EOL before closing the actual sink.
            super.close();
        } finally {
            // seems we need to do this, see JENKINS-45057.
            IOUtils.closeQuietly(logger);
        }
    }

    @Override
    protected void eol(byte[] bytes, int len) throws IOException {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "ColorCmdLogFilter: I am working");
        }

        final String inputLine = new String(bytes, 0, len, StandardCharsets.UTF_8);
        String line = inputLine;

        String newLine = LineUtils.filterLine(line);

        if (null != newLine) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE,
                    "Filtered logfile output ''{0}''.", line);
            }
            logger.write(newLine.getBytes(StandardCharsets.UTF_8));
        }

    }

}
