How to stop a TestCase after given number of StepFailures occured
==========

## What is this

This is a small Katalon Studio project for demonstration purpose.
You can download the zip from [Releases](https://github.com/kazurayam/StopTestCaseAfterGivenNumberOfStepFailures/releases) page, unzip it, and open it using your Katalon Studio.

I developed this project using Katalon Studio ver 7.2.1.

I developed this project to propose a solution to a discussion in Katalon Forum:

- [How to force stop test case after a few consecutive step failures](https://forum.katalon.com/t/how-to-force-stop-test-case-after-a-few-consecutive-step-failures/42383)

## Problem to solve

Quote from the oritinal post:

>I have default failure handling set to CONTINUE_ON_FAILURE (some small failures won’t disrupt the overall test), but I have found in situations where test steps fail consecutively (aka in a state where the rest of my steps would never pass) scripts that usually take max 10 minutes to execute are now taking hours.

## Solution

When a step failure occured, it emits the following stacktrace:

```
com.kms.katalon.core.exception.StepFailedException: <your message text comes here>
	at com.kms.katalon.core.util.KeywordUtil.markFailed(KeywordUtil.java:19)
...
```

It tells that `com.kms.katalon.core.util.KeywordUtil.markFailed()` method is invoked everytime a step failed with the default FailureHandling to be CONTINUE_ON_FAILURE. You can read the source of `KeywordUtil` disclosed [here](https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/util/KeywordUtil.java).

You would want to change the behavior of `markFailed()` so that it counts the number of step failures.
And as soon as the failure count exceeds the criteria you set,
`markFailed()` should throw an Exception to quit the test case.

However, is it possible to change the behavior of the built-in `KeywordUtil.markFailed()` on the fly?

Yes, you can. Groovy language's Metaprogramming feature enables you to do it.

## Description

### Demo

Please try running the `Test Cases/TC1_10failures`:

```
import static com.kazurayam.ksbackyard.PrimitiveKeywords.fail
import com.kms.katalon.core.model.FailureHandling

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
```

This test case emits 10 times of step failures. the `fail(String)` is a primitive custom keyword that always fails. You can see the source [here](./Keywords/com/kazurayam/ksbackyard/PrimitiveKeywords.groovy).

Now I want to quit this test case as soon as `fail(String)` was called more thatn 3 times.

Please try running the `Test Cases/TC2_stopAfterGivenNumberOfFailures`:

```
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

```

Please note that a new custom keyword `com.kazurayam.ksbackyard.KeywordUtilExpander` is used here. The source code is [here](./Keywords/com/kazurayam/ksbackyard/KeywordUtilExpander.groovy). This class employs a bit of Groovy' Metaprogramming feature. I would not explain it here. You can read the full documentation [here](https://groovy-lang.org/metaprogramming.html#metaprogramming_emc).

When you run the TC2, you will see the following output in the console.

```
2020-04-17 10:40:08.170 INFO  c.k.katalon.core.main.TestCaseExecutor   - START Test Cases/TC2_stopAfterGivenNumberOfFailures
2020-04-17 10:40:08.829 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 1: KeywordUtilExpander.stopAfterFailedStepsOf(3)
2020-04-17 10:40:08.890 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 2: msg = "All work and no play makes Huck a dull boy"
2020-04-17 10:40:08.894 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 3: PrimitiveKeywords.fail("1: " + msg)
2020-04-17 10:40:08.910 ERROR com.kms.katalon.core.util.KeywordUtil    - ❌ 1: All work and no play makes Huck a dull boy
2020-04-17 10:40:08.972 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 4: PrimitiveKeywords.fail("2: " + msg)
2020-04-17 10:40:08.976 ERROR com.kms.katalon.core.util.KeywordUtil    - ❌ 2: All work and no play makes Huck a dull boy
2020-04-17 10:40:08.979 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 5: PrimitiveKeywords.fail("3: " + msg)
2020-04-17 10:40:09.001 ERROR com.kms.katalon.core.util.KeywordUtil    - ❌ 3: All work and no play makes Huck a dull boy
2020-04-17 10:40:09.002 DEBUG t.TC2_stopAfterGivenNumberOfFailures     - 6: PrimitiveKeywords.fail("4: " + msg)
2020-04-17 10:40:09.004 ERROR com.kms.katalon.core.util.KeywordUtil    - ❌ 4: All work and no play makes Huck a dull boy
2020-04-17 10:40:09.069 WARN  com.kms.katalon.core.util.KeywordUtil    - Expanded markFiled() detected that 4 steps failed, which exceeds the limit you declared:3. So long!
2020-04-17 10:40:09.122 ERROR c.k.katalon.core.main.TestCaseExecutor   - ❌ Test Cases/TC2_stopAfterGivenNumberOfFailures FAILED.
Reason:
com.kms.katalon.core.exception.StepFailedException: 1: All work and no play makes Huck a dull boy
	at com.kazurayam.ksbackyard.KeywordUtilExpander$_stopAfterFailedStepsOf_closure1.doCall(KeywordUtilExpander.groovy:20)
....
```

The test case stops after 3 times of step failure, while skipping the following steps. --- the problem is resolved.
