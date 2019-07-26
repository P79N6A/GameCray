/**
 * Copyright (C) Alibaba Cloud Computing, 2015
 * All rights reserved.
 * <p>
 * 版权所有 （C）阿里巴巴云计算，2015
 */

package com.blockchain.ossuploadlib;

import android.content.Context;

import com.blockchain.ossuploadlib.callback.OSSCompletedCallback;
import com.blockchain.ossuploadlib.common.auth.OSSCredentialProvider;
import com.blockchain.ossuploadlib.internal.OSSAsyncTask;
import com.blockchain.ossuploadlib.model.AbortMultipartUploadRequest;
import com.blockchain.ossuploadlib.model.AbortMultipartUploadResult;
import com.blockchain.ossuploadlib.model.AppendObjectRequest;
import com.blockchain.ossuploadlib.model.AppendObjectResult;
import com.blockchain.ossuploadlib.model.CompleteMultipartUploadRequest;
import com.blockchain.ossuploadlib.model.CompleteMultipartUploadResult;
import com.blockchain.ossuploadlib.model.CopyObjectRequest;
import com.blockchain.ossuploadlib.model.CopyObjectResult;
import com.blockchain.ossuploadlib.model.CreateBucketRequest;
import com.blockchain.ossuploadlib.model.CreateBucketResult;
import com.blockchain.ossuploadlib.model.DeleteBucketLifecycleRequest;
import com.blockchain.ossuploadlib.model.DeleteBucketLifecycleResult;
import com.blockchain.ossuploadlib.model.DeleteBucketLoggingRequest;
import com.blockchain.ossuploadlib.model.DeleteBucketLoggingResult;
import com.blockchain.ossuploadlib.model.DeleteBucketRequest;
import com.blockchain.ossuploadlib.model.DeleteBucketResult;
import com.blockchain.ossuploadlib.model.DeleteMultipleObjectRequest;
import com.blockchain.ossuploadlib.model.DeleteMultipleObjectResult;
import com.blockchain.ossuploadlib.model.DeleteObjectRequest;
import com.blockchain.ossuploadlib.model.DeleteObjectResult;
import com.blockchain.ossuploadlib.model.GeneratePresignedUrlRequest;
import com.blockchain.ossuploadlib.model.GetBucketACLRequest;
import com.blockchain.ossuploadlib.model.GetBucketACLResult;
import com.blockchain.ossuploadlib.model.GetBucketInfoRequest;
import com.blockchain.ossuploadlib.model.GetBucketInfoResult;
import com.blockchain.ossuploadlib.model.GetBucketLifecycleRequest;
import com.blockchain.ossuploadlib.model.GetBucketLifecycleResult;
import com.blockchain.ossuploadlib.model.GetBucketLoggingRequest;
import com.blockchain.ossuploadlib.model.GetBucketLoggingResult;
import com.blockchain.ossuploadlib.model.GetBucketRefererRequest;
import com.blockchain.ossuploadlib.model.GetBucketRefererResult;
import com.blockchain.ossuploadlib.model.GetObjectACLRequest;
import com.blockchain.ossuploadlib.model.GetObjectACLResult;
import com.blockchain.ossuploadlib.model.GetObjectRequest;
import com.blockchain.ossuploadlib.model.GetObjectResult;
import com.blockchain.ossuploadlib.model.GetSymlinkRequest;
import com.blockchain.ossuploadlib.model.GetSymlinkResult;
import com.blockchain.ossuploadlib.model.HeadObjectRequest;
import com.blockchain.ossuploadlib.model.HeadObjectResult;
import com.blockchain.ossuploadlib.model.ImagePersistRequest;
import com.blockchain.ossuploadlib.model.ImagePersistResult;
import com.blockchain.ossuploadlib.model.InitiateMultipartUploadRequest;
import com.blockchain.ossuploadlib.model.InitiateMultipartUploadResult;
import com.blockchain.ossuploadlib.model.ListBucketsRequest;
import com.blockchain.ossuploadlib.model.ListBucketsResult;
import com.blockchain.ossuploadlib.model.ListMultipartUploadsRequest;
import com.blockchain.ossuploadlib.model.ListMultipartUploadsResult;
import com.blockchain.ossuploadlib.model.ListObjectsRequest;
import com.blockchain.ossuploadlib.model.ListObjectsResult;
import com.blockchain.ossuploadlib.model.ListPartsRequest;
import com.blockchain.ossuploadlib.model.ListPartsResult;
import com.blockchain.ossuploadlib.model.MultipartUploadRequest;
import com.blockchain.ossuploadlib.model.PutBucketLifecycleRequest;
import com.blockchain.ossuploadlib.model.PutBucketLifecycleResult;
import com.blockchain.ossuploadlib.model.PutBucketLoggingRequest;
import com.blockchain.ossuploadlib.model.PutBucketLoggingResult;
import com.blockchain.ossuploadlib.model.PutBucketRefererRequest;
import com.blockchain.ossuploadlib.model.PutBucketRefererResult;
import com.blockchain.ossuploadlib.model.PutObjectRequest;
import com.blockchain.ossuploadlib.model.PutObjectResult;
import com.blockchain.ossuploadlib.model.PutSymlinkRequest;
import com.blockchain.ossuploadlib.model.PutSymlinkResult;
import com.blockchain.ossuploadlib.model.RestoreObjectRequest;
import com.blockchain.ossuploadlib.model.RestoreObjectResult;
import com.blockchain.ossuploadlib.model.ResumableUploadRequest;
import com.blockchain.ossuploadlib.model.ResumableUploadResult;
import com.blockchain.ossuploadlib.model.TriggerCallbackRequest;
import com.blockchain.ossuploadlib.model.TriggerCallbackResult;
import com.blockchain.ossuploadlib.model.UploadPartRequest;
import com.blockchain.ossuploadlib.model.UploadPartResult;

