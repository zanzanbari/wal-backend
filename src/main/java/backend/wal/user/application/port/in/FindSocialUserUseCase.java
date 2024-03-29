package backend.wal.user.application.port.in;

import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;

public interface FindSocialUserUseCase {

    User findUserBySocialIdAndSocialType(String socialId, SocialType socialType);
}
