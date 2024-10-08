import React from "react";
import { useNavigate } from "react-router-dom";
// import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';

function MemberInfo(){
    const navigate = useNavigate();

    //로컬스토리지에서 사용자 정보 불러오기
    const savedUsers = JSON.parse(localStorage.getItem('users'))||[];

    return(
        <>
        <h1>관리자페이지</h1>
        <button onClick={()=> navigate('/')}>메인으로</button>
        <table border="1">
            <thead>
                <tr>
                    <th>아이디</th>
                    <th>비밀번호</th>
                    <th>이메일</th>
                </tr>
            </thead>
            <tbody>
                {savedUsers.map((user, index) => (
                    <tr key={index}>
                        <td>{user.username}</td>
                        <td>{user.password}</td>
                        <td>{user.email}</td>
                    </tr>
                ))}
            </tbody>
        </table>
        </>
    )
}

export default MemberInfo;