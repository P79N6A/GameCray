package com.blockchain.ossuploadlib.internal;

/**
 * Created by zhouzhuo on 9/19/15.
 */
public enum OSSRetryType {
    OSSRetryTypeShouldNotRetry,
    OSSRetryTypeShouldRetry,
    OSSRetryTypeShouldFixedTimeSkewedAndRetry,
}
