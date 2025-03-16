package org.devtools.grpcclient.mobile;

import com.google.rpc.Code;
import io.grpc.*;
import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.grpcclient.validation.exceptions.ApplicationException;

@Slf4j
@AllArgsConstructor
public class GrpcInterceptor implements ServerInterceptor {
//    private final JWTUtil jwtUtil;

    public static final String BEARER_TYPE = "Bearer";
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> CONSUMER_ID_CONTEXT_KEY = Context.key("consumer_id");
    public static final Context.Key<String> TOKEN_TYPE_CONTEXT_KEY = Context.key("typ");
    public static final Context.Key<String> TOKEN_CONTEXT_KEY = Context.key("token");


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info(serverCall.getMethodDescriptor().getFullMethodName());
        if ("mobileSignupConsumer".equals(serverCall.getMethodDescriptor().getBareMethodName()) ||
                "mobileLoginConsumer".equals(serverCall.getMethodDescriptor().getBareMethodName()) ||
                "mobileRestorePassword".equals(serverCall.getMethodDescriptor().getBareMethodName()) ||
                "mobileResendConfirmEmail".equals(serverCall.getMethodDescriptor().getBareMethodName()) ||
                "mobileGetEmailInfo".equals(serverCall.getMethodDescriptor().getBareMethodName()) ||
                "mobileEnterOAuthConsumer".equals(serverCall.getMethodDescriptor().getBareMethodName())) {
            return serverCallHandler.startCall(serverCall, headers);
        }
        String value = headers.get(AUTHORIZATION_METADATA_KEY);
        Status status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
//        if (value == null) {
//            log.warn("Authorization token is missing");
//            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
//        } else if (!value.startsWith(BEARER_TYPE)) {
//            log.warn("Unknown authorization type");
//            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
//        } else {
//            String jwt = value.substring(BEARER_TYPE.length()).trim();
//            try {
//                DecodedJWT decodedJWT = jwtUtil.validateToken(jwt);
//                String typeJwt = decodedJWT.getClaim("typ").asString();
//                if (!("mobileGetNewTokensByRefreshToken".equals(serverCall.getMethodDescriptor().getBareMethodName())
//                        && "refresh".equals(typeJwt))
//                        && !"access".equals(typeJwt)
//                )) {
//                    log.warn("Bad type token");
//                    throw new ApplicationException(Status.UNAUTHENTICATED, "Bad type token");
//                }
//                Context ctx = Context.current()
//                        .withValue(TOKEN_CONTEXT_KEY, jwt)
//                        .withValue(CONSUMER_ID_CONTEXT_KEY, decodedJWT.getClaim("consumer_id").asString())
//                        .withValue(TOKEN_TYPE_CONTEXT_KEY, typeJwt);
//                return Contexts.interceptCall(ctx, serverCall, headers, serverCallHandler);
//            } catch (Exception e) {
//                log.error("Error", e);
//                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
//            }
//        }
        serverCall.close(status, headers);
        return serverCallHandler.startCall(serverCall, headers);
    }
}