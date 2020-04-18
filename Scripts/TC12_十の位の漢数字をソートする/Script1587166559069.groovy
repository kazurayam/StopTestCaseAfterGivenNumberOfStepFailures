import java.text.Collator;
import java.text.RuleBasedCollator;

List<String> stringList = Arrays.asList("十","五","一","七","八","四","六","二","九",
	"三","十一","二十","廿","廿三","三十","二十一","十三");

RuleBasedCollator localRules = (RuleBasedCollator) Collator.getInstance(Locale.JAPAN);
String extraRules 

StringBuilder sb= new StringBuilder();
sb.append("一 < 二 < 三 < 四 < 五 < 六 < 七 < 八 < 九 < 十")
sb.append(" < 十一 < 十二 < 十三 < 十四 < 十五 < 十六 < 十七 < 十八 < 十九 < 二十,廿");
sb.append(" < 二十一,廿一 < 二十二,廿二 < 二十三,廿三 < 二十四,廿四 < 二十五,廿五");
sb.append(" < 二十六,廿六 < 二十七,廿七 < 二十八,廿八 < 二十九,廿九 < 三十 < 三十一");

RuleBasedCollator c = new RuleBasedCollator(localRules.getRules() + " & " + sb.toString());
Collections.sort(stringList, c);
System.out.println(stringList);
// [一, 二, 三, 四, 五, 六, 七, 八, 九, 十, 十一, 十三, 二十, 廿, 二十一, 廿三, 三十]
