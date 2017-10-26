package vn.kms.fundamentals.basicwebapp.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathResolver {
  private static final Pattern PARAMETER_PATTERN = Pattern.compile("(\\{[a-zA-Z]+})");
  private static final String PARAM_PLACEHOLDER = "___PARAM___";
  private final Map<String, Map.Entry<Pattern, List<String>>> paramsByUriTemplates = new HashMap<>();

  public boolean matchUri(String uriTemplate, String uriPath) {
    Matcher matcher = getUriMatcher(uriTemplate, uriPath);

    return matcher.matches();
  }

  public Map<String, String> getParams(String uriTemplate, String uriPath) {
    Matcher matcher = getUriMatcher(uriTemplate, uriPath);

    if (!matcher.matches()) {
      return Collections.emptyMap();
    }

    List<String> paramNames = paramsByUriTemplates.get(uriTemplate).getValue();
    final Map<String, String> pathParams = new HashMap<>();
    for (int i = 1; i <= matcher.groupCount(); i++) {
      pathParams.put(paramNames.get(i - 1), matcher.group(i));
    }

    return pathParams;
  }

  private Matcher getUriMatcher(String uriTemplate, String uriPath) {
    Map.Entry<Pattern, List<String>> patternParamNames = paramsByUriTemplates.get(uriTemplate);
    if (patternParamNames == null) {
      patternParamNames = parseParamNames(uriTemplate);
      paramsByUriTemplates.put(uriTemplate, patternParamNames);
    }

    Pattern uriPattern = patternParamNames.getKey();

    return uriPattern.matcher(uriPath);
  }

  private Map.Entry<Pattern, List<String>> parseParamNames(String uriTemplate) {
    final Matcher matcher = PARAMETER_PATTERN.matcher(uriTemplate);

    List<String> paramNames = new ArrayList<>();

    while (matcher.find()) {
      if (matcher.groupCount() == 1) {
        final String group = matcher.group(1);
        if (group.length() > 2) {
          paramNames.add(group.substring(1, group.length() - 1));
        } else {
          paramNames.add(group);
        }
      }
    }

    Pattern pattern = Pattern.compile(Pattern.quote(matcher
        .replaceAll(PARAM_PLACEHOLDER))
        .replace(PARAM_PLACEHOLDER, "\\E([^/]*)\\Q"));

    return new AbstractMap.SimpleEntry<>(pattern, paramNames);
  }
}
