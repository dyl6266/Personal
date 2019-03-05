package com.dy.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.dy.dao.UserDAO;
import com.dy.domain.CustomUserDetails;

/**
 * UserDetailsService - DB에서 사용자 정보를 직접 가져오는 인터페이스
 * 
 * @author for
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	/**
	 * DB에 있는 이용자의 정보를 UserDetails 타입으로 반환
	 * 
	 * @param memberId - 회원 아이디
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		CustomUserDetails userDetails = null;
		if (StringUtils.isEmpty(userId)) {
			throw new UsernameNotFoundException("user ID is empty");
		}

		/* userId에 해당하는 회원 상세 정보 조회 */
		userDetails = userDAO.selectUserDetail(userId);

		if (ObjectUtils.isEmpty(userDetails)) {
			throw new UsernameNotFoundException("There are no users for the user ID : " + userId);
			/* 이걸 Provider에서 사용해야 하는건지? 여기서 사용해야 하는건지? */
//			throw new InternalAuthenticationServiceException("존재하지 않는 아이디입니다.");
		}

		return userDetails;
	}

}
