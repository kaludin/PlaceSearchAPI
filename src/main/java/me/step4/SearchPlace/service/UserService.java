package me.step4.SearchPlace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.repo.UserRepository;
import me.step4.SearchPlace.repo.entity.User;

/**
 * User Service
 * @author Sihun
 *
 */
@RequiredArgsConstructor
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Create User
	 * @param user
	 * @return
	 */
	public boolean createUser(User user){
		try{
			userRepository.save(user);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Get All User
	 * @return
	 */
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	/**
	 * Get User
	 * @param id
	 * @return
	 */
	public Optional<User> getUser(Long id){
		return userRepository.findById(id);
	}
	
	/**
	 * Update User
	 * @param id
	 * @param user
	 * @return
	 */
	public User updateUser(Long id, User user){
		final Optional<User> fetchedUser = userRepository.findById(id);
		if(fetchedUser.isPresent()){
			user.setSeq(id);
			return userRepository.save(user);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Patch User
	 * @param id
	 * @param user
	 * @return
	 */
	public User patchUser(Long id, User user){
		final Optional<User> fetchedUser = userRepository.findById(id);
		if(fetchedUser.isPresent()){
			if(user.getName() != null){
				fetchedUser.get().setName(user.getName());
			}
			if(user.getPassword() != null){
				fetchedUser.get().setPassword(user.getPassword());
			}
			if(user.getUpdateDate() != null){
				fetchedUser.get().setUpdateDate(user.getUpdateDate());
			}
			return userRepository.save(fetchedUser.get());
		}
		else{
			return null;
		}
	}
	
	/**
	 * Delete User
	 * @param id
	 * @return
	 */
	public boolean deleteUser(Long id){
		final Optional<User> fetchedUser = userRepository.findById(id);
		if(fetchedUser.isPresent()){
			userRepository.deleteById(id);
			return true;
		}
		else{
			return false;
		}
	}
}