import java.io.IOException;

/**
 * The entry point class of (Open Storage Service, OSS）, which is the implementation of interface
 * OSS.
 */
public class OSSClient implements OSS {

    private OSS mOss;

    /**
     * Creates a {@link OSSClient} instance.
     *
     * @param context            android application's application context
     * @param endpoint           OSS endpoint, check out:http://help.aliyun.com/document_detail/oss/user_guide/endpoint_region.html
     * @param credentialProvider credential provider instance
     */
    public OSSClient(Context context, String endpoint, OSSCredentialProvider credentialProvider) {
        this(context, endpoint, credentialProvider, null);
    }

    /**
     * Creates a {@link OSSClient} instance.
     *
     * @param context            aandroid application's application context
     * @param endpoint           OSS endpoint, check out:http://help.aliyun.com/document_detail/oss/user_guide/endpoint_region.html
     * @param credentialProvider credential provider instance
     * @param conf               Client side configuration
     */
    public OSSClient(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        mOss = new OSSImpl(context, endpoint, credentialProvider, conf);
    }

    public OSSClient(Context context, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        mOss = new OSSImpl(context, credentialProvider, conf);
    }

    @Override
    public OSSAsyncTask<ListBucketsResult> asyncListBuckets(
            ListBucketsRequest request, OSSCompletedCallback<ListBucketsRequest, ListBucketsResult> completedCallback) {
        return mOss.asyncListBuckets(request, completedCallback);
    }

    @Override
    public ListBucketsResult listBuckets(ListBucketsRequest request)
            throws ClientException, ServiceException {
        return mOss.listBuckets(request);
    }

    @Override
    public OSSAsyncTask<CreateBucketResult> asyncCreateBucket(
            CreateBucketRequest request, OSSCompletedCallback<CreateBucketRequest, CreateBucketResult> completedCallback) {

        return mOss.asyncCreateBucket(request, completedCallback);
    }

    @Override
    public CreateBucketResult createBucket(CreateBucketRequest request)
            throws ClientException, ServiceException {

        return mOss.createBucket(request);
    }

    @Override
    public OSSAsyncTask<DeleteBucketResult> asyncDeleteBucket(
            DeleteBucketRequest request, OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult> completedCallback) {

        return mOss.asyncDeleteBucket(request, completedCallback);
    }

