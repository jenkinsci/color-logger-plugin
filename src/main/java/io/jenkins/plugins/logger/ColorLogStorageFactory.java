package io.jenkins.plugins.logger;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.Run;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.jenkinsci.plugins.workflow.log.BrokenLogStorage;
import org.jenkinsci.plugins.workflow.log.LogStorage;
import org.jenkinsci.plugins.workflow.log.LogStorageFactory;

/**
 * @author albert.lv
 */
@Extension
public class ColorLogStorageFactory implements LogStorageFactory {

  private static final Logger LOGGER = Logger.getLogger(ColorLogStorageFactory.class.getName());

  @Override
  public LogStorage forBuild(FlowExecutionOwner owner) {
    try {
      Queue.Executable exec = owner.getExecutable();
      if (exec instanceof Run) {
        if (LOGGER.isLoggable(Level.FINE)) {
          LOGGER.log(Level.FINE, "Jenkins got ColorLogStorage");
        }
        return ColorLogStorage.forFile(new File(owner.getRootDir(), "log"));
      } else {
        return null;
      }
    } catch (IOException x) {
      return new BrokenLogStorage(x);
    }
  }

}
