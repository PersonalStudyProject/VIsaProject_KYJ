import { useNavigate } from "react-router-dom";
import React from "react";

function Cvisa(){

    const navigate = useNavigate();

    return(
        <>

        <button onClick={()=>navigate('/')}>메인으로</button><br/><br/>
        <button onClick={() => navigate(-1)}>이전 페이지</button><br/><br/>
        <h1>Cvisa</h1>
        
        <button onClick={() =>navigate('/Visa/DetailC1')}>C-1 일시취재</button><br/><br/>
        <button>C-3 단기방문</button><br/><br/>
        <button>C-4 단기취업</button><br/><br/>
        
        </>
    );
}

export default Cvisa;