    @Override
    public DeleteBucketResult deleteBucket(DeleteBucketRequest request)
            throws ClientException, ServiceException {

        return mOss.deleteBucket(request);
    }

    @Override
    public OSSAsyncTask<GetBucketInfoResult> asyncGetBucketInfo(GetBucketInfoRequest request, OSSCompletedCallback<GetBucketInfoRequest, GetBucketInfoResult> completedCallback) {
        return mOss.asyncGetBucketInfo(request, completedCallback);
    }

    @Override
    public GetBucketInfoResult getBucketInfo(GetBucketInfoRequest request) throws ClientException, ServiceException {
        return mOss.getBucketInfo(request);
    }

    @Override
    public OSSAsyncTask<GetBucketACLResult> asyncGetBucketACL(
            GetBucketACLRequest request, OSSCompletedCallback<GetBucketACLRequest, GetBucketACLResult> completedCallback) {

        return mOss.asyncGetBucketACL(request, completedCallback);
    }

    @Override
    public GetBucketACLResult getBucketACL(GetBucketACLRequest request)
            throws ClientException, ServiceException {

        return mOss.getBucketACL(request);
    }

    @Override
    public OSSAsyncTask<PutBucketRefererResult> asyncPutBucketReferer(PutBucketRefererRequest request, OSSCompletedCallback<PutBucketRefererRequest, PutBucketRefererResult> completedCallback) {
        return mOss.asyncPutBucketReferer(request, completedCallback);
    }

    @Override
    public PutBucketRefererResult putBucketReferer(PutBucketRefererRequest request) throws ClientException, ServiceException {
        return mOss.putBucketReferer(request);
    }

    @Override
    public GetBucketRefererResult getBucketReferer(GetBucketRefererRequest request) throws ClientException, ServiceException {
        return mOss.getBucketReferer(request);
    }

    @Override
    public OSSAsyncTask<GetBucketRefererResult> asyncGetBucketReferer(GetBucketRefererRequest request, OSSCompletedCallback<GetBucketRefererRequest, GetBucketRefererResult> completedCallback) {
        return mOss.asyncGetBucketReferer(request, completedCallback);
    }

    @Override
    public DeleteBucketLoggingResult deleteBucketLogging(DeleteBucketLoggingRequest request) throws ClientException, ServiceException {
        return mOss.deleteBucketLogging(request);
    }

    @Override
    public OSSAsyncTask<DeleteBucketLoggingResult> asyncDeleteBucketLogging(DeleteBucketLoggingRequest request, OSSCompletedCallback<DeleteBucketLoggingRequest, DeleteBucketLoggingResult> completedCallback) {
        return mOss.asyncDeleteBucketLogging(request, completedCallback);
    }

    @Override
    public PutBucketLoggingResult putBucketLogging(PutBucketLoggingRequest request) throws ClientException, ServiceException {
        return mOss.putBucketLogging(request);
    }

    @Override
    public OSSAsyncTask<PutBucketLoggingResult> asyncPutBucketLogging(PutBucketLoggingRequest request, OSSCompletedCallback<PutBucketLoggingRequest, PutBucketLoggingResult> completedCallback) {
        return mOss.asyncPutBucketLogging(request, completedCallback);
    }

    @Override
    public GetBucketLoggingResult getBucketLogging(GetBucketLoggingRequest request) throws ClientException, ServiceException {
        return mOss.getBucketLogging(request);
    }

    @Override
    public OSSAsyncTask<GetBucketLoggingResult> asyncGetBucketLogging(GetBucketLoggingRequest request, OSSCompletedCallback<GetBucketLoggingRequest, GetBucketLoggingResult> completedCallback) {
        return mOss.asyncGetBucketLogging(request, completedCallback);
    }

    @Override
    public PutBucketLifecycleResult putBucketLifecycle(PutBucketLifecycleRequest request) throws ClientException, ServiceException {
        return mOss.putBucketLifecycle(request);
    }

