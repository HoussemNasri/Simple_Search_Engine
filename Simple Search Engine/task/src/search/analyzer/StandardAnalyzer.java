package search.analyzer;

import java.util.Arrays;
import java.util.List;

public class StandardAnalyzer implements Analyzer<String> {
  @Override
  public List<String> analyze(String text) {
    return Arrays.asList(text.split("\\s"));
  }
}
