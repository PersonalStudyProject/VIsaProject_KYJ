import React,{ useContext, useState, useEffect } from 'react';
import { UserContext } from './Context';
import { useNavigate } from 'react-router-dom';


function Reservation() {
    const { userData } = useContext(UserContext); //로그인한 사용자정보가져오기
    const navigate = useNavigate();
    // const [name, setName] = useState('');
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');

    //로그인하지 않은경우 로그인페이지로 리디렉션
    
    // useEffect(() =>{
    //     if(!userData || !userData.username){
    //         alert('예약을 하려면 먼저 로그인하세요');
    //         navigate('/login');
    //         return null; //컴포넌트 렌더링 중단
    //     } 
    // },[userData, navigate]);

    useEffect(() => {
        const userData = JSON.parse(localStorage.getItem('currentUser'));
        if (!userData || !userData.username) {
            alert('예약을 하려면 먼저 로그인하세요');
            navigate('/login');
        }
    }, [navigate]);

    // if(!userData.username){
    //             alert('예약을 하려면 먼저 로그인하세요');
    //             navigate('/login');
    //             return; //컴포넌트 렌더링 중단
    //         } 

    // if (!userData || !userData.username) {
    //     return null;  // 렌더링 중단
    // }
    

    // 폼 제출 핸들러
    const handleReservation = async (event) => {
        event.preventDefault();

        //전송할 예약데이터
        const reservationData = {
            name : userData.username,  //로그인한 사용자의 이름
            date : date,
            time : time
        }

        try {
            const response = await fetch('http://localhost:5000/Reservation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reservationData),  // 예약 데이터를 JSON 형태로 변환하여 전송
            });

            if (response.ok) {
                alert('예약이 성공적으로 완료되었습니다!');
                navigate('/Main');
                // 입력 폼 초기화
                // setName('');
                setDate('');
                setTime('');
            } else {
                alert('예약에 실패했습니다. 다시 시도해주세요.');
            }
        } catch (error) {
            console.error('서버 통신 에러:', error);
            alert('서버와의 통신 중 오류가 발생했습니다.');
        }
    };

    return (
        <>
            <h1>{userData.username}예약 페이지입니다.</h1>
            <form onSubmit={handleReservation}>
                {/* <label>
                    이름:
                    <input 
                        type="text" 
                        value={name} 
                        onChange={(e) => setName(e.target.value)} 
                        placeholder="이름을 입력하세요" 
                        required 
                    />
                </label> */}
                <br />
                <label>
                    방문 날짜:
                    <input type="date" 
                    value={date}
                    onChange={(e) => setDate(e.target.value)} 
                        required />
                </label>
                <br />
                <label>
                    방문 시간:
                    <input 
                        type="time" 
                        value={time} 
                        onChange={(e) => setTime(e.target.value)} 
                        required 
                    />
                </label>
                <br />
                <button type="submit">예약하기</button>
            </form>
        </>
    );
}

export default Reservation;