    @Override
    public OSSAsyncTask<PutBucketLifecycleResult> asyncPutBucketLifecycle(PutBucketLifecycleRequest request, OSSCompletedCallback<PutBucketLifecycleRequest, PutBucketLifecycleResult> completedCallback) {
        return mOss.asyncPutBucketLifecycle(request, completedCallback);
    }

    @Override
    public GetBucketLifecycleResult getBucketLifecycle(GetBucketLifecycleRequest request) throws ClientException, ServiceException {
        return mOss.getBucketLifecycle(request);
    }

    @Override
    public OSSAsyncTask<GetBucketLifecycleResult> asyncGetBucketLifecycle(GetBucketLifecycleRequest request, OSSCompletedCallback<GetBucketLifecycleRequest, GetBucketLifecycleResult> completedCallback) {
        return mOss.asyncGetBucketLifecycle(request, completedCallback);
    }

    @Override
    public DeleteBucketLifecycleResult deleteBucketLifecycle(DeleteBucketLifecycleRequest request) throws ClientException, ServiceException {
        return mOss.deleteBucketLifecycle(request);
    }

    @Override
    public OSSAsyncTask<DeleteBucketLifecycleResult> asyncDeleteBucketLifecycle(DeleteBucketLifecycleRequest request, OSSCompletedCallback<DeleteBucketLifecycleRequest, DeleteBucketLifecycleResult> completedCallback) {
        return mOss.asyncDeleteBucketLifecycle(request, completedCallback);
    }

    @Override
    public OSSAsyncTask<PutObjectResult> asyncPutObject(
            PutObjectRequest request, OSSCompletedCallback<PutObjectRequest, PutObjectResult> completedCallback) {

        return mOss.asyncPutObject(request, completedCallback);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.putObject(request);
    }

    @Override
    public OSSAsyncTask<GetObjectResult> asyncGetObject(
            GetObjectRequest request, OSSCompletedCallback<GetObjectRequest, GetObjectResult> completedCallback) {

        return mOss.asyncGetObject(request, completedCallback);
    }

    @Override
    public GetObjectResult getObject(GetObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.getObject(request);
    }

    @Override
    public OSSAsyncTask<GetObjectACLResult> asyncGetObjectACL(
            GetObjectACLRequest request, OSSCompletedCallback<GetObjectACLRequest, GetObjectACLResult> completedCallback) {
        return mOss.asyncGetObjectACL(request, completedCallback);
    }

    @Override
    public GetObjectACLResult getObjectACL(GetObjectACLRequest request)
            throws ClientException, ServiceException {
        return mOss.getObjectACL(request);
    }

    @Override
    public OSSAsyncTask<DeleteObjectResult> asyncDeleteObject(
            DeleteObjectRequest request, OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult> completedCallback) {

        return mOss.asyncDeleteObject(request, completedCallback);
    }

    @Override
    public DeleteObjectResult deleteObject(DeleteObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.deleteObject(request);
    }

    @Override
    public OSSAsyncTask<DeleteMultipleObjectResult> asyncDeleteMultipleObject(
            DeleteMultipleObjectRequest request, OSSCompletedCallback<DeleteMultipleObjectRequest, DeleteMultipleObjectResult> completedCallback) {

        return mOss.asyncDeleteMultipleObject(request, completedCallback);
    }

    @Override
    public DeleteMultipleObjectResult deleteMultipleObject(DeleteMultipleObjectRequest request)
            throws ClientException, ServiceException {
        return mOss.deleteMultipleObject(request);
    }

    @Override
    public OSSAsyncTask<AppendObjectResult> asyncAppendObject(
            AppendObjectRequest request, OSSCompletedCallback<AppendObjectRequest, AppendObjectResult> completedCallback) {

        return mOss.asyncAppendObject(request, completedCallback);
    }

    @Override
    public AppendObjectResult appendObject(AppendObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.appendObject(request);
    }

    @Override
    public OSSAsyncTask<HeadObjectResult> asyncHeadObject(HeadObjectRequest request, OSSCompletedCallback<HeadObjectRequest, HeadObjectResult> completedCallback) {

        return mOss.asyncHeadObject(request, completedCallback);
    }

