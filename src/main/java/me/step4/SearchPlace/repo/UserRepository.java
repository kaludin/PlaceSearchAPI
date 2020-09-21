package me.step4.SearchPlace.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.step4.SearchPlace.repo.entity.User;

/**
 * 사용자 Repository
 * @author Sihun
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}