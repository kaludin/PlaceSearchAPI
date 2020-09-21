package me.step4.SearchPlace.controller.v1;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.advice.exception.BadRequestException;
import me.step4.SearchPlace.advice.exception.UserNotFoundException;
import me.step4.SearchPlace.model.CommonResult;
import me.step4.SearchPlace.model.ListResult;
import me.step4.SearchPlace.model.SingleResult;
import me.step4.SearchPlace.repo.entity.User;
import me.step4.SearchPlace.service.ResponseService;
import me.step4.SearchPlace.service.UserService;

/**
 * 사용자 컨트롤러
 * @author Sihun
 *
 */
@Api(tags="2. User")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserRestController {
	
	@Autowired
	private final UserService userService;
	@Autowired
	private final ResponseService responseService;

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 생성", notes="사용자을 생성합니다.")
	@PostMapping("/user")
	public CommonResult createUser(
			@ApiParam(value="사용자 정보", required = true)
			@RequestBody User user,
			final HttpServletResponse resp,
			UriComponentsBuilder builder) {
		boolean isSuccess = userService.createUser(user);
        if (isSuccess == false) {
        	throw new BadRequestException();
        }
    	resp.setStatus(HttpServletResponse.SC_CREATED);
		return responseService.getSuccessResult();
	}

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 조회", notes="모든 사용자을 조회합니다.")
	@GetMapping("/user")
	public ListResult<User> userList(final HttpServletResponse resp) {
		List<User> users = userService.getAllUser();
    	resp.setStatus(HttpServletResponse.SC_OK);
		return responseService.getListResult(users);
	}

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 조회", notes="지정 사용자을 조회합니다.")
	@GetMapping("/user/{id}")
	public SingleResult<User> userList(
			@ApiParam(value="사용자ID", required = true)
			@PathVariable Long id,
			final HttpServletResponse resp) {
		Optional<User> user = userService.getUser(id);
		if(user.isPresent()) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return responseService.getSingleResult(userService.getUser(id).orElse(null));
		} else {
        	throw new UserNotFoundException();
		}
	}

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 변경", notes="사용자를 변경합니다.")
	@PutMapping("/user/{id}")
	public SingleResult<User> updateUser(
			@ApiParam(value="사용자ID", required = true)
			@PathVariable Long id, 
			@ApiParam(value="변경할 사용자 정보", required = true)
			@RequestBody User user,
			final HttpServletResponse resp) {
		User updateUser = userService.updateUser(id, user); 
		if(updateUser != null){
	    	resp.setStatus(HttpServletResponse.SC_OK);
			return responseService.getSingleResult(updateUser);
		}
		else{
        	throw new UserNotFoundException();
		}		
	}

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 수정", notes="사용자 정보를 수정합니다.")
	@PatchMapping("/user/{id}")
	public SingleResult<User> patchUser(
			@ApiParam(value="사용자ID", required = true)
			@PathVariable Long id,
			@ApiParam(value="사용자 정보", required = true)
			@RequestBody User user,
			final HttpServletResponse resp) {
		User updateUser = userService.patchUser(id, user); 
		if(updateUser != null){
	    	resp.setStatus(HttpServletResponse.SC_OK);
			return responseService.getSingleResult(updateUser);
		}
		else{
        	throw new UserNotFoundException();
		}		
	}

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="사용자 삭제", notes="사용자 정보를 삭제합니다.")
	@DeleteMapping("/user/{id}")
	public CommonResult deleteUser(
			@ApiParam(value="사용자ID", required = true)
			@PathVariable Long id,
			final HttpServletResponse resp) {
		if(userService.deleteUser(id)){	//exist
	    	resp.setStatus(HttpServletResponse.SC_OK);
	    	return responseService.getSuccessResult();
		}
		else{
        	throw new UserNotFoundException();
		}		
	}
}
