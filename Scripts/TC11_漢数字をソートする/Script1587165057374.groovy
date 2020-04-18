import java.text.Collator;
import java.text.RuleBasedCollator;

List<String> stringList = Arrays.asList("五","一","七","八","四","六","二","九","三");

// 各CharのUNICODE値の順にソート
Collections.sort(stringList);
System.out.println(stringList);
// [一, 七, 三, 九, 二, 五, 八, 六, 四]

// 音読み順にソート
Collator collator = Collator.getInstance(Locale.JAPAN);
Collections.sort(stringList, collator);
System.out.println(stringList);
// [一, 九, 五, 三, 四, 七, 二, 八, 六]

//
RuleBasedCollator localRules = (RuleBasedCollator) Collator.getInstance(Locale.JAPAN);
String extraRules = "一 < 二 < 三 < 四 < 五 < 六 < 七 < 八 < 九 < 十";
RuleBasedCollator c = new RuleBasedCollator(localRules.getRules() + " & " + extraRules);
Collections.sort(stringList, c);
System.out.println(stringList);
// [一, 二, 三, 四, 五, 六, 七, 八, 九]