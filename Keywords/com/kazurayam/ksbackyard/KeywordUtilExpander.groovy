package com.kazurayam.ksbackyard

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.logging.ErrorCollector
import com.kms.katalon.core.util.KeywordUtil

/**
 * Override some methods of KeywordUtil class.
 * 
 * You can read the source of KeyrodUtil class at
 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/util/KeywordUtil.java
 * 
 * @author kazurayam
 */
public class KeywordUtilExpander {

	static void stopAfterFailedStepsOf(int ceil) {
		KeywordUtil.metaClass.static.markFailed = { String message ->
			delegate.logger.logFailed(message);
			ErrorCollector.getCollector().addError(new StepFailedException(message));
			//
			def numberOfFailedSteps = ErrorCollector.getCollector().getErrors().size()
			if (numberOfFailedSteps > ceil) {
				StringBuffer sb = new StringBuffer()
				sb.append("Expanded markFiled() detected that ")
				sb.append(numberOfFailedSteps)
				sb.append(" steps failed, which exceeds the limit you declared:")
				sb.append(ceil)
				KeywordUtil.markWarning(sb.toString())
				throw new StepFailedException(sb.toString());
			}
		}
	}
}
