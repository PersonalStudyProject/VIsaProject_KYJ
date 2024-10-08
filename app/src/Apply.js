import React from 'react';
import { useNavigate } from 'react-router-dom';
import './css/Apply.css';

function Apply(){

    const navigate = useNavigate();

    const visaC = () => {
        navigate('/Visa/Cvisa');
    }

    const visaD = () => {
        navigate('/Visa/Dvisa');
    }

    const visaE = () => {
        navigate('/Visa/Evisa');
    }

    const visaF = () => {
        navigate('/Visa/Fvisa');
    }

    return(
        <>
        <button onClick={() => navigate(-1)}>이전 페이지</button><br/><br/>
        <h1>신청하기 페이지</h1>
        <button onClick={visaC}>C비자</button><br/><br/>
        <button onClick={visaD}>D비자</button><br/><br/>
        <button onClick={visaE}>E비자</button><br/><br/>
        <button onClick={visaF}>F비자</button><br/><br/>
        </>
    )

}
export default Apply;