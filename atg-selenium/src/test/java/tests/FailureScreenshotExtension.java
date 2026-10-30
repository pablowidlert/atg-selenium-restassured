package tests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class FailureScreenshotExtension
        implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        captureScreenshot(context, throwable);
        throw throwable;
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {
        captureScreenshot(context, throwable);
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {
        captureScreenshot(context, throwable);
        throw throwable;
    }

    private void captureScreenshot(ExtensionContext context, Throwable failure) {
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest baseTest) {
            baseTest.captureFailureScreenshot(context.getUniqueId(), failure);
        }
    }
}
