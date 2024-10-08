import React from "react";
import { useNavigate } from "react-router-dom";

function AdminPage(){
    const navigate = useNavigate();

    return(
        <>
        <h1>관리자페이지</h1>
        <button onClick={()=> navigate('/')}>메인으로</button><br/><br/>
        <button onClick={()=> navigate('/Admin/MemberInfo')}>회원정보</button>
        </>
    )
}

export default AdminPage;