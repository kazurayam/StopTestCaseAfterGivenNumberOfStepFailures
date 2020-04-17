import static com.kazurayam.ksbackyard.PrimitiveKeywords.fail
import com.kazurayam.ksbackyard.KeywordUtilExpander

KeywordUtilExpander.stopAfterFailedStepsOf(3)

String msg = "All work and no play makes Huck a dull boy"

fail("1: " + msg)
fail("2: " + msg)
fail("3: " + msg)
fail("4: " + msg)
fail("5: " + msg)
fail("6: " + msg)
fail("7: " + msg)
fail("8: " + msg)
fail("9: " + msg)
fail("10: " + msg)
