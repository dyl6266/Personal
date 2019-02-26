//package com.dy.common.security.bak;
//package com.dy.common.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import com.dy.dao.MemberDAO;
//import com.dy.domain.MemberVO;
//
//public class MemberAuthService implements UserDetailsService {
//
//	@Autowired
//	private MemberDAO memberDAO;
//
//	/**
//	 * DB에 있는 이용자의 정보를 UserDetails 타입으로 반환
//	 * 
//	 * @param memberId - 회원 아이디
//	 */
//	@Override
//	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
//
//		/* 사용자 정보를 담는 객체 */
//		UserDetails user = null;
//
//		if (StringUtils.isEmpty(memberId)) {
//			throw new UsernameNotFoundException("member id is null");
//		} else {
//			/* memberId에 해당하는 회원 조회 */
//			MemberVO member = memberDAO.selectMemberDetail(memberId);
//
//			if (ObjectUtils.isEmpty(member)) {
//				throw new UsernameNotFoundException("No user corresponding to member ID" + memberId);
//			} else {
//				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//				/* member 객체에 권한에 대한 인스턴스(멤버) 변수를 하나 둬서 이곳에서 ADD를 하면 될 것 같다.. */
//				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//				user = new User(memberId, member.getMemberPw(), authorities);
//			}
//		}
//
//		return user;
//	}
//
//}
