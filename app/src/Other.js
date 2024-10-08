import { useNavigate } from "react-router-dom";
import React from "react";

function Other(){

    const navigate = useNavigate();

    const goToMain = () => {
        navigate('/');
    };

    return(

        <>        
        <h2>other페이지</h2>
        <button onClick = {goToMain}>메인으로 가기</button>
        </>
        
    );

}

export default Other;