    @Override
    public HeadObjectResult headObject(HeadObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.headObject(request);
    }

    @Override
    public OSSAsyncTask<CopyObjectResult> asyncCopyObject(CopyObjectRequest request, OSSCompletedCallback<CopyObjectRequest, CopyObjectResult> completedCallback) {

        return mOss.asyncCopyObject(request, completedCallback);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest request)
            throws ClientException, ServiceException {

        return mOss.copyObject(request);
    }

    @Override
    public OSSAsyncTask<ListObjectsResult> asyncListObjects(
            ListObjectsRequest request, OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> completedCallback) {

        return mOss.asyncListObjects(request, completedCallback);
    }

    @Override
    public ListObjectsResult listObjects(ListObjectsRequest request)
            throws ClientException, ServiceException {

        return mOss.listObjects(request);
    }

    @Override
    public OSSAsyncTask<InitiateMultipartUploadResult> asyncInitMultipartUpload(InitiateMultipartUploadRequest request, OSSCompletedCallback<InitiateMultipartUploadRequest, InitiateMultipartUploadResult> completedCallback) {

        return mOss.asyncInitMultipartUpload(request, completedCallback);
    }

    @Override
    public InitiateMultipartUploadResult initMultipartUpload(InitiateMultipartUploadRequest request)
            throws ClientException, ServiceException {

        return mOss.initMultipartUpload(request);
    }

    @Override
    public OSSAsyncTask<UploadPartResult> asyncUploadPart(UploadPartRequest request, OSSCompletedCallback<UploadPartRequest, UploadPartResult> completedCallback) {

        return mOss.asyncUploadPart(request, completedCallback);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest request)
            throws ClientException, ServiceException {

        return mOss.uploadPart(request);
    }

    @Override
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncCompleteMultipartUpload(CompleteMultipartUploadRequest request, OSSCompletedCallback<CompleteMultipartUploadRequest, CompleteMultipartUploadResult> completedCallback) {

        return mOss.asyncCompleteMultipartUpload(request, completedCallback);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request)
            throws ClientException, ServiceException {

        return mOss.completeMultipartUpload(request);
    }

    @Override
    public OSSAsyncTask<AbortMultipartUploadResult> asyncAbortMultipartUpload(AbortMultipartUploadRequest request, OSSCompletedCallback<AbortMultipartUploadRequest, AbortMultipartUploadResult> completedCallback) {

        return mOss.asyncAbortMultipartUpload(request, completedCallback);
    }

