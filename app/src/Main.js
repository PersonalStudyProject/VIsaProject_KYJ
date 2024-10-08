import React, { useContext, useEffect } from 'react';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom'
import { UserContext } from './Context';
import './css/Main.css';

function Main(){

    const {userData, setUserData, logout} = useContext(UserContext);
    //입력값 저장함
    const [inputValue, setInputValue] = useState(''); //단일 입력값
    // const [inputList, setInputList] = useState([]);  //입력값 리스트상태

    const [inputList, setInputList] = useState(()=>{
        const savedList = localStorage.getItem('inputList');
        return savedList ? JSON.parse(savedList) :[];
    });

    const navigate = useNavigate();

    useEffect(()=>{
        console.log("UserData", userData);
        
    },[userData]);

    // useEffect(() => {
    //     const savedUser = localStorage.getItem('currentUser');
    //     if (savedUser) {
    //         setUserData(JSON.parse(savedUser));
    //     }
    // }, []);


    //입력값 업데이트
    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    }

    const addToListAndNavigate = () =>{
    
        if(inputValue.trim()){

            //입력값을 리스트에 추가
            const newList =[...inputList, inputValue];
            setInputList(newList); //상태업데이트
            setInputValue(''); //입력값초기화

            localStorage.setItem('inputList', JSON.stringify(newList));

            //페이지 이동하면서 list전달
            navigate('/Etc',{state:{inputList:newList}});
        }else{
            alert('값을 입력하세요');  
        }
    }
    

    const goLogin =() =>{
        navigate('/Login');
    
        };

    const goOther = () =>{

        navigate('/Other');
    }    

    const goMyPage = () =>{

        navigate('/MyPage');
    }   

    const goApply = () =>{

        navigate('/Apply');
    }  

    const goEtc = () =>{
        if(inputList.length > 0){
            navigate('/Etc', {state: {inputList}}); // 입력된 리스트를 전달
        } else {
            alert('리스트에 값이 없습니다');
        }
    } 

    const handleLogout =() => {
        // if(logout){
        logout(); //기존 로그아웃처리
        // navigate('/Login');
        // }else{
        //     console.log('logout function is not available');
            
        // }

        //로그아웃시 현재 사용자 정보를 localstoryage에서 삭제
        localStorage.removeItem('currentUser');

        //전역상태 초기화
        setUserData({ username:'',password:''});

        //로그인 페이지로 리디렉션
        navigate('/Login');

    };

    //etc페이지로 전달
    // const goEtc =() =>{
    //     console.log('Navigating to ETC with', inputList);
    //     if(inputList.length >0){
    //     navigate('/Etc',{state:{inputList}});
    //     }else{
    //         alert('리스트에 값이 없습니다');
    //     }
    // };

    return(
        <div>
        <h1>VISA PROJECT !</h1>
        {userData.username ? (
                <>
                    <p>{userData.username}님!, 로그인이 완료되었습니다</p>
                    <button className="common-button" onClick={handleLogout}>로그아웃</button>
                    <button className="common-button" onClick={goMyPage}>마이페이지</button>
                    {userData.username === 'admin' &&(
                        <button className="common-button" onClick={() => navigate('/admin')}>관리자페이지</button>
                    )}
                </>
            ) : (
                <p>로그인하지 않았습니다.</p>
            )}
        <button className="common-button" onClick={goLogin}>로그인 </button><br/> <br/>
        <input value={inputValue} onChange={handleInputChange} placeholder='값을 입력하세요'></input> <br/> <br/>
        <button className="common-button" onClick={goEtc}>기타 목록보기</button>
        <button className="common-button" onClick={addToListAndNavigate}>추가</button> {/* 입력값 리스트에 추가 */}
        <br/> <br/>
        <button className="common-button" onClick={goOther}>other</button>
        <br/> <br/>
        <button className="common-button" onClick={goApply}>신청하기</button>
        <br/> <br/>
        <button className="common-button" onClick={() =>navigate('/Reservation')}>예약하기</button>
        
        {/* <button onClick={goEtc}>기타</button> */}
        </div>
    )

}

export default Main;