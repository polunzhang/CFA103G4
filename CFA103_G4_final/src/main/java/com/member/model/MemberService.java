package com.member.model;

import java.util.List;

public class MemberService {
	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	public MemberVO addMember(String mname, String gender, java.sql.Date bday, String address, String phone,
			String maccount, String mpassword, String mem_email, Integer mem_state, Integer mem_verify, String mem_id) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMname(mname);
		memberVO.setGender(gender);
		memberVO.setBday(bday);
		memberVO.setAddress(address);
		memberVO.setPhone(phone);
		memberVO.setMaccount(maccount);
		memberVO.setMpassword(mpassword);
		memberVO.setMem_email(mem_email);
		memberVO.setMem_state(mem_state);
		memberVO.setMem_verify(mem_verify);
		memberVO.setMem_id(mem_id);
		dao.insert(memberVO);
		return memberVO;
	}

//	public MemberVO updateMember(Integer memno, String mname, String gender, java.sql.Date bday, String address, String phone,
//			String maccount, String mpassword, String mem_email, Integer mem_state, Integer mem_verify, String mem_id) {
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMemno(memno);
//		memberVO.setMname(mname);
//		memberVO.setGender(gender);
//		memberVO.setBday(bday);
//		memberVO.setAddress(address);
//		memberVO.setPhone(phone);
//		memberVO.setMaccount(maccount);
//		memberVO.setMpassword(mpassword);
//		memberVO.setMem_email(mem_email);
//		memberVO.setMem_state(mem_state);
//		memberVO.setMem_verify(mem_verify);
//		memberVO.setMem_id(mem_id);
//		dao.update(memberVO);
//		return memberVO;
//	}

	public MemberVO updateMember(MemberVO memberVO) {
		dao.update(memberVO);
		return memberVO;
	}
	
	public void deleteMember(Integer memno) {
		dao.delete(memno);
	}

	public MemberVO getOneMember(Integer memno) {
		return dao.findByPrimaryKey(memno);
	}
	
	public MemberVO login(String maccount) {
		return dao.login(maccount);
	}
	
	public MemberVO getOneMemberByName(String mname) {
		return dao.findByName(mname);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}
	
	public MemberVO forgetpassword(String mem_email) {
		return dao.forgetpassword(mem_email);
	}
}
