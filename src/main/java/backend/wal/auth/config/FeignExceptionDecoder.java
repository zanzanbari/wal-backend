package backend.wal.auth.config;

import backend.wal.auth.exception.InternalAuthServerException;
import backend.wal.auth.exception.UnAuthorizedTokenException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignExceptionDecoder implements ErrorDecoder {

    private static final int FEIGN_UNAUTHORIZED = 401;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == FEIGN_UNAUTHORIZED) {
            return UnAuthorizedTokenException.expired();
        }
        return InternalAuthServerException.anotherFeignException();
    }
}
