import React from "react";
import { useNavigate } from "react-router-dom";

function DetailC1(){

    const navigate = useNavigate();

    return (
        <>
            <h1>DetailC-1</h1>
            <button onClick={() => navigate(-1)}>뒤로가기</button>
            <br /><br /><br /><br />
            <table border="0">
                <thead>
                    <tr>
                        <th>체류기간</th>
                        <th>연장시 필요서류</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>최장 90일</td>
                        <td>1. 신청서 2.여권원본 3. 수수료 4.연장필요성 소명서류</td>
                    </tr>
                    <tr>
                        <td>입국일로부터 90일까지 연장</td>
                        <td>두번째 칸</td>
                    </tr>
                </tbody>
            </table>

            <br /><br /><br /><br />
            <button onClick={() => navigate('/DocumentApplication')}>신청하기</button>
        </>
    );
}

export default DetailC1;