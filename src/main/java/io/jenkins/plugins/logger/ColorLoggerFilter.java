package io.jenkins.plugins.logger;

import hudson.Extension;
import hudson.console.ConsoleLogFilter;
import hudson.model.Run;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author albert.lv
 */
@Extension
public class ColorLoggerFilter extends ConsoleLogFilter implements Serializable {
  private static final Logger LOGGER = Logger.getLogger(ConsoleLogFilter.class.getCanonicalName());
  private static final long serialVersionUID = -2002540783410493275L;

  @Override
  public OutputStream decorateLogger(Run build, OutputStream logger)
      throws IOException, InterruptedException {
    if(logger == null)
      return null;

    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, "Jenkins got ColorCmdLogFilter");
    }

    Charset charset = null;
    if (build != null) {
      charset = build.getCharset();
    } else {
      charset = Charset.defaultCharset();
    }

    return new ColorLogFilterOutputStream(logger, charset);

  }
}
