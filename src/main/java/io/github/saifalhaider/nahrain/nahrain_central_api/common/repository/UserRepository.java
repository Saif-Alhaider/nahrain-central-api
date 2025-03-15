package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository<T extends User, ID> extends JpaRepository<T, ID> {

    Optional<T> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.totpSecret = :totpSecret WHERE user.email = :email")
    void updateTotpSecret(@Param("email") String email, @Param("totpSecret") String totpSecret);

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.mfaEnabled = :mfaEnabled WHERE user.email = :email")
    void updateMfaEnabled(@Param("email") String email, @Param("mfaEnabled") boolean mfaEnabled);

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.password = :password WHERE user.email = :email")
    void updateUserPassword(@Param("email") String email, @Param("password") String password);
}