package io.jenkins.plugins.logger;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author albert.lv
 */
public class LineUtils {

  private static Logger LOGGER = Logger.getLogger(LineUtils.class.getCanonicalName());

  private static final Pattern WARNING = Pattern.compile("WARN", Pattern.CASE_INSENSITIVE);
  private static final Pattern ERROR = Pattern.compile("ERROR", Pattern.CASE_INSENSITIVE);
  private static final Pattern CMD = Pattern.compile("\u001B\\[0(;[3-4][0-7])?m");

  private static final String HIDDEN_CMD = "\u001B[8mha";
  private static final String WARNING_SPAN_PREFIX = "<span style=\"color: #fff; background-color: #f57542; font-size: 100%; font-weight: bold\">";
  private static final String ERROR_SPAN_PREFIX = "<span style=\"color: #fff; background-color: #ed0e0e; font-size: 100%; font-weight: bold\">";
  private static final String SPAN_SUFFIX = "</span>";

  public static String filterLine(String line) {
    String result = null;
    try {
      if (line.indexOf(HIDDEN_CMD) != -1) return result;
      line = CMD.matcher(line).replaceAll("");

      StringBuilder sb = new StringBuilder(line);
      if (WARNING.matcher(line).find()) {
        sb.insert(0, WARNING_SPAN_PREFIX);
        sb.insert(sb.lastIndexOf("\n"), SPAN_SUFFIX);
      } else if (ERROR.matcher(line).find()) {
        sb.insert(0, ERROR_SPAN_PREFIX);
        sb.insert(sb.lastIndexOf("\n"), SPAN_SUFFIX);
      }
      result = sb.toString();
    } catch (Exception e) {
      LOGGER.log(Level.WARNING,
          "Exception when wrapping log output" +
              " in line '" + line + "' got: '" + e + "'.", e);
    }
    return result;
  }

  public static void main(String[] args) {
    Pattern CMD = Pattern.compile("\u001B\\[0(;[3-4][0-7])?m");
    String cmd = "\u001B[0;31m";
    System.out.println(CMD.matcher(cmd).matches());
  }

}
