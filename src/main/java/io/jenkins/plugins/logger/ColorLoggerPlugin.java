package io.jenkins.plugins.logger;

import hudson.Plugin;
import org.apache.log4j.Logger;

/**
 * @author albert.lv
 */
public class ColorLoggerPlugin extends Plugin {

  private static final Logger LOGGER = Logger.getLogger(ColorLoggerPlugin.class.getCanonicalName());

  @Override
  public void start() throws Exception {
    LOGGER.info("ColorLoggerPlugin starting ...");
  }
}
