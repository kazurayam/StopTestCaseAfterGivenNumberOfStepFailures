import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.joining;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.stream.IntStream;

RuleBasedCollator localRules = (RuleBasedCollator) Collator.getInstance();

String extraRules = IntStream.range(0, 100).mapToObj(String.&valueOf).collect(joining(" < "));
RuleBasedCollator c = new RuleBasedCollator(localRules.getRules() + " & " + extraRules);

List<String> a = Arrays.asList("1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01", "pic02", "pic02a", "pic 5", "pic05", "pic   7", "pic100", "pic100a", "pic120", "pic121");
shuffle(a);

a.sort(c);
System.out.println(a);