/**
 * Copyright (C) Alibaba Cloud Computing, 2015
 * All rights reserved.
 * <p>
 * 版权所有 （C）阿里巴巴云计算，2015
 */

package com.blockchain.ossuploadlib;

import android.content.Context;

import com.blockchain.ossuploadlib.callback.OSSCompletedCallback;
import com.blockchain.ossuploadlib.common.OSSLogToFileUtils;
import com.blockchain.ossuploadlib.common.auth.OSSCredentialProvider;
import com.blockchain.ossuploadlib.common.utils.OSSUtils;
import com.blockchain.ossuploadlib.internal.ExtensionRequestOperation;
import com.blockchain.ossuploadlib.internal.InternalRequestOperation;
import com.blockchain.ossuploadlib.internal.OSSAsyncTask;
import com.blockchain.ossuploadlib.internal.ObjectURLPresigner;
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
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The entry point class of (Open Storage Service, OSS）, which is the implementation of interface
 * OSS.
 */
class OSSImpl implements OSS {

    private URI endpointURI;
    private OSSCredentialProvider credentialProvider;
    private InternalRequestOperation internalRequestOperation;
    private ExtensionRequestOperation extensionRequestOperation;
    private ClientConfiguration conf;

    /**
     * Creates a {@link OSSImpl} instance.
     *
     * @param context            a android application's application context
     * @param endpoint           OSS endpoint, check out:http://help.aliyun.com/document_detail/oss/user_guide/endpoint_region.html
     * @param credentialProvider credential provider instance
     * @param conf               Client side configuration
     */
    public OSSImpl(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        OSSLogToFileUtils.init(context.getApplicationContext(), conf);//init log
        try {
            endpoint = endpoint.trim();
            if (!endpoint.startsWith("http")) {
                endpoint = "http://" + endpoint;
            }
            this.endpointURI = new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Endpoint must be a string like 'http://oss-cn-****.aliyuncs.com'," +
                    "or your cname like 'http://image.cnamedomain.com'!");
        }
        if (credentialProvider == null) {
            throw new IllegalArgumentException("CredentialProvider can't be null.");
        }

        Boolean hostIsIP = false;
        try {
            hostIsIP = OSSUtils.isValidateIP(this.endpointURI.getHost());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.endpointURI.getScheme().equals("https") && hostIsIP) {
            throw new IllegalArgumentException("endpoint should not be format with https://ip.");
        }

        this.credentialProvider = credentialProvider;
        this.conf = (conf == null ? ClientConfiguration.getDefaultConf() : conf);

        internalRequestOperation = new InternalRequestOperation(context.getApplicationContext(), endpointURI, credentialProvider, this.conf);
        extensionRequestOperation = new ExtensionRequestOperation(internalRequestOperation);
    }

    public OSSImpl(Context context, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        this.credentialProvider = credentialProvider;
        this.conf = (conf == null ? ClientConfiguration.getDefaultConf() : conf);
        internalRequestOperation = new InternalRequestOperation(context.getApplicationContext(), credentialProvider, this.conf);
        extensionRequestOperation = new ExtensionRequestOperation(internalRequestOperation);
    }

    @Override
    public OSSAsyncTask<ListBucketsResult> asyncListBuckets(
            ListBucketsRequest request, OSSCompletedCallback<ListBucketsRequest, ListBucketsResult> completedCallback) {
        return internalRequestOperation.listBuckets(request, completedCallback);
    }

    @Override
    public ListBucketsResult listBuckets(ListBucketsRequest request)
            throws ClientException, ServiceException {
        return internalRequestOperation.listBuckets(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<CreateBucketResult> asyncCreateBucket(
            CreateBucketRequest request, OSSCompletedCallback<CreateBucketRequest, CreateBucketResult> completedCallback) {

        return internalRequestOperation.createBucket(request, completedCallback);
    }

    @Override
    public CreateBucketResult createBucket(CreateBucketRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.createBucket(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<DeleteBucketResult> asyncDeleteBucket(
            DeleteBucketRequest request, OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult> completedCallback) {

        return internalRequestOperation.deleteBucket(request, completedCallback);
    }

    @Override
    public DeleteBucketResult deleteBucket(DeleteBucketRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.deleteBucket(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetBucketInfoResult> asyncGetBucketInfo(GetBucketInfoRequest request, OSSCompletedCallback<GetBucketInfoRequest, GetBucketInfoResult> completedCallback) {
        return internalRequestOperation.getBucketInfo(request, completedCallback);
    }

    @Override
    public GetBucketInfoResult getBucketInfo(GetBucketInfoRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.getBucketInfo(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetBucketACLResult> asyncGetBucketACL(
            GetBucketACLRequest request, OSSCompletedCallback<GetBucketACLRequest, GetBucketACLResult> completedCallback) {

        return internalRequestOperation.getBucketACL(request, completedCallback);
    }

    @Override
    public GetBucketACLResult getBucketACL(GetBucketACLRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.getBucketACL(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<PutBucketRefererResult> asyncPutBucketReferer(PutBucketRefererRequest request, OSSCompletedCallback<PutBucketRefererRequest, PutBucketRefererResult> completedCallback) {
        return internalRequestOperation.putBucketReferer(request, completedCallback);
    }

    @Override
    public PutBucketRefererResult putBucketReferer(PutBucketRefererRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.putBucketReferer(request, null).getResult();
    }

    @Override
    public GetBucketRefererResult getBucketReferer(GetBucketRefererRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.getBucketReferer(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetBucketRefererResult> asyncGetBucketReferer(GetBucketRefererRequest request, OSSCompletedCallback<GetBucketRefererRequest, GetBucketRefererResult> completedCallback) {
        return internalRequestOperation.getBucketReferer(request, completedCallback);
    }

    @Override
    public DeleteBucketLoggingResult deleteBucketLogging(DeleteBucketLoggingRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.deleteBucketLogging(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<DeleteBucketLoggingResult> asyncDeleteBucketLogging(DeleteBucketLoggingRequest request, OSSCompletedCallback<DeleteBucketLoggingRequest, DeleteBucketLoggingResult> completedCallback) {
        return internalRequestOperation.deleteBucketLogging(request, completedCallback);
    }

    @Override
    public PutBucketLoggingResult putBucketLogging(PutBucketLoggingRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.putBucketLogging(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<PutBucketLoggingResult> asyncPutBucketLogging(PutBucketLoggingRequest request, OSSCompletedCallback<PutBucketLoggingRequest, PutBucketLoggingResult> completedCallback) {
        return internalRequestOperation.putBucketLogging(request, completedCallback);
    }

    @Override
    public GetBucketLoggingResult getBucketLogging(GetBucketLoggingRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.getBucketLogging(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetBucketLoggingResult> asyncGetBucketLogging(GetBucketLoggingRequest request, OSSCompletedCallback<GetBucketLoggingRequest, GetBucketLoggingResult> completedCallback) {
        return internalRequestOperation.getBucketLogging(request, completedCallback);
    }

    @Override
    public PutBucketLifecycleResult putBucketLifecycle(PutBucketLifecycleRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.putBucketLifecycle(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<PutBucketLifecycleResult> asyncPutBucketLifecycle(PutBucketLifecycleRequest request, OSSCompletedCallback<PutBucketLifecycleRequest, PutBucketLifecycleResult> completedCallback) {
        return internalRequestOperation.putBucketLifecycle(request, completedCallback);
    }

    @Override
    public GetBucketLifecycleResult getBucketLifecycle(GetBucketLifecycleRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.getBucketLifecycle(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetBucketLifecycleResult> asyncGetBucketLifecycle(GetBucketLifecycleRequest request, OSSCompletedCallback<GetBucketLifecycleRequest, GetBucketLifecycleResult> completedCallback) {
        return internalRequestOperation.getBucketLifecycle(request, completedCallback);
    }

    @Override
    public DeleteBucketLifecycleResult deleteBucketLifecycle(DeleteBucketLifecycleRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.deleteBucketLifecycle(request,null).getResult();
    }

    @Override
    public OSSAsyncTask<DeleteBucketLifecycleResult> asyncDeleteBucketLifecycle(DeleteBucketLifecycleRequest request, OSSCompletedCallback<DeleteBucketLifecycleRequest, DeleteBucketLifecycleResult> completedCallback) {
        return internalRequestOperation.deleteBucketLifecycle(request, completedCallback);
    }

    @Override
    public OSSAsyncTask<PutObjectResult> asyncPutObject(
            PutObjectRequest request, final OSSCompletedCallback<PutObjectRequest, PutObjectResult> completedCallback) {
        return internalRequestOperation.putObject(request, completedCallback);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest request)
            throws ClientException, ServiceException {
        return internalRequestOperation.syncPutObject(request);
    }

    @Override
    public OSSAsyncTask<GetObjectResult> asyncGetObject(
            GetObjectRequest request, final OSSCompletedCallback<GetObjectRequest, GetObjectResult> completedCallback) {

        return internalRequestOperation.getObject(request, completedCallback);
    }

    @Override
    public GetObjectResult getObject(GetObjectRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.getObject(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<GetObjectACLResult> asyncGetObjectACL(
            GetObjectACLRequest request, OSSCompletedCallback<GetObjectACLRequest, GetObjectACLResult> completedCallback) {
        return internalRequestOperation.getObjectACL(request, completedCallback);
    }

    @Override
    public GetObjectACLResult getObjectACL(GetObjectACLRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.getObjectACL(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<DeleteObjectResult> asyncDeleteObject(
            DeleteObjectRequest request, OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult> completedCallback) {

        return internalRequestOperation.deleteObject(request, completedCallback);
    }

    @Override
    public DeleteObjectResult deleteObject(DeleteObjectRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.deleteObject(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<DeleteMultipleObjectResult> asyncDeleteMultipleObject(
            DeleteMultipleObjectRequest request, OSSCompletedCallback<DeleteMultipleObjectRequest, DeleteMultipleObjectResult> completedCallback) {

        return internalRequestOperation.deleteMultipleObject(request, completedCallback);
    }

    @Override
    public DeleteMultipleObjectResult deleteMultipleObject(DeleteMultipleObjectRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.deleteMultipleObject(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<AppendObjectResult> asyncAppendObject(
            AppendObjectRequest request, final OSSCompletedCallback<AppendObjectRequest, AppendObjectResult> completedCallback) {
        return internalRequestOperation.appendObject(request, completedCallback);
    }

    @Override
    public AppendObjectResult appendObject(AppendObjectRequest request)
            throws ClientException, ServiceException {
        return internalRequestOperation.syncAppendObject(request);
    }

    @Override
    public OSSAsyncTask<HeadObjectResult> asyncHeadObject(HeadObjectRequest request, OSSCompletedCallback<HeadObjectRequest, HeadObjectResult> completedCallback) {

        return internalRequestOperation.headObject(request, completedCallback);
    }

    @Override
    public HeadObjectResult headObject(HeadObjectRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.headObject(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<CopyObjectResult> asyncCopyObject(CopyObjectRequest request, OSSCompletedCallback<CopyObjectRequest, CopyObjectResult> completedCallback) {

        return internalRequestOperation.copyObject(request, completedCallback);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.copyObject(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<ListObjectsResult> asyncListObjects(
            ListObjectsRequest request, OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> completedCallback) {

        return internalRequestOperation.listObjects(request, completedCallback);
    }

    @Override
    public ListObjectsResult listObjects(ListObjectsRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.listObjects(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<InitiateMultipartUploadResult> asyncInitMultipartUpload(InitiateMultipartUploadRequest request, OSSCompletedCallback<InitiateMultipartUploadRequest, InitiateMultipartUploadResult> completedCallback) {

        return internalRequestOperation.initMultipartUpload(request, completedCallback);
    }

    @Override
    public InitiateMultipartUploadResult initMultipartUpload(InitiateMultipartUploadRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.initMultipartUpload(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<UploadPartResult> asyncUploadPart(UploadPartRequest request, final OSSCompletedCallback<UploadPartRequest, UploadPartResult> completedCallback) {

        return internalRequestOperation.uploadPart(request, completedCallback);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest request)
            throws ClientException, ServiceException {
        return internalRequestOperation.syncUploadPart(request);
    }

    @Override
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncCompleteMultipartUpload(CompleteMultipartUploadRequest request
            , final OSSCompletedCallback<CompleteMultipartUploadRequest, CompleteMultipartUploadResult> completedCallback) {

        return internalRequestOperation.completeMultipartUpload(request, completedCallback);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request)
            throws ClientException, ServiceException {
        return internalRequestOperation.syncCompleteMultipartUpload(request);
    }


    @Override
    public OSSAsyncTask<AbortMultipartUploadResult> asyncAbortMultipartUpload(AbortMultipartUploadRequest request, OSSCompletedCallback<AbortMultipartUploadRequest, AbortMultipartUploadResult> completedCallback) {

        return internalRequestOperation.abortMultipartUpload(request, completedCallback);
    }

    @Override
    public AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.abortMultipartUpload(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<ListPartsResult> asyncListParts(ListPartsRequest request, OSSCompletedCallback<ListPartsRequest, ListPartsResult> completedCallback) {

        return internalRequestOperation.listParts(request, completedCallback);
    }

    @Override
    public ListPartsResult listParts(ListPartsRequest request)
            throws ClientException, ServiceException {

        return internalRequestOperation.listParts(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<ListMultipartUploadsResult> asyncListMultipartUploads(ListMultipartUploadsRequest request, OSSCompletedCallback<ListMultipartUploadsRequest, ListMultipartUploadsResult> completedCallback) {
        return internalRequestOperation.listMultipartUploads(request, completedCallback);
    }

    @Override
    public ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.listMultipartUploads(request, null).getResult();
    }

    @Override
    public void updateCredentialProvider(OSSCredentialProvider credentialProvider) {
        this.credentialProvider = credentialProvider;
        internalRequestOperation.setCredentialProvider(credentialProvider);
    }

    @Override
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncMultipartUpload(
            MultipartUploadRequest request, OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult> completedCallback) {

        return extensionRequestOperation.multipartUpload(request, completedCallback);
    }

    @Override
    public CompleteMultipartUploadResult multipartUpload(MultipartUploadRequest request)
            throws ClientException, ServiceException {

        return extensionRequestOperation.multipartUpload(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<ResumableUploadResult> asyncResumableUpload(
            ResumableUploadRequest request, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> completedCallback) {

        return extensionRequestOperation.resumableUpload(request, completedCallback);
    }

    @Override
    public ResumableUploadResult resumableUpload(ResumableUploadRequest request)
            throws ClientException, ServiceException {

        return extensionRequestOperation.resumableUpload(request, null).getResult();
    }

    @Override
    public OSSAsyncTask<ResumableUploadResult> asyncSequenceUpload(
            ResumableUploadRequest request, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> completedCallback) {

        return extensionRequestOperation.sequenceUpload(request, completedCallback);
    }


    @Override
    public ResumableUploadResult sequenceUpload(ResumableUploadRequest request)
            throws ClientException, ServiceException {

        return extensionRequestOperation.sequenceUpload(request, null).getResult();
    }

    @Override
    public String presignConstrainedObjectURL(GeneratePresignedUrlRequest request) throws ClientException {
        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf)
                .presignConstrainedURL(request);
    }

    @Override
    public String presignConstrainedObjectURL(String bucketName, String objectKey, long expiredTimeInSeconds)
            throws ClientException {

        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf)
                .presignConstrainedURL(bucketName, objectKey, expiredTimeInSeconds);
    }

    @Override
    public String presignPublicObjectURL(String bucketName, String objectKey) {

        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf)
                .presignPublicURL(bucketName, objectKey);
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectKey)
            throws ClientException, ServiceException {

        return extensionRequestOperation.doesObjectExist(bucketName, objectKey);
    }

    @Override
    public void abortResumableUpload(ResumableUploadRequest request) throws IOException {

        extensionRequestOperation.abortResumableUpload(request);
    }

    @Override
    public OSSAsyncTask<TriggerCallbackResult> asyncTriggerCallback(TriggerCallbackRequest request, OSSCompletedCallback<TriggerCallbackRequest, TriggerCallbackResult> completedCallback) {
        return internalRequestOperation.triggerCallback(request, completedCallback);
    }

    @Override
    public TriggerCallbackResult triggerCallback(TriggerCallbackRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.asyncTriggerCallback(request);
    }

    @Override
    public OSSAsyncTask<ImagePersistResult> asyncImagePersist(ImagePersistRequest request, OSSCompletedCallback<ImagePersistRequest, ImagePersistResult> completedCallback) {
        return internalRequestOperation.imageActionPersist(request, completedCallback);
    }

    @Override
    public ImagePersistResult imagePersist(ImagePersistRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.imageActionPersist(request, null).getResult();
    }

    @Override
    public PutSymlinkResult putSymlink(PutSymlinkRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.syncPutSymlink(request);
    }

    @Override
    public OSSAsyncTask<PutSymlinkResult> asyncPutSymlink(PutSymlinkRequest request, OSSCompletedCallback<PutSymlinkRequest, PutSymlinkResult> completedCallback) {
        return internalRequestOperation.putSymlink(request, completedCallback);
    }

    @Override
    public GetSymlinkResult getSymlink(GetSymlinkRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.syncGetSymlink(request);
    }

    @Override
    public OSSAsyncTask<GetSymlinkResult> asyncGetSymlink(GetSymlinkRequest request, OSSCompletedCallback<GetSymlinkRequest, GetSymlinkResult> completedCallback) {
        return internalRequestOperation.getSymlink(request, completedCallback);
    }

    @Override
    public RestoreObjectResult restoreObject(RestoreObjectRequest request) throws ClientException, ServiceException {
        return internalRequestOperation.syncRestoreObject(request);
    }

    @Override
    public OSSAsyncTask<RestoreObjectResult> asyncRestoreObject(RestoreObjectRequest request, OSSCompletedCallback<RestoreObjectRequest, RestoreObjectResult> completedCallback) {
        return internalRequestOperation.restoreObject(request, completedCallback);
    }
}