    @Override
    public AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request)
            throws ClientException, ServiceException {

        return mOss.abortMultipartUpload(request);
    }

    @Override
    public OSSAsyncTask<ListPartsResult> asyncListParts(ListPartsRequest request, OSSCompletedCallback<ListPartsRequest, ListPartsResult> completedCallback) {

        return mOss.asyncListParts(request, completedCallback);
    }

    @Override
    public ListPartsResult listParts(ListPartsRequest request)
            throws ClientException, ServiceException {

        return mOss.listParts(request);
    }

    @Override
    public OSSAsyncTask<ListMultipartUploadsResult> asyncListMultipartUploads(ListMultipartUploadsRequest request, OSSCompletedCallback<ListMultipartUploadsRequest, ListMultipartUploadsResult> completedCallback) {
        return mOss.asyncListMultipartUploads(request, completedCallback);
    }

    @Override
    public ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest request) throws ClientException, ServiceException {
        return mOss.listMultipartUploads(request);
    }

    @Override
    public void updateCredentialProvider(OSSCredentialProvider credentialProvider) {
        mOss.updateCredentialProvider(credentialProvider);
    }

    @Override
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncMultipartUpload(
            MultipartUploadRequest request, OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult> completedCallback) {

        return mOss.asyncMultipartUpload(request, completedCallback);
    }

    @Override
    public CompleteMultipartUploadResult multipartUpload(MultipartUploadRequest request)
            throws ClientException, ServiceException {

        return mOss.multipartUpload(request);
    }

    @Override
    public OSSAsyncTask<ResumableUploadResult> asyncResumableUpload(
            ResumableUploadRequest request, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> completedCallback) {

        return mOss.asyncResumableUpload(request, completedCallback);
    }

    @Override
    public ResumableUploadResult resumableUpload(ResumableUploadRequest request)
            throws ClientException, ServiceException {
        return mOss.resumableUpload(request);
    }

    @Override
    public OSSAsyncTask<ResumableUploadResult> asyncSequenceUpload(ResumableUploadRequest request, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> completedCallback) {
        return mOss.asyncSequenceUpload(request, completedCallback);
    }

    @Override
    public ResumableUploadResult sequenceUpload(ResumableUploadRequest request) throws ClientException, ServiceException {
        return mOss.sequenceUpload(request);
    }

    @Override
    public String presignConstrainedObjectURL(GeneratePresignedUrlRequest request) throws ClientException {
        return mOss.presignConstrainedObjectURL(request);
    }

    @Override
    public String presignConstrainedObjectURL(String bucketName, String objectKey, long expiredTimeInSeconds)
            throws ClientException {

        return mOss.presignConstrainedObjectURL(bucketName, objectKey, expiredTimeInSeconds);
    }

    @Override
    public String presignPublicObjectURL(String bucketName, String objectKey) {

        return mOss.presignPublicObjectURL(bucketName, objectKey);
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectKey)
            throws ClientException, ServiceException {

        return mOss.doesObjectExist(bucketName, objectKey);
    }

    @Override
    public void abortResumableUpload(ResumableUploadRequest request) throws IOException {

        mOss.abortResumableUpload(request);
    }

    @Override
    public OSSAsyncTask<TriggerCallbackResult> asyncTriggerCallback(TriggerCallbackRequest request, OSSCompletedCallback<TriggerCallbackRequest, TriggerCallbackResult> completedCallback) {
        return mOss.asyncTriggerCallback(request, completedCallback);
    }

    @Override
    public TriggerCallbackResult triggerCallback(TriggerCallbackRequest request) throws ClientException, ServiceException {
        return mOss.triggerCallback(request);
    }

    @Override
    public OSSAsyncTask<ImagePersistResult> asyncImagePersist(ImagePersistRequest request, OSSCompletedCallback<ImagePersistRequest, ImagePersistResult> completedCallback) {
        return mOss.asyncImagePersist(request, completedCallback);
    }

    @Override
    public ImagePersistResult imagePersist(ImagePersistRequest request) throws ClientException, ServiceException {
        return mOss.imagePersist(request);
    }

    @Override
    public PutSymlinkResult putSymlink(PutSymlinkRequest request) throws ClientException, ServiceException {
        return mOss.putSymlink(request);
    }

    @Override
    public OSSAsyncTask<PutSymlinkResult> asyncPutSymlink(PutSymlinkRequest request, OSSCompletedCallback<PutSymlinkRequest, PutSymlinkResult> completedCallback) {
        return mOss.asyncPutSymlink(request, completedCallback);
    }

    @Override
    public GetSymlinkResult getSymlink(GetSymlinkRequest request) throws ClientException, ServiceException {
        return mOss.getSymlink(request);
    }

    @Override
    public OSSAsyncTask<GetSymlinkResult> asyncGetSymlink(GetSymlinkRequest request, OSSCompletedCallback<GetSymlinkRequest, GetSymlinkResult> completedCallback) {
        return mOss.asyncGetSymlink(request, completedCallback);
    }

    @Override
    public RestoreObjectResult restoreObject(RestoreObjectRequest request) throws ClientException, ServiceException {
        return mOss.restoreObject(request);
    }

    @Override
    public OSSAsyncTask<RestoreObjectResult> asyncRestoreObject(RestoreObjectRequest request, OSSCompletedCallback<RestoreObjectRequest, RestoreObjectResult> completedCallback) {
        return mOss.asyncRestoreObject(request, completedCallback);
    }
}
