package com.member.model;

import java.util.List;

public interface MemberDAO_interface {
	public void insert(MemberVO memVO);

	public void update(MemberVO memVO);

	public void delete(Integer memno);

	public MemberVO findByPrimaryKey(Integer memno);

	public MemberVO findByName(String mname);

	public List<MemberVO> getAll();

	public MemberVO login(String maccount);

	public MemberVO forgetpassword(String mem_email);

	public List<MemberVO> getAll2();
}
