package backend.wal.auth.application.port.out;

import backend.wal.user.application.port.dto.CreateUserDto;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;

public interface UserPort {

    User findSocialUserCall(String socialId, SocialType socialType);

    Long signupCall(CreateUserDto createUserDto);
}
