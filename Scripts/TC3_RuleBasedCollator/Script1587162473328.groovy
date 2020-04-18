import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.joining;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.stream.IntStream;

List<String> list = new ArrayList<String>();
list.add("AA-10");
list.add("AA-1");
list.add("AA-2 (1)");
list.add("AA-2");

RuleBasedCollator localRules = (RuleBasedCollator) Collator.getInstance();
String extraRules = IntStream.range(0, 100).mapToObj(String.&valueOf).collect(joining(" < "));
println "extraRules=" + extraRules
RuleBasedCollator c = new RuleBasedCollator(localRules.getRules() + " & " + extraRules);

shuffle(list);
list.sort(c);
System.out.println(list);